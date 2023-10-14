import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public class CallQueue {
    private final ArrayBlockingQueue<Call> queue;
    private final ConcurrentMap<Integer, Call> callsInQueue = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public CallQueue(int capacity) {
        queue = new ArrayBlockingQueue<>(capacity);
    }

    public void addCall(Call call) {
        try {
            queue.put(call);
            callsInQueue.put(call.getCallID(), call);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
        }
    }

    public Call takeCall() {
        try {
            Call call = queue.take();
            callsInQueue.remove(call.getCallID());
            return call;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public boolean isEmpty() {
        try {
            return queue.isEmpty();
        } finally {
        }
    }

    public Call getLastCallToArrive() {
        try {
            lock.lock();
            if (callsInQueue.isEmpty()) {
                return null;
            }


            int maxCallNumber = callsInQueue.keySet().stream().max(Integer::compareTo).orElse(0);
            Call returnCall = callsInQueue.get(maxCallNumber);
            callsInQueue.remove(maxCallNumber);
            queue.remove(returnCall);
            return returnCall;
        } finally {
            lock.unlock();
        }
    }


}
