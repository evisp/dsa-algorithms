import json
import os
from flask import Flask, jsonify, request, render_template
from flask_cors import CORS
from graph.city import City
from graph.city_graph import CityGraph

app = Flask(__name__)
CORS(app)

# ── Load graph from JSON ────────────────────────────────────

def load_graph():
    data_path = os.path.join(os.path.dirname(__file__), "data", "albania.json")
    with open(data_path) as f:
        data = json.load(f)

    graph = CityGraph()
    city_lookup = {}

    # First pass — create all cities
    for entry in data:
        city = City(
            name=entry["name"],
            type=entry["type"],
            lat=entry["lat"],
            lng=entry["lng"]
        )
        graph.add_city(city)
        city_lookup[city.name] = city

    # Second pass — create all roads
    for entry in data:
        from_city = city_lookup[entry["name"]]
        for road in entry["roads"]:
            to_city = city_lookup[road["to"]]
            # Only add if not already added by bidirectional pass
            if not graph.hasRoad_by_name(entry["name"], road["to"]):
                graph.add_road(
                    from_city,
                    to_city,
                    road["distance_km"],
                    road["bidirectional"]
                )

    return graph


graph = load_graph()


# ── Routes ──────────────────────────────────────────────────


@app.route("/")
def index():
    return render_template("index.html")

@app.route("/cities", methods=["GET"])
def get_cities():
    cities = [city.to_dict() for city in graph.all_cities()]
    return jsonify(cities)


@app.route("/roads", methods=["GET"])
def get_roads():
    roads = [road.to_dict() for road in graph.all_roads()]
    return jsonify(roads)


@app.route("/route", methods=["POST"])
def get_route():
    body = request.get_json()

    from_name = body.get("from")
    to_name   = body.get("to")
    algorithm = body.get("algorithm", "dijkstra").lower()

    if not from_name or not to_name:
        return jsonify({"error": "from and to are required"}), 400

    if from_name == to_name:
        return jsonify({"error": "from and to must be different"}), 400

    algorithms = {
        "bfs":      graph.bfs,
        "dfs":      graph.dfs,
        "dijkstra": graph.dijkstra,
        "astar":    graph.a_star
    }

    if algorithm not in algorithms:
        return jsonify({"error": f"Unknown algorithm. Choose from: {list(algorithms.keys())}"}), 400

    result = algorithms[algorithm](from_name, to_name)

    if result is None:
        return jsonify({"error": f"No route found from {from_name} to {to_name}"}), 404

    return jsonify(result)


# ── Health check ────────────────────────────────────────────

@app.route("/health", methods=["GET"])
def health():
    return jsonify({
        "status":  "ok",
        "cities":  len(graph.all_cities()),
        "roads":   len(graph.all_roads())
    })


if __name__ == "__main__":
    app.run(debug=True, port=5000)