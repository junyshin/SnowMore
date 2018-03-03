package project.ecse428.mcgill.ca.snowmore;

import android.view.View;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Michael Maatouk on 2/22/2018.
 */
public class ShovelingRequestTest {

    @Test
    public void postRequestTest(){

        View view = null;
        UserShovelingRequest userShovelingRequest = new UserShovelingRequest();
        userShovelingRequest.streetAddress = null;
        userShovelingRequest.error_message_streetAddress = null;
        userShovelingRequest.streetAddress.setText("Saint-Catherine");
      //  Bundle bundle = new Bundle();
       // userShovelingRequest.onCreate(bundle);

        userShovelingRequest.postRequest(view);

        assertEquals(userShovelingRequest.error_message_streetAddress.getText(), "Please enter street address");
    }


}
