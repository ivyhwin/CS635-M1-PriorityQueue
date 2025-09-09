/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

import java.util.ArrayList;

/**
 * A custom implementation of a binary MAX-heap.
 * This is the core data structure that efficiently manages elements based on their priority.
 * It stores Node objects which contain the calculated score and all necessary tie-breaker data.
 * This class ensures O(log N) for push and pop operations, and O(1) for peek.
 */
class BinaryMaxHeap {

    /**
     * A static inner class representing a node in the heap.
     * It is immutable (all fields are final) to ensure consistency and thread safety.
     * It stores the primary score and all tie-breaking criteria to enable deterministic comparisons.
     */
    static class Node {
        final double score;     // Primary comparison key (higher is better)
        final Student student;  // The actual Student object payload

        // Tie-breaker fields, stored here to avoid repeated method calls during heapify operations.
        final int units;        // Tie-breaker 1: Higher wins
        final double gpa;       // Tie-breaker 2: Higher wins
        final String name;      // Tie-breaker 3: Lexicographically smaller (A→Z) wins
        final String redId;     // Tie-breaker 4: Lexicographically smaller (A→Z) wins

        /**
         * Constructs a Node for the heap.
         * @param score The pre-calculated priority score.
         * @param s The Student object from which to extract tie-breaker data.
         */
        Node(double score, Student s) {
            this.score = score;
            this.student = s;
            this.units = s.getUnits();
            this.gpa = s.getGpa();
            this.name = s.getName();
            this.redId = s.getRedId();
        }
    }

    // The underlying data storage for the heap. An ArrayList provides O(1) access by index and dynamic resizing.
    private final ArrayList<Node> data = new ArrayList<>();

    // A small epsilon value for safe floating-point comparisons.
    private static final double EPS = 1e-12;

    // Returns the number of elements in the heap.
    int size() { return data.size(); }

    // Checks if the heap is empty.
    boolean isEmpty() { return data.isEmpty(); }

    /**
     * Creates a shallow copy of this heap.
     * This is efficient because Nodes are immutable.
     * @return A new BinaryMaxHeap containing the same nodes.
     */
    BinaryMaxHeap copy() {
        BinaryMaxHeap h = new BinaryMaxHeap();
        h.data.addAll(this.data); // Shallow copy is sufficient (Node objects are immutable)
        return h;
    }

    /**
     * Returns the root node (highest priority) without removing it.
     * Time Complexity: O(1)
     * @return The root Node, or null if the heap is empty.
     */
    Node peek() {
        return data.isEmpty() ? null : data.get(0);
    }

    /**
     * Adds a new node to the heap and maintains the heap property.
     * Time Complexity: O(log N)
     * @param node The node to be added.
     */
    void push(Node node) {
        data.add(node); // Append to the end of the list
        siftUp(data.size() - 1); // Restore heap property by sifting the new element up
    }

    /**
     * Removes and returns the root node (highest priority) and maintains the heap property.
     * Time Complexity: O(log N)
     * @return The root Node, or null if the heap is empty.
     */
    Node pop() {
        if (data.isEmpty()) return null;
        int lastIndex = data.size() - 1;
        swap(0, lastIndex); // Swap root with the last element
        Node removedNode = data.remove(lastIndex); // Remove the former root (now at the end)
        if (!data.isEmpty()) {
            siftDown(0); // Restore heap property by sifting the new root down
        }
        return removedNode;
    }

    // --- Core Heap Mechanics (Helper Methods) ---

    // Returns the index of the parent node for a given index.
    private static int parent(int i) { return (i - 1) / 2; }

    // Returns the index of the left child for a given index.
    private static int left(int i) { return 2 * i + 1; }

    // Returns the index of the right child for a given index.
    private static int right(int i) { return 2 * i + 2; }

    /**
     * Moves the node at index 'i' up the heap until the heap property is restored.
     * Used after insertion.
     * @param i The index of the node to sift up.
     */
    private void siftUp(int i) {
        // Continue until we reach the root (index 0)
        while (i > 0) {
            int parentIndex = parent(i);
            // If the current node has higher priority than its parent, swap them.
            if (isHigher(data.get(i), data.get(parentIndex))) {
                swap(i, parentIndex);
                i = parentIndex; // Move up to the parent's index
            } else {
                break; // Heap property is satisfied
            }
        }
    }

    /**
     * Moves the node at index 'i' down the heap until the heap property is restored.
     * Used after removal.
     * @param i The index of the node to sift down.
     */
    private void siftDown(int i) {
        int size = data.size();
        while (true) {
            int leftChild = left(i);
            int rightChild = right(i);
            int maxIndex = i; // Assume the current node is the largest

            // Compare with left child
            if (leftChild < size && isHigher(data.get(leftChild), data.get(maxIndex))) {
                maxIndex = leftChild;
            }
            // Compare with right child
            if (rightChild < size && isHigher(data.get(rightChild), data.get(maxIndex))) {
                maxIndex = rightChild;
            }
            // If the current node is larger than both children, stop.
            if (maxIndex == i) break;

            // Swap with the larger child and continue sifting down from that child's index.
            swap(i, maxIndex);
            i = maxIndex;
        }
    }

    // Swaps the nodes at indices i and j in the underlying ArrayList.
    private void swap(int i, int j) {
        Node temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    /**
     * The core comparator function for the MAX-heap.
     * Determines if node 'a' has higher priority than node 'b' and should be above it.
     * @param a The first node to compare.
     * @param b The second node to compare.
     * @return True if 'a' has higher priority than 'b'.
     */
    private static boolean isHigher(Node a, Node b) {
        // 1. Primary Comparison: Score
        double scoreDifference = a.score - b.score;
        if (Math.abs(scoreDifference) > EPS) {
            return scoreDifference > 0.0; // True if a's score is significantly larger
        }

        // 2. Tie-Breaker 1: Units Completed (higher wins)
        if (a.units != b.units) {
            return a.units > b.units;
        }
        // 3. Tie-Breaker 2: GPA (higher wins). Use epsilon for safe double comparison.
        if (Math.abs(a.gpa - b.gpa) > EPS) {
            return a.gpa > b.gpa;
        }
        // 4. Tie-Breaker 3: Name (lexicographical order, A→Z wins, i.e., smaller string)
        int nameComparison = a.name.compareTo(b.name);
        if (nameComparison != 0) {
            return nameComparison < 0;
        }
        // 5. Tie-Breaker 4: RedID (lexicographical order, A→Z wins)
        int idComparison = a.redId.compareTo(b.redId);
        return idComparison < 0;
    }
}