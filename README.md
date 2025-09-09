# CS635-M1-PriorityQueue
I built a custom priority queue in Java to manage student records using a clear rule: 70% units + 30% GPA, both normalized. 

The Student class is an immutable model that validates its fields and owns the priority() calculation so the rest of the code stays clean. 
The BinaryMaxHeap is the engine: an array-backed max-heap that gives me O(log N) inserts/removals and O(1) peek via simple sift-up/sift-down, with deterministic tie-breaks (units → GPA → name → RedID). 

On top of that, StudentPriorityQueue is a small, friendly facade that composes the heap and exposes the API I actually use—add, peek, pop, and a non-destructive printInPriorityOrder that works on a copied heap. 

Finally, Main demonstrates typical, boundary, and tie-breaker cases so it’s easy to verify behavior. I chose a heap because it meets the efficiency requirements while keeping the design modular; using encapsulation, abstraction, composition, and immutability makes the code safer, easier to test, and straightforward to maintain.
