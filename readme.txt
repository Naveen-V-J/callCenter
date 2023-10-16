Program 2

Approach:
------------------
The second program leverages the powerful features of `java.util.concurrent` to simplify thread management and synchronization. It follows the structure of the first program, however it uses high-level constructs like `ExecutorService`, `AtomicInteger`, `LinkedBlockingDeque`, and `ReentrantLock` to make syncrhonization simpler.

Classes and Roles:
------------------
1. Main Class:
   - Serves as the entry point for the program.
   - Manages the creation of worker (agent) threads, caller threads, and call queues.
   - Utilizes an `ExecutorService` to simplify thread management and ensure graceful termination.

2. Agent Class:
   - Represents worker (agent) threads responsible for handling calls.
   - Randomly selects a call queue to service.
   - Handles calls in a FIFO manner and can steal calls from other queues when their own is empty.
   - Utilizes an `AtomicInteger` to track the total number of calls handled and finish when all calls are answered.

3. CallQueue Class:
   - Represents call queues that utilize `LinkedBlockingDeque` for thread-safe storage of calls.
   - Provides methods for adding, taking, and stealing calls.
   - Uses a `ReentrantLock` to ensure that only one worker can select a queue at a time.
   - Efficiently manages concurrency and thread safety.

4. Caller Class:
   - Represents caller threads responsible for generating calls.
   - Randomly selects call queues and adds calls to them.

5. Call Class:
   - Represents a call with a ID.
   - Created by a Caller

Synchronization:
------------------
The program employs several mechanisms for synchronization:
- The ExecutorService simplifies the management of worker and caller threads, ensuring they complete their tasks before program termination.
- The AtomicInteger tracks the total number of calls handled by all agents, allowing for thread-safe increments without explicit synchronization.
- LinkedBlockingDeque in the CallQueue class provides thread-safe storage for calls, ensuring proper synchronization for adding, taking, and stealing calls.
- The ReentrantLock in the CallQueue class ensures that only one worker can select a queue at a time, avoiding contention and ensuring fairness.