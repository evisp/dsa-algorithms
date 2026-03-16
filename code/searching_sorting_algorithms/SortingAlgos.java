import java.util.Arrays;

public class SortingAlgos {

    // 1. Bubble Sort
    public static void bubbleSort(String[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    String temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 2. Selection Sort
    public static void selectionSort(String[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            String temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // 3. Insertion Sort
    public static void insertionSort(String[] arr) {

        for (int i = 1; i < arr.length; i++) {

            String key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // 4. Merge Sort
    public static void mergeSort(String[] arr, int left, int right) {

        if (left < right) {

            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    private static void merge(String[] arr, int left, int mid, int right) {

        String[] temp = new String[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {

            if (arr[i].compareTo(arr[j]) <= 0) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        for (int t = 0; t < temp.length; t++) {
            arr[left + t] = temp[t];
        }
    }

    // 5. Quick Sort
    public static void quickSort(String[] arr, int low, int high) {

        if (low < high) {

            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(String[] arr, int low, int high) {

        String pivot = arr[high];
        int i = low;

        for (int j = low; j < high; j++) {

            if (arr[j].compareTo(pivot) < 0) {

                String temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                i++;
            }
        }

        String temp = arr[i];
        arr[i] = arr[high];
        arr[high] = temp;

        return i;
    }

    // Helper method to print arrays
    public static void printArray(String[] arr) {
        System.out.println(Arrays.toString(arr));
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

        String[] a;

        a = scientists.clone();
        bubbleSort(a);
        System.out.print("Bubble Sort: ");
        printArray(a);

        a = scientists.clone();
        selectionSort(a);
        System.out.print("Selection Sort: ");
        printArray(a);

        a = scientists.clone();
        insertionSort(a);
        System.out.print("Insertion Sort: ");
        printArray(a);

        a = scientists.clone();
        mergeSort(a, 0, a.length - 1);
        System.out.print("Merge Sort: ");
        printArray(a);

        a = scientists.clone();
        quickSort(a, 0, a.length - 1);
        System.out.print("Quick Sort: ");
        printArray(a);
    }
}