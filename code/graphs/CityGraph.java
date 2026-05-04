import java.util.*;

public class CityGraph {

    private HashMap<City, List<Road>> adjacencyList;

    public CityGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addCity(City city) {
        if (!adjacencyList.containsKey(city)) {
            adjacencyList.put(city, new ArrayList<>());
        }
    }

    public void addRoad(City from, City to, int distanceKm, boolean isBidirectional) {
        addCity(from);
        addCity(to);
        adjacencyList.get(from).add(
        		new Road(from, to, 
        				distanceKm, 
        				isBidirectional));
        if (isBidirectional) {
            adjacencyList.get(to).add(new Road(to, from, distanceKm, isBidirectional));
        }
    }

    public void printMap() {
        for (Map.Entry<
        		City, List<Road>> 
        entry : adjacencyList.entrySet()) {
            System.out.println(
            		entry.getKey() + " → " 
            + entry.getValue());
        }
    }

    public int[] getDegree(City city) {
        if (!adjacencyList.containsKey(city)) 
        	return new int[]{-1, -1, -1};
        int outDegree = 
        		adjacencyList.get(city).size();
        int inDegree = 0;

        for (List<Road> roads : 
        	adjacencyList.values()) {
            for (Road road : roads) {
                if (road.to.equals(city)) 
                	inDegree++;
            }
        }
        return new int[]{inDegree, outDegree, inDegree + outDegree};
    }

    public boolean hasRoad(City from, City to) {
        if (!adjacencyList.containsKey(from) 
        		|| !adjacencyList.containsKey(to)) 
        	return false;
        for (Road road : adjacencyList.get(from)) {
            if (road.to.equals(to)) return true;
        }
        return false;
    }

    public boolean hasRoute(City from, City to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) return false;

        Set<City> visited = new HashSet<>();
        Queue<City> queue = new LinkedList<>();

        queue.add(from);
        visited.add(from);

        while (!queue.isEmpty()) {
            City current = queue.poll();
            if (current.equals(to)) return true;
            for (Road road : adjacencyList.get(current)) {
                if (!visited.contains(road.to)) {
                    visited.add(road.to);
                    queue.add(road.to);
                }
            }
        }

        return false;
    }
    
    // ── BFS traversal ──────────────────────────────────────
    // Explores level by level using a Queue (FIFO).
    // Visits all cities reachable from start, nearest first.
    // Time complexity: O(V + E)

    public List<City> bfs(City start) {
        List<City> result = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) return result;

        Set<City> visited = new HashSet<>();
        Queue<City> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            City current = queue.poll();
            result.add(current);

            for (Road road : adjacencyList.get(current)) {
                if (!visited.contains(road.to)) {
                    visited.add(road.to);
                    queue.add(road.to);
                }
            }
        }

        return result;
    }

    // ── DFS traversal ──────────────────────────────────────
    // Explores as deep as possible using a Deque as a stack (LIFO).
    // Visits all cities reachable from start, following each road
    // to its end before backtracking.
    // Time complexity: O(V + E)

    public List<City> dfs(City start) {
        List<City> result = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) return result;

        Set<City> visited = new HashSet<>();
        Deque<City> stack = new ArrayDeque<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            City current = stack.pop();

            if (visited.contains(current)) continue;
            visited.add(current);
            result.add(current);

            for (Road road : adjacencyList.get(current)) {
                if (!visited.contains(road.to)) {
                    stack.push(road.to);
                }
            }
        }

        return result;
    }
    
    /**
     * Finds the shortest path between two cities using Dijkstra's algorithm.
     *
     * <p>Expands cities in order of accumulated cost from {@code start}, settling
     * each city exactly once. Stops as soon as {@code end} is settled — at that
     * point its shortest distance is guaranteed to be final, because all edge
     * weights are non-negative.</p>
     *
     * <p>Time complexity: O((V + E) log V) where V is the number of cities and
     * E is the number of roads. Each edge relaxation may push to the priority
     * queue in O(log V); stale entries are ignored on pop.</p>
     *
     * @param start the city to depart from
     * @param end   the city to arrive at
     * @return      the minimum-cost path as an ordered {@code List<City>} from
     *              {@code start} to {@code end}, or an empty list if either city
     *              does not exist in the graph or no path connects them
     */
    public List<City> dijkstra(City start, City end) {
        // your code here
        return null;
    }

    /**
     * Reconstructs an ordered path from {@code start} to {@code end} by walking
     * the {@code prev} map backwards.
     *
     * <p>Called by {@link #dijkstra} and {@link #aStar} after the search
     * completes. Each entry in {@code prev} records which city settled a given
     * city — tracing from {@code end} back to {@code start} and prepending each
     * step produces the path in the correct order.</p>
     *
     * @param prev  a map where {@code prev.get(v)} is the city that settled
     *              {@code v} during the search
     * @param start the expected first city in the reconstructed path
     * @param end   the city the search was targeting
     * @return      an ordered {@code List<City>} from {@code start} to
     *              {@code end}, or an empty list if {@code end} was never
     *              reached (i.e. the path chain does not trace back to
     *              {@code start})
     */
    private List<City> reconstructPath(Map<City, City> prev, City start, City end) {
        // your code here
        return null;
    }
    
    /**
     * Finds the shortest path between two cities using the A* algorithm.
     *
     * <p>Extends Dijkstra by ordering the priority queue on {@code f(n) = g(n) + h(n)},
     * where {@code g(n)} is the actual cost from {@code start} and {@code h(n)} is the
     * admissible straight-line estimate to {@code end} from {@link #heuristic}.
     * This steers expansion toward the target, settling fewer cities than Dijkstra
     * on most inputs while still guaranteeing an optimal path.</p>
     *
     * <p>A {@code visited} set is required here — unlike Dijkstra, the combined
     * f-score comparator can reorder nodes in ways that cause the same city to
     * appear in the queue under different scores. Without the set, a stale entry
     * could be expanded after the city has already been optimally settled.</p>
     *
     * <p>Time complexity: O((V + E) log V) in the worst case, identical to
     * Dijkstra. In practice, a tight admissible heuristic reduces the number
     * of cities expanded significantly.</p>
     *
     * @param start the city to depart from
     * @param end   the city to arrive at
     * @return      the minimum-cost path as an ordered {@code List<City>} from
     *              {@code start} to {@code end}, or an empty list if either city
     *              does not exist in the graph or no path connects them
     */
    public List<City> aStar(City start, City end) {
        // your code here
        return null;
    }
    
    
    /**
     * Estimates the remaining cost from city {@code a} to city {@code b} using
     * the Haversine formula — the great-circle straight-line distance in kilometres.
     *
     * <p>This heuristic is <em>admissible</em>: it never overestimates the true
     * road distance, because no road between two points can be shorter than the
     * straight line connecting them. Admissibility guarantees that {@link #aStar}
     * always returns an optimal path.</p>
     *
     * <p>The Haversine formula accounts for the curvature of the Earth, making it
     * accurate over long distances where flat-plane approximations break down.</p>
     *
     * @param a the current city, carrying {@code lat} and {@code lon} in decimal degrees
     * @param b the target city, carrying {@code lat} and {@code lon} in decimal degrees
     * @return  the great-circle distance between {@code a} and {@code b} in kilometres
     */
    private double heuristic(City a, City b) {
        // your code here
        return 0;
    }
 
}