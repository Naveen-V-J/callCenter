import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;


public class CallQueue {

    private final LinkedBlockingDeque<Call> queue; // Queue to hold incoming calls, BlockingQueue is used to ensure that the data structure is thread-safe and synchronized
    private final ReentrantLock lock = new ReentrantLock(); // Lock for ensuring thread safety
    private Boolean hasWorker = false; // Flag to indicate if an agent is assigned to this queue
    private final char queueID;
    /**
     * Initializes a CallQueue.
     *
     * @param capacity The maximum capacity of the call queue.
     * @param queueID  A unique identifier for the call queue.
     */
    public CallQueue(int capacity, char queueID) {
        this.queue = new LinkedBlockingDeque<>(capacity);
        this.queueID = queueID;
    }

    /**
     * Adds a call to the call queue.
     *
     * @param call The Call object to be added to the queue.
     */
    public void addCall(Call call) {
        try {
            queue.put(call);
            Event.CallAppendedToQueue(call.getCallID(),queueID);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Takes the next call from the call queue.
     *
     * @return The Call object representing the call taken from the queue.
     */
    public Call takeCall() {
        Call call = queue.pollFirst();
        return call;
    }

    /**
     * Checks if the call queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Retrieves and removes the last call to arrive in the call queue.
     *
     * @return The Call object representing the last call to arrive.
     */
    public Call getLastCallToArrive() {

        Call lastCall = queue.pollLast();
        return lastCall;
    }

    /**
     * Attempts to select the call queue by a worker.
     *
     * @return True if the queue is successfully selected by a worker, false if it already has a worker.
     */
    public boolean selectQueue(){
        //Use tryLock so that worker threads aren't blocked unnecessarily
        if(lock.tryLock()){
            try {
                //Lock is used to ensure multiple worker/agent threads won't be able
                // to select the same queue
                if (hasWorker){
                    return false;
                }else {
                    hasWorker = true;
                    return true;
                }
            } finally {
                //After the queue has been selected the lock is released allowing
                lock.unlock();
            }

        }else {
            //If another worker thread already has locked the resource, return false
            return false;
        }

    }


    /**
     * Gets the unique identifier of the call queue.
     *
     * @return A character representing the queue's identifier.
     */
    public char getQueueID() {
        return queueID;
    }
}
