# CS635-M1-PriorityQueue
I built a custom priority queue in Java to manage student records using a clear rule: 70% units + 30% GPA, both normalized so the weights are meaningful. The Student class is my immutable data model: it validates all fields up front and owns the priority() calculation, so no other class repeats that logic or handles bad inputs. The BinaryMaxHeap is the engine; an array-backed max-heap that keeps the highest-priority student at the root and gives me O(log N) inserts/removals and O(1) peek via simple sift-up/sift-down. Ordering is deterministic with a tie-break cascade (units → GPA → name → RedID). On top of that, StudentPriorityQueue is a small, friendly facade that composes the heap and exposes the API I actually use—add, peek, pop, plus a non-destructive printInPriorityOrder that works on a copied heap so the real queue isn’t touched. Main drives the demo with typical, boundary, and tie-breaker cases so it’s easy to verify behavior.


OOP is doing the heavy lifting here. 
Encapsulation: Student wraps data + validation + priority; the heap wraps index math and sift logic; the queue wraps all of that behind a tiny API. 
Abstraction: users of the queue do not need to know it is a heap, they just add students and get the top one. 
Composition: the queue has a BinaryMaxHeap (not is a heap), which keeps things loosely coupled and let me swap internals later without changing the public interface. 
Immutability: Student (and the heap’s node objects) don't change after creation, which makes behavior predictable, safer for concurrency, and simpler to test. Put together, the heap meets the efficiency requirements, and the OOP structure keeps the design clean, modular, and maintainable.
