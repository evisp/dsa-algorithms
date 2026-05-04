import heapq
from graph.city import City
from graph.road import Road
from graph.haversine import haversine


class CityGraph:
    def __init__(self):
        self.adjacency_list = {}  # { City: [Road] }

    # ── Build ───────────────────────────────────────────────

    def add_city(self, city):
        if city not in self.adjacency_list:
            self.adjacency_list[city] = []

    def add_road(self, from_city, to_city, distance_km, bidirectional):
        self.add_city(from_city)
        self.add_city(to_city)
        self.adjacency_list[from_city].append(
            Road(from_city, to_city, distance_km, bidirectional)
        )
        if bidirectional:
            self.adjacency_list[to_city].append(
                Road(to_city, from_city, distance_km, bidirectional)
            )

    def get_city_by_name(self, name):
        for city in self.adjacency_list:
            if city.name == name:
                return city
        return None

    def all_cities(self):
        return list(self.adjacency_list.keys())

    def all_roads(self):
        seen = set()
        roads = []
        for road_list in self.adjacency_list.values():
            for road in road_list:
                key = tuple(sorted([road.from_city.name, road.to_city.name]))
                if key not in seen:
                    seen.add(key)
                    roads.append(road)
        return roads

    def print_map(self):
        for city, roads in self.adjacency_list.items():
            print(f"{city} → {roads}")

    # ── BFS ─────────────────────────────────────────────────
    # Explores level by level using a queue (FIFO).
    # Returns visited order and shortest path by hop count.

    def bfs(self, start_name, end_name):
        start = self.get_city_by_name(start_name)
        end   = self.get_city_by_name(end_name)
        if not start or not end:
            return None

        visited  = []
        parent   = {start: None}
        queue    = [start]

        while queue:
            current = queue.pop(0)
            visited.append(current.name)

            if current == end:
                return {
                    "path":      self._reconstruct_path(parent, start, end),
                    "visited":   visited,
                    "total_km":  self._path_distance(parent, start, end),
                    "algorithm": "BFS"
                }

            for road in self.adjacency_list[current]:
                if road.to_city not in parent:
                    parent[road.to_city] = current
                    queue.append(road.to_city)

        return None

    # ── DFS ─────────────────────────────────────────────────
    # Explores as deep as possible using a stack (LIFO).
    # Does not guarantee shortest path.

    def dfs(self, start_name, end_name):
        start = self.get_city_by_name(start_name)
        end   = self.get_city_by_name(end_name)
        if not start or not end:
            return None

        visited = []
        parent  = {start: None}
        stack   = [start]

        while stack:
            current = stack.pop()

            if current.name in visited:
                continue
            visited.append(current.name)

            if current == end:
                return {
                    "path":      self._reconstruct_path(parent, start, end),
                    "visited":   visited,
                    "total_km":  self._path_distance(parent, start, end),
                    "algorithm": "DFS"
                }

            for road in self.adjacency_list[current]:
                if road.to_city not in parent:
                    parent[road.to_city] = current
                    stack.append(road.to_city)

        return None

    # ── Dijkstra ────────────────────────────────────────────
    # Guarantees shortest path by road distance.
    # Uses a min-heap priority queue ordered by cumulative km.

    def dijkstra(self, start_name, end_name):
        start = self.get_city_by_name(start_name)
        end   = self.get_city_by_name(end_name)
        if not start or not end:
            return None

        visited  = []
        parent   = {start: None}
        dist     = {start: 0}
        heap     = [(0, id(start), start)]  # (cost, tiebreak, city)

        while heap:
            cost, _, current = heapq.heappop(heap)

            if current.name in visited:
                continue
            visited.append(current.name)

            if current == end:
                return {
                    "path":      self._reconstruct_path(parent, start, end),
                    "visited":   visited,
                    "total_km":  round(dist[end], 1),
                    "algorithm": "Dijkstra"
                }

            for road in self.adjacency_list[current]:
                neighbour  = road.to_city
                new_cost   = dist[current] + road.distance_km

                if neighbour not in dist or new_cost < dist[neighbour]:
                    dist[neighbour]   = new_cost
                    parent[neighbour] = current
                    heapq.heappush(heap, (new_cost, id(neighbour), neighbour))

        return None

    # ── A* ──────────────────────────────────────────────────
    # Extends Dijkstra with a haversine heuristic.
    # Prioritises cities geographically closer to the goal,
    # so it typically visits fewer cities than Dijkstra.

    def a_star(self, start_name, end_name):
        start = self.get_city_by_name(start_name)
        end   = self.get_city_by_name(end_name)
        if not start or not end:
            return None

        visited  = []
        parent   = {start: None}
        g_cost   = {start: 0}                        # actual distance from start
        f_cost   = {start: haversine(start, end)}    # g + heuristic
        heap     = [(f_cost[start], id(start), start)]

        while heap:
            _, _, current = heapq.heappop(heap)

            if current.name in visited:
                continue
            visited.append(current.name)

            if current == end:
                return {
                    "path":      self._reconstruct_path(parent, start, end),
                    "visited":   visited,
                    "total_km":  round(g_cost[end], 1),
                    "algorithm": "A*"
                }

            for road in self.adjacency_list[current]:
                neighbour  = road.to_city
                new_g      = g_cost[current] + road.distance_km
                new_f      = new_g + haversine(neighbour, end)

                if neighbour not in g_cost or new_g < g_cost[neighbour]:
                    g_cost[neighbour]  = new_g
                    f_cost[neighbour]  = new_f
                    parent[neighbour]  = current
                    heapq.heappush(heap, (new_f, id(neighbour), neighbour))

        return None

    # ── Helpers ─────────────────────────────────────────────

    def _reconstruct_path(self, parent, start, end):
        path    = []
        current = end
        while current is not None:
            path.append(current.name)
            current = parent.get(current)
        path.reverse()
        return path if path[0] == start.name else []

    def _path_distance(self, parent, start, end):
        path    = []
        current = end
        while current is not None:
            path.append(current)
            current = parent.get(current)
        path.reverse()

        total = 0
        for i in range(len(path) - 1):
            for road in self.adjacency_list[path[i]]:
                if road.to_city == path[i + 1]:
                    total += road.distance_km
                    break
        return round(total, 1)
    
    def hasRoad_by_name(self, from_name, to_name):
        from_city = self.get_city_by_name(from_name)
        if not from_city:
            return False
        for road in self.adjacency_list[from_city]:
            if road.to_city.name == to_name:
                return True
        return False