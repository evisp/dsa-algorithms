import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CityGraph albania = new CityGraph();

        // Real approximate coordinates (lat, lon)
        City tirana      = new City("Tirana",      "capital", 41.3275, 19.8187);
        City durres      = new City("Durrës",       "port",    41.3246, 19.4565);
        City elbasan     = new City("Elbasan",      "town",    41.1125, 20.0822);
        City vlore       = new City("Vlorë",        "port",    40.4667, 19.4833);
        City korce       = new City("Korçë",        "town",    40.6186, 20.7808);
        City gjirokaster = new City("Gjirokastër",  "town",    40.0758, 20.1389);
        City shkoder     = new City("Shkodër",      "town",    42.0683, 19.5126);
        City berat       = new City("Berat",        "town",    40.7058, 19.9522);

        // Road network (distances in km, bidirectional where applicable)
        albania.addRoad(tirana,   durres,       38,  true);
        albania.addRoad(tirana,   elbasan,      54,  true);
        albania.addRoad(tirana,   shkoder,     116,  true);
        albania.addRoad(durres,   vlore,       148,  true);
        albania.addRoad(elbasan,  berat,        71,  true);
        albania.addRoad(elbasan,  korce,        95,  true);
        albania.addRoad(berat,    gjirokaster,  98,  true);
        albania.addRoad(berat,    vlore,        67,  true);
        albania.addRoad(korce,    gjirokaster,  80,  true);

        System.out.println("=== Map ===");
        albania.printMap();

        System.out.println("\n=== Degrees ===");
        System.out.println("Tirana:      " + Arrays.toString(albania.getDegree(tirana)));
        System.out.println("Gjirokastër: " + Arrays.toString(albania.getDegree(gjirokaster)));

        System.out.println("\n=== Has Road ===");
        System.out.println("Tirana → Durrës:      " + albania.hasRoad(tirana, durres));
        System.out.println("Durrës → Tirana:      " + albania.hasRoad(durres, tirana));
        System.out.println("Tirana → Gjirokastër: " + albania.hasRoad(tirana, gjirokaster));

        System.out.println("\n=== Has Route ===");
        System.out.println("Tirana → Gjirokastër: " + albania.hasRoute(tirana, gjirokaster));
        System.out.println("Shkodër → Korçë:      " + albania.hasRoute(shkoder, korce));

        System.out.println("\n=== BFS from Tirana ===");
        albania.bfs(tirana).forEach(System.out::println);

        System.out.println("\n=== DFS from Tirana ===");
        albania.dfs(tirana).forEach(System.out::println);

        System.out.println("\n=== Dijkstra: Tirana → Gjirokastër ===");
        albania.dijkstra(tirana, gjirokaster).forEach(System.out::println);

        System.out.println("\n=== A*:        Tirana → Gjirokastër ===");
        albania.aStar(tirana, gjirokaster).forEach(System.out::println);

        System.out.println("\n=== Dijkstra vs A*: Shkodër → Korçë ===");
        List<City> d = albania.dijkstra(shkoder, korce);
        List<City> a = albania.aStar(shkoder, korce);
        System.out.println("Dijkstra: " + d);
        System.out.println("A*:       " + a);
    }
}