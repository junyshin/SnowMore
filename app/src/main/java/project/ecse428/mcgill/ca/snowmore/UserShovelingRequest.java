package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import backend.ShovelingRequest;
import backend.User;

public class UserShovelingRequest extends AppCompatActivity {

    private EditText streetAddress;
    private EditText city;
    private EditText postalCode;
    private EditText phoneNumber;
    private TextView error_message_streetAddress;
    private TextView error_message_city;
    private TextView error_message_postalCode;
    private TextView error_message_phoneNumber;
    private Button post_Button;
    private Button back_Button;
    private ShovelingRequest sr;
    private Dialog dialog = null;
    private Context context = null;

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myRef;
    private Firebase mRootRef;
    private String postID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoveler_request);

        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference();
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Snow More");
        context = UserShovelingRequest.this;
        setUpVariables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //UI Initialization
    public void setUpVariables() {
        streetAddress = findViewById(R.id.streetAddress);
        city = findViewById(R.id.city);
        postalCode = findViewById(R.id.postalCode);
        phoneNumber = findViewById(R.id.phoneNumber);

        error_message_city = (TextView) findViewById(R.id.error_message_city);
        error_message_postalCode = (TextView) findViewById(R.id.error_message_postCode);
        error_message_phoneNumber = (TextView) findViewById(R.id.error_message_phoneNumber);
        error_message_streetAddress = (TextView) findViewById(R.id.error_message_streetAddress);

        post_Button = (Button) findViewById(R.id.postButton);
        back_Button = (Button) findViewById(R.id.backButton);

        sr = new ShovelingRequest();
    }

    //Registration button action
    public void postRequest(View view) {
        if (TextUtils.isEmpty(streetAddress.getText().toString())) {
            error_message_streetAddress.setText("Please enter street address");
            error_message_streetAddress.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkStreetAddress(streetAddress.getText().toString())) {
                error_message_streetAddress.setText("Invalid street address");
                error_message_streetAddress.setVisibility(View.VISIBLE);
            } else {
                error_message_streetAddress.setVisibility(View.INVISIBLE);
            }
        }
        if (TextUtils.isEmpty(city.getText().toString())) {
            error_message_city.setText("Please enter city");
            error_message_city.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkCity((city.getText().toString()))) {
                error_message_city.setText("Please enter a valid city name");
                error_message_city.setVisibility(View.VISIBLE);
            } else {
                error_message_city.setVisibility(View.INVISIBLE);
            }
        }
        if (TextUtils.isEmpty(postalCode.getText().toString())) {
            error_message_postalCode.setText("Please enter postal code");
            error_message_postalCode.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkPostalCode(postalCode.getText().toString())) {
                error_message_postalCode.setText("Please enter a valid postal code");
                error_message_postalCode.setVisibility(View.VISIBLE);
            } else {
                error_message_postalCode.setVisibility(View.INVISIBLE);
            }
        }
        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            error_message_phoneNumber.setText("Please enter your phone number");
            error_message_phoneNumber.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkPhoneNumber(phoneNumber.getText().toString())) {
                error_message_phoneNumber.setText("Please enter a valid phone number");
                error_message_phoneNumber.setVisibility(View.VISIBLE);
            } else {
                error_message_phoneNumber.setVisibility(View.INVISIBLE);
            }
        }
        if (sr.checkCity(city.getText().toString()) && sr.checkPhoneNumber(phoneNumber.getText().toString()) && sr.checkPostalCode(postalCode.getText().toString()) && sr.checkStreetAddress(streetAddress.getText().toString())) {
            createRequest();
        }
    }

    //Sign In button action
    public void backButton(View view) {
        Intent back = new Intent(this, ClientShovelerPage.class);
        startActivity(back);
    }

    public void createRequest() {
        mRootRef = new Firebase("https://snowmore-3e355.firebaseio.com/requestPost");
        FirebaseUser fb_madeRequest = mAuth.getCurrentUser();
        //User madeRequest = new User()

        ShovelingRequest requestShoveler = new ShovelingRequest(streetAddress.getText().toString(), city.getText().toString(),
                postalCode.getText().toString(), phoneNumber.getText().toString(), madeRequest);


        postID = fb_request.getUid();
        DatabaseReference postRef = myRef.child("requestPost").child(postID);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("request_info", requestShoveler.toMap());
        mRootRef.push().updateChildren(dataMap);

        Toast toast = Toast.makeText(context, "Successfully Sent Request", Toast.LENGTH_SHORT);
        toast.show();
    }

}