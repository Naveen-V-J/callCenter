/**
 * Represents a call
 */
public class Call {
    private final Integer callID;
    private long timeStamp;

    /**
     * @param callID Integer same as the CallNumber of the Caller object which creates this
     */
    public Call(Integer callID){
        this.callID = callID;
    }

    public Integer getCallID() {
        return callID;
    }
    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
