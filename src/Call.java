/**
 * Represents a call
 */
public class Call {
    private final Integer callID;
    private final Integer callDuration;
    private long timeStamp;

    /**
     * @param callID Integer same as the CallNumber of the Caller object which creates this
     * @param callDuration Integer duration of the call in milliseconds
     */
    public Call(Integer callID, Integer callDuration){
        this.callID = callID;
        this.callDuration = callDuration;
    }

    public Integer getCallID() {
        return callID;
    }
    public int getCallDuration() {
        return callDuration;
    }
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
