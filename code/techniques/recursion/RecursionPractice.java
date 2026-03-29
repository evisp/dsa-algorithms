public class RecursionPractice {

    // Checks if string reads same forward/backward
    public static boolean isPalindrome(String s) {
        if (s.length() <= 1) return true;
        if (s.charAt(0) != s.charAt(s.length() - 1)) return false;
        return isPalindrome(s.substring(1, s.length() - 1));
    }

    // Reverses array elements between left/right indices
    public static void reverseArray(int[] arr, int left, int right) {
        if (left >= right) return;
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        reverseArray(arr, left + 1, right - 1);
    }

    public static void main(String[] args) {
        // Palindrome tests
        System.out.println("Palindromes:");
        System.out.println("\"abc\" → " + isPalindrome("abc"));      // false
        System.out.println("\"aba\" → " + isPalindrome("aba"));     // true
        System.out.println("\"racecar\" → " + isPalindrome("racecar")); // true
        System.out.println("\"\" → " + isPalindrome(""));           // true
        System.out.println("\"a\" → " + isPalindrome("a"));         // true

        // Reverse tests
        System.out.println("\nReverse arrays:");
        
        int[] arr1 = {1, 2, 3, 4, 5};
        System.out.print("Before: ");
        printArray(arr1);
        reverseArray(arr1, 0, arr1.length - 1);
        System.out.print("After:  ");
        printArray(arr1);  // [5,4,3,2,1]

        int[] arr2 = {1, 2, 3};
        System.out.print("Before: ");
        printArray(arr2);
        reverseArray(arr2, 0, arr2.length - 1);
        System.out.print("After:  ");
        printArray(arr2);  // [3,2,1]
    }

    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}