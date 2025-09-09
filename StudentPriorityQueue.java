/** ************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

/**
 * Priority queue specialized for Student.
 * A small facade: I expose add/peek/pop (and print-in-order) and let BinaryMaxHeap do the heavy lifting.
 * Applies the Student priority + deterministic tie-breaks. O(log N) add/pop, O(1) peek
 */
public class StudentPriorityQueue { // Creates a queue ready to accept students

    // The underlying data structure: a custom binary max-heap.
    // 'final' ensures the reference cannot be changed, promoting immutability and thread safety for the reference.
    private final BinaryMaxHeap heap = new BinaryMaxHeap();

    // Returns the number of students in the queue.
    public int size() { return heap.size(); }

    // Checks if the queue is empty.
    public boolean isEmpty() { return heap.isEmpty(); }

    /**
     * Adds a student to the priority queue.
     * Time Complexity: O(log N)
     * @param s The Student object to be added.
     */
    public void add(Student s) {
        // Calculate the student's priority score based on the defined formula.
        double score = s.priority();
        // Create a Node wrapping the student and its score, then push it into the heap.
        heap.push(new BinaryMaxHeap.Node(score, s));
    }

    /**
     * Retrieves, but does not remove, the student with the highest priority.
     * Time Complexity: O(1)
     * @return The highest-priority Student, or null if the queue is empty.
     */
    public Student peek() {
        BinaryMaxHeap.Node n = heap.peek();
        return n == null ? null : n.student; // Return the student payload from the Node
    }

    /**
     * Retrieves and removes the student with the highest priority.
     * Time Complexity: O(log N)
     * @return The highest-priority Student, or null if the queue is empty.
     */
    public Student pop() {
        BinaryMaxHeap.Node n = heap.pop();
        return n == null ? null : n.student; // Return the student payload from the Node
    }

    /**
     * Generates a list of all students in descending priority order.
     * This operation is NON-MUTATING; the original queue remains unchanged.
     * Time Complexity: O(N log N) (as it performs a heapsort on a copy)
     * @return A List<Student> sorted from highest to lowest priority.
     */
    public List<Student> toListInPriorityOrder() {
        // Create a shallow copy of the heap to avoid modifying the original data.
        BinaryMaxHeap copy = heap.copy();
        List<Student> out = new ArrayList<>();
        // Repeatedly pop from the copy to get students in descending order.
        while (!copy.isEmpty()) {
            out.add(copy.pop().student);
        }
        return out;
    }

    /**
     * Prints the list of students in descending priority order to the console.
     * The format is: "[Rank]. [RedID]  -  [Name]"
     * This is a non-mutating operation.
     */
    public void printPriorityOrder() {
        // Get the ordered list non-destructively.
        List<Student> ordered = toListInPriorityOrder();
        System.out.println("Priority Order (highest first):");
        int i = 1; // Rank counter
        for (Student s : ordered) {
            System.out.printf("%2d. %s  -  %s%n", i++, s.getRedId(), s.getName());
        }
    }
}
