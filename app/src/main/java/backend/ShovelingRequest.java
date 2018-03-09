package backend;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by armenstepanians on 2018-02-12.
 */

public class ShovelingRequest {

    private String streetAddress;
    private String city;
    private String postalCode;
    private String shovelerNumber;
    private String clientNumber;
    private String requestDate;
    private String requestTime;
    private String userID;
    private String shovelerID;

    private boolean streetAddress_check;
    private boolean city_check;
    private boolean postalCode_check;
    private boolean phoneNumber_check;
    private boolean requestDate_check;
    private boolean requestTime_check;

    public ShovelingRequest(String streetAddress, String city, String postalCode, String phoneNumber, String requestDate, String requestTime , String userID) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.clientNumber = phoneNumber;
        this.requestTime = requestTime;
        this.requestDate = requestDate;
        this.userID = userID;
        streetAddress_check = false;
        city_check = false;
        postalCode_check = false;
        phoneNumber_check = false;
        requestDate_check = false;
        requestTime_check = false;
    }

    public ShovelingRequest() {
        // Default constructor
        streetAddress_check = false;
        city_check = false;
        postalCode_check = false;
        phoneNumber_check = false;
        requestDate_check = false;
        requestTime_check = false;
    }

    public boolean checkStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        if (streetAddress.isEmpty()) {
            streetAddress_check = false;
        } else {
            streetAddress_check = true;
        }
        return streetAddress_check;
    }

    public boolean checkCity(String city) {
        this.city = city;
        if (city.isEmpty()) {
            city_check = false;
        } else {
            city_check = true;
        }
        return city_check;
    }

    public boolean checkPostalCode(String postalCode) {
        this.postalCode = postalCode;
        if (postalCode.isEmpty()) {
            postalCode_check = false;
        } else {
            postalCode_check = true;
        }
        return postalCode_check;
    }

    public boolean checkPhoneNumber(String phoneNumber) {

        if (phoneNumber.isEmpty()) {
            phoneNumber_check = false;
        } else {
            phoneNumber_check = true;
            //this.clientNumber = phoneNumber;
        }
        return phoneNumber_check;
    }

    public boolean checkRequestDate(String requestDate) {
        this.requestDate = requestDate;
        if (requestDate.isEmpty()) {
            requestDate_check = false;
        } else {
            requestDate_check = true;
        }
        return requestDate_check;
    }

    public boolean checkRequestTime(String requestTime) {
        this.requestTime = requestTime;
        if (requestTime.isEmpty()) {
            requestTime_check = false;
        } else {
            requestTime_check = true;
        }
        return requestTime_check;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public String getCity() {
        return this.city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getClientNumber() {
        return this.clientNumber;
    }

    public String getShovelerNumber() {
        return this.shovelerNumber;
    }
  
    public String getRequestDate() {
        return this.requestDate;
    }

    public String getRequestTime() {
        return this.requestTime;
    }

    public void setStreetAddress(String address) {
        this.streetAddress = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setClientNumber(String phoneNumber) {
        this.clientNumber = phoneNumber;
    }

    public void setShovelerNumber(String shovelerNumber) {
        this.shovelerNumber = shovelerNumber;
    }
      
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public void setShovelerID(String shovelerID) {this.shovelerID = shovelerID;}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("streetAddress", streetAddress);
        result.put("city", city);
        result.put("postalCode", postalCode);
        result.put("clientNumber", clientNumber);
        result.put("requestDate", requestDate);
        result.put("requestTime", requestTime);
        result.put("shovelerNumber", shovelerNumber);
        result.put("shovelerID", shovelerID);
        return result;
    }
}