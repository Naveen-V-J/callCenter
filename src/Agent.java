import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Agent implements Runnable {
    private final Integer agentID;
    private CallQueue queue;
    private final CallQueue[] queues;
    private final Random random = new Random();
    private final AtomicInteger totalCallsHandled; // Shared counter for the total calls answered by all agents
    private final Integer totalCalls; // The total number of calls to be answered by all agents

    /**
     * @param agentID   The unique identifier for the agent.
     * @param queues    An array of CallQueue instances representing call queues.
     * @param totalCallsHandled A shared counter to track the total number of calls answered.
     * @param totalCalls    The total number of calls to be answered by all agents.
     */
    public Agent(Integer agentID, CallQueue[] queues, AtomicInteger totalCallsHandled, Integer totalCalls){
        this.agentID=agentID;
        this.queues=queues;
        this.queue=null;
        this.totalCallsHandled = totalCallsHandled;
        this.totalCalls=totalCalls;
    }

    /**
     *
     */
    @Override
    public void run() {
        // Select a call queue to service

        while (this.queue==null){
            //Try to select a random queue, if the queue already has worker try again
            int attempt = random.nextInt(queues.length);
            if(queues[attempt].selectQueue()){
                this.queue = queues[attempt];
                break;
            }

        }

        Event.WorkerChoosesQueue(agentID,this.queue.getQueueID());

        while (totalCallsHandled.get()<totalCalls) {

            if (queue.isEmpty()) {
                // Steal the last call from another worker's queue
                for (CallQueue otherQueue : queues) {
                    if (otherQueue != queue && !otherQueue.isEmpty()) {
                        Call stolenCall = otherQueue.getLastCallToArrive();
                        if (stolenCall == null){
                            continue;
                        }
                        Event.WorkerStealsCall(agentID,stolenCall.getCallID(),otherQueue.getQueueID());
                        totalCallsHandled.incrementAndGet(); //Increment after completing a call
                        break;
                    }
                }
            } else {
                Call call = queue.takeCall();
                if (call == null){
                    continue;
                }
                Event.WorkerAnswersCall(agentID,call.getCallID());
                totalCallsHandled.incrementAndGet(); //Increment after completing a call
            }
        }

    }
}
