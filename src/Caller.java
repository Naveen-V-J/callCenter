import java.util.Random;

public class Caller implements Runnable {
    private final int callNumber;
    private final CallQueue[] callQueues;
    private final Random random = new Random();

    public Caller(int callNumber, CallQueue[] callQueues) {
        this.callNumber = callNumber;
        this.callQueues = callQueues;
    }

    @Override
    public void run() {
        int queueIndex = random.nextInt(callQueues.length);
        Call call = new Call(callNumber, random.nextInt(1000) + 1000); // Random call duration
        callQueues[queueIndex].addCall(call);
        System.out.println("Call " + callNumber + " added to Queue " + queueIndex);
    }
}
