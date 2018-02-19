package backend;

/**
 * Created by junshin on 2018-02-18.
 */

public class AcceptRequest {

    private String clientNumber;
    private String clientAddress;
    private String requestDate;
    private String requestTime;
    private String clientName;

    private String shovelerNumber;

    private boolean clientNumber_check;
    private boolean clientAddress_check;
    private boolean requestDate_check;
    private boolean requestTime_check;

    private boolean shovelerNumber_check;

    public AcceptRequest(String clientName, String clientAddress, String requestDate, String requestTime, String clientNumber, String shovelerNumber){
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientNumber = clientNumber;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.shovelerNumber = shovelerNumber;

        this.clientAddress_check = false;
        this.clientNumber_check = false;
        this.requestDate_check = false;
        this.requestTime_check = false;
        this.shovelerNumber_check = false;
    }

    public AcceptRequest(){
        this.clientAddress_check = false;
        this.clientNumber_check = false;
        this.requestDate_check = false;
        this.requestTime_check = false;
        this.shovelerNumber_check = false;
    }

    public String getClientNumber(){
        return this.clientNumber;
    }

    public String getClientAddress(){
        return this.clientAddress;
    }

    public String getClientName(){
        return this.clientName;
    }

    public String getRequestDate(){
        return this.requestDate;
    }

    public String getRequestTime(){
        return this.requestTime;
    }

    public String getShovelerNumber(){
        return this.shovelerNumber;
    }

    public void setClientNumber(String clientNumber){
        this.clientNumber = clientNumber;
    }

    public void setClientAddress(String clientAddress){
        this.clientAddress = clientAddress;
    }

    public void setRequestDate(String requestDate){
        this.requestDate = requestDate;
    }

    public void setRequestTime(String requestTime){
        this.requestTime = requestTime;
    }

    public void setClientName(String clientName){
        this.clientName = clientName;
    }

    public void setShovelerNumber(String shovelerNumber){
        this.shovelerNumber = shovelerNumber;
    }

    public boolean checkShoverlerNumber(String shovelerNumber) {
        this.shovelerNumber = shovelerNumber;
        if (shovelerNumber.isEmpty()){
            shovelerNumber_check = false;
        } else {
            shovelerNumber_check = true;
        }
        return shovelerNumber_check;
    }
}
