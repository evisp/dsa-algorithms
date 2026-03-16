import java.util.Arrays;

public class SearchingAlgos {

    // Linear Search
    public static int linearSearch(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }

    // Binary Search
    public static int binarySearch(String[] arr, String target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            int comparison = arr[mid].compareToIgnoreCase(target);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        String[] scientists = {
            "Alan Turing",
            "Ada Lovelace",
            "Grace Hopper",
            "Edsger Dijkstra",
            "Donald Knuth",
            "John von Neumann",
            "Tim Berners-Lee"
        };

        String target = "Ada Lovelace";

        // Linear Search
        int linearResult = linearSearch(scientists, target);
        if (linearResult != -1) {
            System.out.println("Linear Search: Found " + target + " at index " + linearResult);
        } else {
            System.out.println("Linear Search: " + target + " not found");
        }

        // Binary Search requires sorted array
        Arrays.sort(scientists);

        int binaryResult = binarySearch(scientists, target);
        if (binaryResult != -1) {
            System.out.println("Binary Search: Found " + target + " at index " + binaryResult);
        } else {
            System.out.println("Binary Search: " + target + " not found");
        }

        // Print sorted list for clarity
        System.out.println("\nSorted list:");
        for (String s : scientists) {
            System.out.println(s);
        }
    }
}