<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Final Project — Graphs & Binary Search Trees</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:wght@400;500&family=Fraunces:ital,opsz,wght@0,9..144,300;0,9..144,500;1,9..144,300&family=DM+Sans:wght@400;500&display=swap" rel="stylesheet">
<style>
  *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

  :root {
    --bg: #f7f5f0;
    --surface: #ffffff;
    --surface2: #f0ede6;
    --border: #e0dbd0;
    --text: #1a1814;
    --muted: #6b6660;
    --accent: #2d5a3d;
    --accent2: #7c3d1a;
    --accent-light: #e8f0eb;
    --accent2-light: #f5ece4;
    --mono: 'IBM Plex Mono', monospace;
    --serif: 'Fraunces', Georgia, serif;
    --sans: 'DM Sans', sans-serif;
    --warn-bg: #fef9ec;
    --warn-border: #d4a017;
    --warn-text: #7a5c00;
    --tip-bg: #edf6f0;
    --tip-border: #2d5a3d;
    --tip-text: #1a3d27;
    --note-bg: #eef2fb;
    --note-border: #3d5ca8;
    --note-text: #1a2d6e;
  }

  body {
    font-family: var(--sans);
    background: var(--bg);
    color: var(--text);
    font-size: 15px;
    line-height: 1.75;
  }

  /* ── Layout ── */
  .page {
    max-width: 860px;
    margin: 0 auto;
    padding: 0 24px 80px;
  }

  /* ── Header ── */
  .site-header {
    border-bottom: 1px solid var(--border);
    padding: 14px 0;
    margin-bottom: 0;
    display: flex;
    align-items: center;
    gap: 10px;
  }
  .site-header .crumb {
    font-family: var(--mono);
    font-size: 12px;
    color: var(--muted);
  }
  .site-header .crumb a { color: var(--accent); text-decoration: none; }
  .site-header .crumb a:hover { text-decoration: underline; }
  .site-header .sep { color: var(--border); }

  /* ── Hero ── */
  .hero {
    padding: 52px 0 36px;
    border-bottom: 1px solid var(--border);
    margin-bottom: 40px;
    animation: fadeUp 0.5s ease both;
  }
  .hero-label {
    font-family: var(--mono);
    font-size: 11px;
    letter-spacing: 0.12em;
    text-transform: uppercase;
    color: var(--accent);
    margin-bottom: 14px;
    display: flex;
    align-items: center;
    gap: 8px;
  }
  .hero-label::before {
    content: '';
    display: inline-block;
    width: 18px;
    height: 1px;
    background: var(--accent);
  }
  h1 {
    font-family: var(--serif);
    font-size: 42px;
    font-weight: 300;
    line-height: 1.15;
    color: var(--text);
    margin-bottom: 24px;
    max-width: 600px;
  }
  h1 em {
    font-style: italic;
    color: var(--accent);
  }

  /* ── Key info pills ── */
  .meta-row {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 8px;
  }
  .meta-pill {
    display: flex;
    align-items: center;
    gap: 8px;
    background: var(--surface);
    border: 1px solid var(--border);
    border-radius: 6px;
    padding: 7px 14px;
    font-size: 13px;
  }
  .meta-pill .pill-label {
    font-family: var(--mono);
    font-size: 10px;
    letter-spacing: 0.08em;
    text-transform: uppercase;
    color: var(--muted);
  }
  .meta-pill .pill-value {
    font-weight: 500;
    color: var(--text);
  }
  .meta-pill.accent { border-color: var(--accent); background: var(--accent-light); }
  .meta-pill.accent .pill-label { color: var(--accent); }
  .meta-pill.accent .pill-value { color: var(--accent); }
  .meta-pill.accent2 { border-color: var(--accent2); background: var(--accent2-light); }
  .meta-pill.accent2 .pill-label { color: var(--accent2); }
  .meta-pill.accent2 .pill-value { color: var(--accent2); }

  /* ── Sections ── */
  .section {
    margin-bottom: 44px;
    animation: fadeUp 0.5s ease both;
  }
  .section:nth-child(2) { animation-delay: 0.05s; }
  .section:nth-child(3) { animation-delay: 0.1s; }
  .section:nth-child(4) { animation-delay: 0.15s; }
  .section:nth-child(5) { animation-delay: 0.2s; }
  .section:nth-child(6) { animation-delay: 0.25s; }

  h2 {
    font-family: var(--serif);
    font-size: 24px;
    font-weight: 300;
    color: var(--text);
    margin-bottom: 16px;
    padding-bottom: 10px;
    border-bottom: 1px solid var(--border);
  }
  h3 {
    font-family: var(--sans);
    font-size: 13px;
    font-weight: 500;
    letter-spacing: 0.06em;
    text-transform: uppercase;
    color: var(--muted);
    margin: 24px 0 12px;
  }

  p { margin-bottom: 14px; color: var(--text); }

  blockquote {
    border-left: 3px solid var(--accent);
    margin: 20px 0;
    padding: 10px 20px;
    background: var(--accent-light);
    border-radius: 0 6px 6px 0;
    font-family: var(--serif);
    font-size: 16px;
    font-style: italic;
    color: var(--accent);
  }

  /* ── Admonitions ── */
  .admonition {
    border-radius: 6px;
    padding: 14px 18px;
    margin: 20px 0;
    font-size: 14px;
    display: flex;
    gap: 12px;
    align-items: flex-start;
  }
  .admonition .adm-icon { font-size: 16px; margin-top: 2px; flex-shrink: 0; }
  .admonition.warning { background: var(--warn-bg); border: 1px solid var(--warn-border); color: var(--warn-text); }
  .admonition.tip    { background: var(--tip-bg);  border: 1px solid var(--tip-border);  color: var(--tip-text);  }
  .admonition.note   { background: var(--note-bg); border: 1px solid var(--note-border); color: var(--note-text); }
  .admonition strong { font-weight: 500; }

  /* ── Project tracks ── */
  .track-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 16px;
    margin-top: 8px;
  }
  @media (max-width: 620px) { .track-grid { grid-template-columns: 1fr; } }

  .track-card {
    background: var(--surface);
    border: 1px solid var(--border);
    border-radius: 8px;
    overflow: hidden;
    transition: box-shadow 0.2s;
  }
  .track-card:hover { box-shadow: 0 4px 16px rgba(0,0,0,0.07); }
  .track-header {
    padding: 14px 18px;
    display: flex;
    align-items: center;
    gap: 10px;
    border-bottom: 1px solid var(--border);
  }
  .track-header.tree { background: var(--accent-light); }
  .track-header.graph { background: var(--accent2-light); }
  .track-header .track-icon { font-size: 20px; }
  .track-header .track-name {
    font-family: var(--mono);
    font-size: 12px;
    font-weight: 500;
    letter-spacing: 0.06em;
    text-transform: uppercase;
  }
  .track-header.tree .track-name { color: var(--accent); }
  .track-header.graph .track-name { color: var(--accent2); }
  .track-list { padding: 8px 0; }
  .track-item {
    display: flex;
    align-items: flex-start;
    gap: 12px;
    padding: 11px 18px;
    border-bottom: 1px solid var(--border);
    transition: background 0.15s;
    cursor: default;
  }
  .track-item:last-child { border-bottom: none; }
  .track-item:hover { background: var(--surface2); }
  .track-num {
    font-family: var(--mono);
    font-size: 11px;
    color: var(--muted);
    min-width: 20px;
    padding-top: 1px;
  }
  .track-item-body {}
  .track-item-name { font-size: 14px; font-weight: 500; color: var(--text); }
  .track-item-sub { font-size: 12px; color: var(--muted); margin-top: 2px; }

  /* ── Requirements list ── */
  .req-list { list-style: none; padding: 0; }
  .req-list li {
    display: flex;
    gap: 12px;
    padding: 10px 0;
    border-bottom: 1px solid var(--border);
    font-size: 14px;
    align-items: flex-start;
  }
  .req-list li:last-child { border-bottom: none; }
  .req-check {
    width: 18px;
    height: 18px;
    border: 1.5px solid var(--border);
    border-radius: 4px;
    flex-shrink: 0;
    margin-top: 2px;
    background: var(--surface);
  }

  /* ── Submission ── */
  .submit-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 16px;
  }
  @media (max-width: 620px) { .submit-grid { grid-template-columns: 1fr; } }
  .submit-card {
    background: var(--surface);
    border: 1px solid var(--border);
    border-radius: 8px;
    padding: 20px;
  }
  .submit-card h4 {
    font-family: var(--mono);
    font-size: 11px;
    letter-spacing: 0.08em;
    text-transform: uppercase;
    color: var(--muted);
    margin-bottom: 10px;
  }
  .submit-card p { font-size: 14px; margin-bottom: 8px; }
  .submit-card ul { padding-left: 18px; font-size: 14px; color: var(--muted); }
  .submit-card ul li { margin-bottom: 4px; }
  .submit-card ul li strong { color: var(--text); }

  /* ── FAQ ── */
  details {
    border: 1px solid var(--border);
    border-radius: 6px;
    margin-bottom: 8px;
    background: var(--surface);
    overflow: hidden;
  }
  summary {
    padding: 13px 16px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    list-style: none;
    display: flex;
    justify-content: space-between;
    align-items: center;
    user-select: none;
  }
  summary::-webkit-details-marker { display: none; }
  summary::after {
    content: '+';
    font-family: var(--mono);
    font-size: 16px;
    color: var(--muted);
    transition: transform 0.2s;
  }
  details[open] summary::after { content: '−'; }
  details[open] summary { border-bottom: 1px solid var(--border); }
  .faq-body { padding: 14px 16px; font-size: 14px; color: var(--muted); line-height: 1.7; }
  .faq-body p { margin-bottom: 6px; color: var(--muted); }
  .faq-body strong { color: var(--text); }

  /* ── Code ── */
  code {
    font-family: var(--mono);
    font-size: 12.5px;
    background: var(--surface2);
    border: 1px solid var(--border);
    border-radius: 4px;
    padding: 1px 6px;
    color: var(--accent2);
  }

  /* ── Animations ── */
  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(14px); }
    to   { opacity: 1; transform: translateY(0); }
  }

  /* ── Footer ── */
  .page-footer {
    margin-top: 60px;
    padding-top: 20px;
    border-top: 1px solid var(--border);
    font-family: var(--mono);
    font-size: 11px;
    color: var(--muted);
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 8px;
  }
</style>
</head>
<body>

<div class="page">

  <!-- Breadcrumb -->
  <header class="site-header">
    <span class="crumb">
      <a href="#">DSA</a>
      <span class="sep"> / </span>
      <a href="#">Projects</a>
      <span class="sep"> / </span>
      Final Project
    </span>
  </header>

  <!-- Hero -->
  <div class="hero">
    <div class="hero-label">Final Project</div>
    <h1>Graphs &amp; <em>Binary Search Trees</em></h1>
    <div class="meta-row">
      <div class="meta-pill accent">
        <span class="pill-label">Deadline</span>
        <span class="pill-value">May 18</span>
      </div>
      <div class="meta-pill">
        <span class="pill-label">Groups</span>
        <span class="pill-value">Up to 4 students</span>
      </div>
      <div class="meta-pill accent2">
        <span class="pill-label">Points</span>
        <span class="pill-value">20 pts</span>
      </div>
      <div class="meta-pill">
        <span class="pill-label">Report</span>
        <span class="pill-value">7 – 10 pages · PDF</span>
      </div>
    </div>
  </div>

  <!-- Goal -->
  <div class="section">
    <h2>Goal</h2>
    <p>
      This project is your chance to take everything you have learned about data structures and apply it
      to a problem that feels real. You will design and build an application around either a
      Binary Search Tree or a Graph — choosing the right structure, implementing it from scratch,
      and making it do something useful.
    </p>
    <p>
      Unlike a lab, there is no benchmark harness to hide behind. You decide how to structure the code,
      which operations matter, and how a user interacts with the system. That freedom is the point.
    </p>
    <blockquote>
      A good project is one you are proud to show — not just to your professor, but to anyone.
    </blockquote>
  </div>

  <!-- Choose your project -->
  <div class="section">
    <h2>Choose your project</h2>
    <p>
      Pick <strong>one</strong> project from either track. All options are at roughly the same difficulty.
      Choose what genuinely interests you — that will show in the final result.
    </p>

    <div class="track-grid">

      <!-- BST track -->
      <div class="track-card">
        <div class="track-header tree">
          <span class="track-icon">🌳</span>
          <span class="track-name">Binary Search Tree track</span>
        </div>
        <div class="track-list">
          <div class="track-item">
            <span class="track-num">01</span>
            <div class="track-item-body">
              <div class="track-item-name">Library catalog</div>
              <div class="track-item-sub">Insert, delete, search by ISBN; in-order listing</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">02</span>
            <div class="track-item-body">
              <div class="track-item-name">Stock price history</div>
              <div class="track-item-sub">Range query by date; min/max over a window</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">03</span>
            <div class="track-item-body">
              <div class="track-item-name">Student grade tracker</div>
              <div class="track-item-sub">Rank by GPA; find top-N; range of students</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">04</span>
            <div class="track-item-body">
              <div class="track-item-name">Pharmacy inventory</div>
              <div class="track-item-sub">Key by expiry date; find soonest-expiring items</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">05</span>
            <div class="track-item-body">
              <div class="track-item-name">Sports leaderboard</div>
              <div class="track-item-sub">Real-time score updates; top-K query</div>
            </div>
          </div>
        </div>
      </div>

      <!-- Graph track -->
      <div class="track-card">
        <div class="track-header graph">
          <span class="track-icon">🔗</span>
          <span class="track-name">Graph track</span>
        </div>
        <div class="track-list">
          <div class="track-item">
            <span class="track-num">01</span>
            <div class="track-item-body">
              <div class="track-item-name">Maze solver</div>
              <div class="track-item-sub">BFS shortest path · DFS all routes</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">02</span>
            <div class="track-item-body">
              <div class="track-item-name">Flight network</div>
              <div class="track-item-sub">Dijkstra — cheapest / fastest route</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">03</span>
            <div class="track-item-body">
              <div class="track-item-name">Campus navigator</div>
              <div class="track-item-sub">Dijkstra or A* — shortest walking route</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">04</span>
            <div class="track-item-body">
              <div class="track-item-name">Social network</div>
              <div class="track-item-sub">BFS — degrees of separation, mutual friends</div>
            </div>
          </div>
          <div class="track-item">
            <span class="track-num">05</span>
            <div class="track-item-body">
              <div class="track-item-name">Delivery route planner</div>
              <div class="track-item-sub">Prim or Kruskal — minimum spanning tree</div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>

  <!-- Requirements -->
  <div class="section">
    <h2>Requirements</h2>
    <p>These apply to all projects, regardless of track.</p>

    <h3>Core operations</h3>
    <ul class="req-list">
      <li><span class="req-check"></span><strong>BST:</strong> insert, delete, search, at least one traversal (in-order), and one domain-specific query (range, top-K, min/max).</li>
      <li><span class="req-check"></span><strong>Graph:</strong> add node / edge, BFS, DFS, and the algorithm central to your chosen project (Dijkstra, Kruskal, etc.).</li>
    </ul>

    <h3>Human interaction</h3>
    <ul class="req-list">
      <li><span class="req-check"></span>Provide a console menu where a user can load or generate a dataset, run the main operations, and see meaningful output.</li>
      <li><span class="req-check"></span>Output must be readable — not raw node/pointer dumps.</li>
    </ul>

    <h3>Code quality</h3>
    <ul class="req-list">
      <li><span class="req-check"></span>Meaningful class and variable names throughout.</li>
      <li><span class="req-check"></span>Each public method has a short comment.</li>
    </ul>

    <div class="admonition tip">
      <span class="adm-icon">💡</span>
      <div><strong>Keep it simple.</strong> A clean console menu is perfectly fine. The quality of your data structure and algorithms matters far more than the interface.</div>
    </div>

    <div class="admonition warning">
      <span class="adm-icon">⚠️</span>
      <div><strong>Academic integrity.</strong> You may use AI assistants to help you write and debug code. However, you must understand every line you submit. Expect to explain your implementation decisions during the review session.</div>
    </div>
  </div>

  <!-- Submission -->
  <div class="section">
    <h2>Submission</h2>
    <p>Submit <strong>two</strong> items by <strong>May 18</strong>.</p>
    <div class="submit-grid">

      <div class="submit-card">
        <h4>01 — Source code</h4>
        <p>A zip of your project, <em>or</em> a link to a public GitHub repository.</p>
        <ul>
          <li>Include all team members' names in a top-level <strong>README.md</strong></li>
        </ul>
      </div>

      <div class="submit-card">
        <h4>02 — Report · 7–10 pages · PDF</h4>
        <p>Write as if explaining your work to a new colleague — clear, honest, specific.</p>
        <ul>
          <li><strong>Design decisions</strong> — why you structured the code this way</li>
          <li><strong>Complexity analysis</strong> — time &amp; space for each operation</li>
          <li><strong>Testing</strong> — edge cases you verified and how</li>
          <li><strong>Reflection</strong> — what was hardest, what you would improve</li>
        </ul>
      </div>

    </div>

    <div class="admonition note" style="margin-top:16px;">
      <span class="adm-icon">📝</span>
      <div><strong>Report tone.</strong> Avoid padding and filler. A concise 7-page report that shows real understanding is worth far more than a padded 12-page one that doesn't.</div>
    </div>
  </div>

  <!-- FAQ -->
  <div class="section">
    <h2>FAQ</h2>

    <details>
      <summary>Can I work alone?</summary>
      <div class="faq-body"><p>Yes. Groups are <strong>optional</strong> and can be between 1 and 4 students. Solo projects are evaluated by the same criteria — there is no penalty, but also no reduced scope.</p></div>
    </details>

    <details>
      <summary>Can I propose a different project idea?</summary>
      <div class="faq-body"><p>Yes, with prior approval. Send a short description of the idea and the core data structure + algorithm you plan to use. Approval must be confirmed before you start.</p></div>
    </details>

    <details>
      <summary>How much of the code can I generate with AI?</summary>
      <div class="faq-body"><p>There is no hard limit. Using AI assistants to scaffold, debug, or improve your code is a skill in itself. What matters is that <strong>you understand the result</strong>. During the review you will be asked to walk through your implementation — unclear or copy-pasted answers will affect your grade.</p></div>
    </details>

    <details>
      <summary>What if my project turns out to be too easy or too hard?</summary>
      <div class="faq-body"><p>If it feels too easy, add a non-trivial extension (self-balancing, weighted edges, traversal visualization). If it feels too hard, come to office hours early — not the day before the deadline.</p></div>
    </details>

    <details>
      <summary>Is there a review / demo session?</summary>
      <div class="faq-body"><p>Yes. Each group will have a short 10-minute slot where you walk through the code and answer questions. Dates will be announced after submissions close.</p></div>
    </details>

  </div>

  <!-- Footer -->
  <div class="page-footer">
    <span>DSA · Final Project · Spring 2025</span>
    <span>Deadline: May 18</span>
  </div>

</div>
</body>
</html>