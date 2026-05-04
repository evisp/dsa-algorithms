// ── Config ───────────────────────────────────────────────
const API       = "http://localhost:5000";
const ANIM_STEP = 80;   // ms between visited-node reveals
const ANIM_PATH = 200;  // ms between path segment draws

// ── State ────────────────────────────────────────────────
let map;
let cityMarkers  = {};   // name → Leaflet marker
let roadLines    = [];   // all static road polylines
let pathLines    = [];   // animated route polylines
let selectedAlgo = "dijkstra";
let cities       = [];
let animTimeout  = [];   // track timeouts so we can cancel

// ── Colours ──────────────────────────────────────────────
const C = {
  road:        "#252938",
  roadHover:   "#3a4060",
  visited:     "#2a3050",
  visitedBorder:"#3a4570",
  path:        "#4f8aff",
  pathGlow:    "#4f8aff",
  start:       "#34d399",
  end:         "#fbbf24",
  default:     "#5a5f75",
  capital:     "#4f8aff",
  port:        "#34d399",
};

// ── DOM refs ─────────────────────────────────────────────
const selFrom       = document.getElementById("select-from");
const selTo         = document.getElementById("select-to");
const btnRun        = document.getElementById("btn-run");
const btnClear      = document.getElementById("btn-clear");
const resultCard    = document.getElementById("result-card");
const resAlgo       = document.getElementById("res-algo");
const resDistance   = document.getElementById("res-distance");
const resVisited    = document.getElementById("res-visited");
const resHops       = document.getElementById("res-hops");
const pathStops     = document.getElementById("path-stops");
const statusText    = document.getElementById("status-text");
const loadingOverlay= document.getElementById("loading-overlay");
const toastEl       = document.getElementById("toast-error");

// ── Init ─────────────────────────────────────────────────
document.addEventListener("DOMContentLoaded", () => {
  initMap();
  bindAlgoPills();
  bindControls();
  loadGraph();
});

// ── Map setup ────────────────────────────────────────────
function initMap() {
  map = L.map("map", {
    center:          [41.15, 20.17],
    zoom:            8,
    zoomControl:     false,
    attributionControl: false,
  });

  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 18,
  }).addTo(map);

  L.control.zoom({ position: "bottomleft" }).addTo(map);
}

// ── Load graph data ───────────────────────────────────────
async function loadGraph() {
  try {
    showLoading(true);

    const [cityData, roadData] = await Promise.all([
      fetch(`${API}/cities`).then(r => r.json()),
      fetch(`${API}/roads`).then(r => r.json()),
    ]);

    cities = cityData;
    populateSelects(cityData);
    plotCities(cityData);
    plotRoads(roadData, cityData);

    setStatus(`Graph loaded — ${cityData.length} cities, ${roadData.length} roads`);
  } catch (err) {
    setStatus("Failed to connect to backend");
    showToast("Cannot reach API at localhost:5000");
  } finally {
    showLoading(false);
  }
}

// ── Populate dropdowns ────────────────────────────────────
function populateSelects(cityData) {
  const sorted = [...cityData].sort((a, b) => a.name.localeCompare(b.name));
  sorted.forEach(city => {
    const optA = new Option(city.name, city.name);
    const optB = new Option(city.name, city.name);
    selFrom.appendChild(optA);
    selTo.appendChild(optB);
  });
}

// ── Plot cities as markers ────────────────────────────────
function plotCities(cityData) {
  cityData.forEach(city => {
    const marker = makeMarker(city);
    marker.addTo(map);
    cityMarkers[city.name] = marker;
  });
}

function makeMarker(city) {
  const size    = city.type === "capital" ? 16 : 12;
  const colour  = city.type === "capital" ? C.capital
                : city.type === "port"    ? C.port
                : C.default;

  const icon = L.divIcon({
    className: "",
    html: `<div style="
      width:${size}px; height:${size}px; border-radius:50%;
      background: rgba(${hexToRgb(colour)},0.15);
      border: 2px solid ${colour};
      cursor: pointer;
      transition: all 0.2s;
    "></div>`,
    iconSize:   [size, size],
    iconAnchor: [size / 2, size / 2],
  });

  const marker = L.marker([city.lat, city.lng], { icon })
    .bindTooltip(city.name, { permanent: false, direction: "top", offset: [0, -8] })
    .bindPopup(`
      <strong>${city.name}</strong><br>
      <small>${city.type} &nbsp;·&nbsp; ${city.lat.toFixed(4)}, ${city.lng.toFixed(4)}</small>
    `);

  // Click to select from/to
  marker.on("click", () => onMarkerClick(city.name));

  return marker;
}

function onMarkerClick(name) {
  if (!selFrom.value) {
    selFrom.value = name;
  } else if (!selTo.value && name !== selFrom.value) {
    selTo.value = name;
  } else {
    selFrom.value = name;
    selTo.value   = "";
  }
  updateRunButton();
  highlightSelected();
}

// ── Plot static road network ──────────────────────────────
function plotRoads(roadData, cityData) {
  const lookup = {};
  cityData.forEach(c => lookup[c.name] = c);

  roadData.forEach(road => {
    const from = lookup[road.from];
    const to   = lookup[road.to];
    if (!from || !to) return;

    const line = L.polyline(
      [[from.lat, from.lng], [to.lat, to.lng]],
      { color: C.road, weight: 2, opacity: 0.7, interactive: false }
    ).addTo(map);

    roadLines.push(line);
  });
}

// ── Highlight selected start/end ──────────────────────────
function highlightSelected() {
  // Reset all markers first
  cities.forEach(city => setMarkerStyle(city.name, "default"));

  if (selFrom.value) setMarkerStyle(selFrom.value, "start");
  if (selTo.value)   setMarkerStyle(selTo.value,   "end");
}

function setMarkerStyle(name, state) {
  const city   = cities.find(c => c.name === name);
  if (!city) return;

  const marker = cityMarkers[name];
  if (!marker) return;

  const size = city.type === "capital" ? 16 : 12;

  const styles = {
    default: {
      bg:     city.type === "capital" ? `rgba(${hexToRgb(C.capital)},0.15)`
            : city.type === "port"    ? `rgba(${hexToRgb(C.port)},0.15)`
            : "transparent",
      border: city.type === "capital" ? C.capital
            : city.type === "port"    ? C.port
            : C.default,
      shadow: "none",
    },
    visited: {
      bg:     C.visited,
      border: C.visitedBorder,
      shadow: "none",
    },
    path: {
      bg:     `rgba(${hexToRgb(C.path)},0.4)`,
      border: C.path,
      shadow: `0 0 10px rgba(${hexToRgb(C.pathGlow)},0.6)`,
    },
    start: {
      bg:     `rgba(${hexToRgb(C.start)},0.25)`,
      border: C.start,
      shadow: `0 0 12px rgba(${hexToRgb(C.start)},0.5)`,
    },
    end: {
      bg:     `rgba(${hexToRgb(C.end)},0.25)`,
      border: C.end,
      shadow: `0 0 12px rgba(${hexToRgb(C.end)},0.5)`,
    },
  };

  const s = styles[state] || styles.default;
  const el = marker.getElement();
  if (!el) return;

  const div = el.querySelector("div");
  if (!div) return;

  div.style.background  = s.bg;
  div.style.borderColor = s.border;
  div.style.boxShadow   = s.shadow;
}

// ── Algorithm pill selection ──────────────────────────────
function bindAlgoPills() {
  document.querySelectorAll(".algo-pill").forEach(pill => {
    pill.addEventListener("click", () => {
      document.querySelectorAll(".algo-pill").forEach(p => p.classList.remove("active"));
      pill.classList.add("active");
      selectedAlgo = pill.dataset.algo;
    });
  });
}

// ── Control bindings ──────────────────────────────────────
function bindControls() {
  selFrom.addEventListener("change", () => { updateRunButton(); highlightSelected(); });
  selTo.addEventListener("change",   () => { updateRunButton(); highlightSelected(); });
  btnRun.addEventListener("click",   runRoute);
  btnClear.addEventListener("click", clearAll);
}

function updateRunButton() {
  btnRun.disabled = !(selFrom.value && selTo.value && selFrom.value !== selTo.value);
}

// ── Run route ─────────────────────────────────────────────
async function runRoute() {
  const from = selFrom.value;
  const to   = selTo.value;
  if (!from || !to) return;

  clearAnimation();
  showLoading(true);
  setStatus(`Running ${selectedAlgo.toUpperCase()} from ${from} to ${to}...`);

  try {
    const res = await fetch(`${API}/route`, {
      method:  "POST",
      headers: { "Content-Type": "application/json" },
      body:    JSON.stringify({ from, to, algorithm: selectedAlgo }),
    });

    if (!res.ok) {
      const err = await res.json();
      throw new Error(err.error || "Unknown error");
    }

    const result = await res.json();
    showLoading(false);
    animateResult(result);

  } catch (err) {
    showLoading(false);
    showToast(err.message);
    setStatus("Route failed — " + err.message);
  }
}

// ── Animate result ────────────────────────────────────────
function animateResult(result) {
  const { path, visited, total_km, algorithm } = result;

  // Phase 1 — animate visited nodes
  visited.forEach((name, i) => {
    const t = setTimeout(() => {
      if (!path.includes(name)) {
        setMarkerStyle(name, "visited");
      }
      setStatus(`Exploring... ${i + 1} / ${visited.length} cities checked`);
    }, i * ANIM_STEP);
    animTimeout.push(t);
  });

  const phase2Start = visited.length * ANIM_STEP;

  // Phase 2 — draw path segments
  const t1 = setTimeout(() => {
    setStatus(`Path found — drawing route...`);
    drawPath(path);
  }, phase2Start);
  animTimeout.push(t1);

  // Phase 3 — show result card
  const t2 = setTimeout(() => {
    showResultCard(result);
    setStatus(`Done — ${algorithm} · ${total_km} km · ${visited.length} cities explored`);
  }, phase2Start + path.length * ANIM_PATH + 200);
  animTimeout.push(t2);
}

function drawPath(path) {
  const lookup = {};
  cities.forEach(c => lookup[c.name] = c);

  path.forEach((name, i) => {
    const t = setTimeout(() => {

      // Highlight marker
      if      (i === 0)              setMarkerStyle(name, "start");
      else if (i === path.length - 1) setMarkerStyle(name, "end");
      else                            setMarkerStyle(name, "path");

      // Draw segment to next city
      if (i < path.length - 1) {
        const a = lookup[name];
        const b = lookup[path[i + 1]];
        if (a && b) {
          const line = L.polyline(
            [[a.lat, a.lng], [b.lat, b.lng]],
            {
              color:     C.path,
              weight:    4,
              opacity:   0.9,
              lineCap:   "round",
              lineJoin:  "round",
            }
          ).addTo(map);
          pathLines.push(line);
        }
      }

    }, i * ANIM_PATH);
    animTimeout.push(t);
  });
}

// ── Result card ───────────────────────────────────────────
function showResultCard(result) {
  const { path, visited, total_km, algorithm } = result;

  resAlgo.textContent     = algorithm;
  resDistance.textContent = `${total_km} km`;
  resVisited.textContent  = `${visited.length} cities`;
  resHops.textContent     = `${path.length} stops`;

  // Build path stops list
  pathStops.innerHTML = "";
  path.forEach((name, i) => {
    if (i < path.length - 1) {
      const dot  = document.createElement("div");
      dot.className = "path-stop";
      dot.innerHTML = `<div class="path-stop-dot"></div><span>${name}</span>`;
      pathStops.appendChild(dot);

      const line = document.createElement("div");
      line.className = "path-stop-line";
      pathStops.appendChild(line);
    } else {
      const dot = document.createElement("div");
      dot.className = "path-stop";
      dot.innerHTML = `<div class="path-stop-dot"></div><span>${name}</span>`;
      pathStops.appendChild(dot);
    }

    // Stagger reveal
    const el = pathStops.children[i * 2] || pathStops.lastElementChild;
    setTimeout(() => el?.classList.add("visible"), i * 80);
  });

  resultCard.classList.add("visible");
}

// ── Clear ─────────────────────────────────────────────────
function clearAll() {
  clearAnimation();

  selFrom.value = "";
  selTo.value   = "";
  updateRunButton();

  resultCard.classList.remove("visible");
  pathStops.innerHTML = "";

  cities.forEach(city => setMarkerStyle(city.name, "default"));
  setStatus(`Graph loaded — ${cities.length} cities`);
}

function clearAnimation() {
  // Cancel pending timeouts
  animTimeout.forEach(t => clearTimeout(t));
  animTimeout = [];

  // Remove path lines from map
  pathLines.forEach(line => map.removeLayer(line));
  pathLines = [];
}

// ── Helpers ───────────────────────────────────────────────
function setStatus(msg) {
  statusText.textContent = msg;
}

function showLoading(visible) {
  loadingOverlay.classList.toggle("visible", visible);
}

function showToast(msg) {
  toastEl.textContent = msg;
  toastEl.classList.add("show");
  setTimeout(() => toastEl.classList.remove("show"), 3500);
}

function hexToRgb(hex) {
  const r = parseInt(hex.slice(1, 3), 16);
  const g = parseInt(hex.slice(3, 5), 16);
  const b = parseInt(hex.slice(5, 7), 16);
  return `${r},${g},${b}`;
}