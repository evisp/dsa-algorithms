public class RecursionExamples {

    // 1. FACTORIAL: n! = n * (n-1)!
    public static long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    // 2. FIBONACCI: fib(n) = fib(n-1) + fib(n-2)
    public static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // 3. SUM 1 to N: sum(n) = n + sum(n-1)
    public static int sumToN(int n) {
        if (n <= 0) return 0;
        return n + sumToN(n - 1);
    }

    // 4. TOWERS OF HANOI: move n disks source→dest using helper
    public static void towersOfHanoi(int n, String source, String helper, String dest) {
        if (n == 1) {
            System.out.println("Move disk 1 from " + source + " → " + dest);
            return;
        }
        
        towersOfHanoi(n - 1, source, dest, helper);
        System.out.println("Move disk " + n + " from " + source + " → " + dest);
        towersOfHanoi(n - 1, helper, source, dest);
    }

    public static void main(String[] args) {
        System.out.println("=== FACTORIAL ===");
        System.out.println("5! = " + factorial(5));     // 120
        System.out.println("0! = " + factorial(0));     // 1

        System.out.println("\n=== FIBONACCI ===");
        System.out.println("fib(7) = " + fibonacci(7));  // 13
        System.out.println("fib(0) = " + fibonacci(0));  // 0

        System.out.println("\n=== SUM TO N ===");
        System.out.println("sum(1..5) = " + sumToN(5));  // 15
        System.out.println("sum(1..0) = " + sumToN(0));  // 0

        System.out.println("\n=== TOWERS OF HANOI (3 disks) ===");
        towersOfHanoi(3, "A", "B", "C");

    }
}