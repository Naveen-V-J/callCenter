public class Call {
    private final Integer callID;
    private final Integer callDuration;

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

    @Override
    public String toString() {
        return "Call ID: "+ callID;
    }
}
