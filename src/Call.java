/**
 * Represents a call
 */
public class Call {
    private final Integer callID;

    /**
     * @param callID Integer same as the CallNumber of the Caller object which creates this
     */
    public Call(Integer callID){
        this.callID = callID;
    }

    public Integer getCallID() {
        return callID;
    }

}
