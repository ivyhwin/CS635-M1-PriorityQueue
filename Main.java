/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 * The main driver class to demonstrate the functionality of the StudentPriorityQueue.
 * It creates a queue, adds various students (including edge cases and ties), and displays the results.
 */


public class Main {
    public static void main(String[] args) {
        // Instantiate the priority queue
        StudentPriorityQueue pq = new StudentPriorityQueue();

        // ---------- Add students ----------
        pq.add(new Student("Ivy Huynh",      "R1234567", "ivy.huynh@university.edu",      3.7, 128));
        pq.add(new Student("Alex Kim",       "R2000001", "alex.kim@university.edu",       3.2, 96));
        pq.add(new Student("Jordan Lee",     "R2000002", "jordan.lee@university.edu",     2.9, 72));
        pq.add(new Student("Priya Patel",    "R2000003", "priya.patel@university.edu",    3.95, 110));
        pq.add(new Student("Sam Rivera",     "R2000004", "sam.rivera@university.edu",     3.4, 140));
        pq.add(new Student("Casey Nguyen",   "R2000005", "casey.nguyen@university.edu",   3.8, 100));
        pq.add(new Student("Taylor Brooks",  "R2000006", "taylor.brooks@university.edu",  3.1, 145));

        // ---------- Add boundary values to test edge cases ----------
        pq.add(new Student("ZeroZero", "R100", "zero.zero@university.edu", 0.0, 0));     // worst possible score
        pq.add(new Student("MaxGPA",   "R101", "max.gpa@university.edu",   4.0, 0));     // max GPA, min units
        pq.add(new Student("MaxUnits", "R102", "max.units@university.edu", 0.0, 150));   // min GPA, max units
        pq.add(new Student("MaxBoth",  "R103", "max.both@university.edu",  4.0, 150));   // max GPA, max units (should be top)

        // ---------- Add students to test tie-breaking rules ----------
        // These three have identical normalized scores (100/150 units & 3.0/4.0 GPA).
        // Order should be: Adam Xiong (name "Xiong" < "Young"), then Adam Young (same name, RedID R008 < R010?), then Bella Young.
        // Note: The tie-breaking logic in isHigher() uses name first, then RedID.
        // "Adam Xiong" has a name lexicographically less than "Adam Young", so it wins.
        pq.add(new Student("Adam Young",  "R010", "adam.young@university.edu", 3.0, 100));
        pq.add(new Student("Bella Young", "R009", "bella.young@university.edu",3.0, 100));
        pq.add(new Student("Adam Xiong",  "R008", "adam.xiong@university.edu", 3.0, 100));

        // ----- Demo Outputs -----
        System.out.println("Top (peek) — should be the max of everything (likely 'MaxBoth'):");
        System.out.println(pq.peek()); // Peek at the highest priority student non-destructively

        System.out.println();
        // Print the entire queue in priority order without modifying it
        pq.printPriorityOrder();

        // Demonstrate the destructive popping process
        System.out.println("\nPopping in priority order (destructive):");
        while (!pq.isEmpty()) {
            Student s = pq.pop(); // Remove and get the highest priority student
            // Print details along with their calculated score for verification
            System.out.printf("%s  -  %s  (score=%.5f)%n", s.getRedId(), s.getName(), s.priority());
        }
    }
}
