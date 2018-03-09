package project.ecse428.mcgill.ca.snowmore;

import org.junit.Test;

import java.util.Map;

import backend.ShovelingRequest;

import static org.junit.Assert.assertEquals;

/**
 * Created by Michael Maatouk on 2/22/2018.
 */
public class ShovelingRequestTest {

    @Test
    public void checkStreetAdressTestValid(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkStreetAddress("Saint-Catherine"), true);



    }

    @Test
    public void checkStreetAdressTestError(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkStreetAddress(""), false);



    }

    @Test
    public void checkCityTestValid(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkCity("Montreal"), true);



    }

    @Test
    public void checkCityTestError(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkStreetAddress(""), false);



    }

    @Test
    public void checkPostalCodeTestValid(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkPostalCode("H213P9"), true);



    }

    @Test
    public void checkPostalCodeTestError(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkStreetAddress(""), false);



    }

    @Test
    public void checkPhoneNumberTestValid(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkPhoneNumber("5149209289"), true);



    }

    @Test
    public void checkPhoneNumberTestError(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkPhoneNumber(""), false);



    }

    @Test
    public void checkRequestDateTestValid(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkRequestDate("01/02/2018"), true);



    }

    @Test
    public void checkRequestDateTestError(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkRequestDate(""), false);



    }

    @Test
    public void checkRequestTimeTestValid(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkRequestDate("14:00"), true);



    }

    @Test
    public void checkRequestTimeTestError(){
        ShovelingRequest shovelingRequest = new ShovelingRequest();
        assertEquals(shovelingRequest.checkRequestDate(""), false);



    }

    @Test
    public void getFunctionsTest(){

        ShovelingRequest shovelingRequest = new ShovelingRequest("Saint-Catherine","Montreal","H2C4P9","514090867","01/02/2018","14:02","1");


        assertEquals(shovelingRequest.getStreetAddress(),"Saint-Catherine");
        assertEquals(shovelingRequest.getCity(),"Montreal");
        assertEquals(shovelingRequest.getPostalCode(),"H2C4P9");
        assertEquals(shovelingRequest.getClientNumber(),"514090867");
        assertEquals(shovelingRequest.getRequestDate(),"01/02/2018");
        assertEquals(shovelingRequest.getRequestTime(),"14:02");
        assertEquals(shovelingRequest.getUserID(),"1");



    }

    @Test
    public void setFunctionsTest(){

        ShovelingRequest shovelingRequest = new ShovelingRequest();

        shovelingRequest.setStreetAddress("Saint-Catherine");
        shovelingRequest.setCity("Montreal");
        shovelingRequest.setPostalCode("H2C4P9");
        shovelingRequest.setClientNumber("514090867");
        shovelingRequest.setRequestDate("01/02/2018");
        shovelingRequest.setRequestTime("14:02");
        shovelingRequest.setUserID("1");

        assertEquals(shovelingRequest.getStreetAddress(),"Saint-Catherine");
        assertEquals(shovelingRequest.getCity(),"Montreal");
        assertEquals(shovelingRequest.getPostalCode(),"H2C4P9");
        assertEquals(shovelingRequest.getClientNumber(),"514090867");
        assertEquals(shovelingRequest.getRequestDate(),"01/02/2018");
        assertEquals(shovelingRequest.getRequestTime(),"14:02");
        assertEquals(shovelingRequest.getUserID(),"1");



    }

    @Test
    public void toMapTest(){

        ShovelingRequest shovelingRequest = new ShovelingRequest("Saint-Catherine","Montreal","H2C4P9","514090867","01/02/2018","14:02","1");
        shovelingRequest.setShovelerID("1");
        Map map = shovelingRequest.toMap();
        assertEquals( map.get("streetAddress"),"Saint-Catherine");
        assertEquals(map.get("city"),"Montreal");
        assertEquals(map.get("postalCode"),"H2C4P9");
        assertEquals(map.get("clientNumber"),"514090867");
        assertEquals(map.get("requestDate"),"01/02/2018");
        assertEquals(map.get("requestTime"),"14:02");
       assertEquals(map.get("shovelerID"),"1");




    }


}
