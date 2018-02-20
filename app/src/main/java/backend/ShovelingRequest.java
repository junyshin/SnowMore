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
    private String phoneNumber;

    private boolean streetAddress_check;
    private boolean city_check;
    private boolean postalCode_check;
    private boolean phoneNumber_check;

    private User madeRequest;
    private User acceptedRequest;

    public ShovelingRequest(String streetAddress, String city, String postalCode, String phoneNumber){
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        streetAddress_check = false;
        city_check = false;
        postalCode_check = false;
        phoneNumber_check = false;
    }

    public ShovelingRequest(){
        // Default constructor
        streetAddress_check = false;
        city_check = false;
        postalCode_check = false;
        phoneNumber_check = false;
    }

    public boolean checkStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        if (streetAddress.isEmpty()){
            streetAddress_check = false;
        } else {
            streetAddress_check = true;
        }
        return streetAddress_check;
    }

    public boolean checkCity(String city) {
        this.city = city;
        if (city.isEmpty()){
            city_check = false;
        } else {
            city_check = true;
        }
        return city_check;
    }

    public boolean checkPostalCode(String postalCode) {
        this.postalCode = postalCode;
        if (postalCode.isEmpty()){
            postalCode_check =  false;
        } else {
            postalCode_check = true;
        }
        return postalCode_check;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        if (phoneNumber.isEmpty()){
            phoneNumber_check = false;
        } else {
            phoneNumber_check = true;
        }
        return phoneNumber_check;
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

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public User getMadeRequest() {
        return madeRequest;
    }

    public User getAcceptedRequest() {
        return acceptedRequest;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMadeRequest(User madeRequest) {
        this.madeRequest = madeRequest;
    }

    public void setAcceptedRequest(User acceptedRequest) {
        this.acceptedRequest = acceptedRequest;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("streetAddress", streetAddress);
        result.put("city", city);
        result.put("postalCode", postalCode);
        result.put("phoneNumber", phoneNumber);
        return result;
    }
}
