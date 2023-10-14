import java.util.Random;

public class Agent implements Runnable {
    private Integer agentID;
    private CallQueue queue;
    private CallQueue[] queues;
    private Random random = new Random();
    public Agent(Integer agentID, CallQueue queue, CallQueue[] queues){
        this.agentID=agentID;
        this.queue = queue;
        this.queues=queues;
    }
    @Override
    public void run() {
        while (true) {
            if (queue.isEmpty()) {
                // Steal the last call from another worker's queue
                for (CallQueue otherQueue : queues) {
                    if (otherQueue != queue && !otherQueue.isEmpty()) {
                        Call stolenCall = otherQueue.getLastCallToArrive();
                        System.out.println("Worker " + agentID + " is answering (stolen) Call " + stolenCall.getCallID());
                        simulateCallDuration(stolenCall);
                        break;
                    }
                }
            } else {
                Call call = queue.takeCall();
                System.out.println("Worker " + agentID + " is answering Call " + call.getCallID());
                simulateCallDuration(call);
            }
        }

    }

    private void simulateCallDuration(Call call) {
        int duration = random.nextInt(1000) + 1000; // Random duration between 1 and 2 seconds
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Worker " + agentID + " finished Call " + call.getCallID());
    }
}
