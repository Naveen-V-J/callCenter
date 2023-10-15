import java.util.Random;

/**
 * Represents a Caller thread which add a call to a random CallQueue
 */
public class Caller implements Runnable {
    private final int callNumber;
    private final CallQueue[] callQueues;
    private final Random random = new Random();

    /**
     * @param callNumber int the number of the call, 1-25
     * @param callQueues CallQueue[] list of the CallQueue objects the Caller can "call" to
     */
    public Caller(int callNumber, CallQueue[] callQueues) {
        this.callNumber = callNumber;
        this.callQueues = callQueues;
    }

    /**
     * Randomly selects a CallQueue from callQueues then it creates a Call object and with a random call duration (
     * between 1-2 seconds). This Call object is then added to the CallQueue using the addCall function CallQueue.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(2000) + 200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        int queueIndex = random.nextInt(callQueues.length);
        Call call = new Call(callNumber); // Random call duration
        callQueues[queueIndex].addCall(call);
    }
}
