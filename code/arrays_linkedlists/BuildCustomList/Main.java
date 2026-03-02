public class Main {
    public static void main(String[] args) {

        // Example 1: "primitive" values via wrapper types (Integer for int)
        MyLinkedList<Integer> numbers = new MyLinkedList<>();
        numbers.addLast(10);
        numbers.addLast(20);
        numbers.addFirst(5);

        System.out.println("numbers = " + numbers);
        System.out.println("numbers.get(1) = " + numbers.get(1));
        System.out.println("removeFirst = " + numbers.removeFirst());
        System.out.println("numbers = " + numbers);
        System.out.println();

        // Example 2: famous CS names as "students"
        MyLinkedList<Student> students = new MyLinkedList<>();
        students.addLast(new Student(1, "Ada Lovelace"));
        students.addLast(new Student(2, "Alan Turing"));
        students.addLast(new Student(3, "Grace Hopper"));
        students.addFirst(new Student(0, "Edsger Dijkstra"));

        System.out.println("students = " + students);

        boolean removed = students.remove(new Student(2, "Alan Turing"));
        System.out.println("removed Alan Turing? " + removed);
        System.out.println("students = " + students);

        System.out.println("\nIterate with for-each:");
        for (Student s : students) {
            System.out.println(" - " + s);
        }
    }
}
