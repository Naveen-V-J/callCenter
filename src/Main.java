import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {
        int numWorkers = 3;
        int numCallQueues = 3;
        int queueCapacity = 5;
        int numCallers = 25;

        CallQueue[] callQueues = new CallQueue[numCallQueues];
        for (int i = 0; i < numCallQueues; i++) {
            callQueues[i] = new CallQueue(queueCapacity);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Starting executor");

        ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < numWorkers; i++) {
            executor.execute(new Agent(i, callQueues[i], callQueues));
        }

        for (int i = 0; i < numCallers; i++) {
            executor.execute(new Caller(i, callQueues));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }
    }
}