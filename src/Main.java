import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args) {
        int numWorkers = 3; // Number of worker threads
        int numCallQueues = 3; // Number of call queues
        int queueCapacity = 5; // Maximum capacity of each call queue
        int numCallers = 25; // Number of caller threads
        AtomicInteger totalCallsHandled = new AtomicInteger(0); // Shared counter for total calls handled

        CallQueue[] callQueues = new CallQueue[numCallQueues]; // Array to hold call queues
        char queueID = 'A'; // Identifier for call queues (e.g., A, B, C)

        // Create and initialize call queues
        for (int i = 0; i < numCallQueues; i++) {
            callQueues[i] = new CallQueue(queueCapacity, queueID);
            queueID++;
        }

        // Create an ExecutorService for managing worker and caller threads
        ExecutorService executor = Executors.newFixedThreadPool(28);

        // Create and start worker threads
        for (int i = 0; i < numWorkers; i++) {
            executor.execute(new Agent(i + 1, callQueues, totalCallsHandled, numCallers));
        }

        // Create and start caller threads
        for (int i = 0; i < numCallers; i++) {
            executor.execute(new Caller(i + 1, callQueues));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        Event.AllCallsAnswered();
    }
}