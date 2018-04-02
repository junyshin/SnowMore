package project.ecse428.mcgill.ca.snowmore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import backend.ShovelingRequest;

public class UserShovelingRequest extends AppCompatActivity {

    private EditText streetAddress;
    private EditText et_city;
    private EditText et_postalCode;
    private EditText phoneNumber;
    private EditText requestDate;
    private EditText requestTime;
    private TextView error_message_streetAddress;
    private TextView error_message_city;
    private TextView error_message_postalCode;
    private TextView error_message_phoneNumber;
    private TextView error_message_requestDate;
    private TextView error_message_requestTime;
    private Button post_Button;
    private Button back_Button;
    private ShovelingRequest sr;
    private Dialog dialog = null;
    private Context context = null;

    private FirebaseAuth mAuth;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myRef;
    private Firebase mRootRef;
    private String postID;
    private DatabaseReference mRequestDB;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoveler_request);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            state = bundle.getInt("state");
        }
        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference();
        mRequestDB = FirebaseDatabase.getInstance().getReference().child("requestPost");
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();

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
        et_city = findViewById(R.id.city);
        et_postalCode = findViewById(R.id.postalCode);
        phoneNumber = findViewById(R.id.phoneNumber);
        requestDate = findViewById(R.id.requestDate);
        requestTime = findViewById(R.id.requestTime);

        error_message_city = (TextView) findViewById(R.id.error_message_city);
        error_message_postalCode = (TextView) findViewById(R.id.error_message_postCode);
        error_message_phoneNumber = (TextView) findViewById(R.id.error_message_phoneNumber);
        error_message_streetAddress = (TextView) findViewById(R.id.error_message_streetAddress);
        error_message_requestDate = (TextView) findViewById(R.id.error_message_requestDate);
        error_message_requestTime = (TextView) findViewById(R.id.error_message_requestTime);

        post_Button = (Button) findViewById(R.id.postButton);
        back_Button = (Button) findViewById(R.id.backButton);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.requestDate);
        txtTime = (EditText) findViewById(R.id.requestTime);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);

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
        if (TextUtils.isEmpty(et_city.getText().toString())) {
            error_message_city.setText("Please enter et_city");
            error_message_city.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkCity((et_city.getText().toString()))) {
                error_message_city.setText("Please enter a valid et_city name");
                error_message_city.setVisibility(View.VISIBLE);
            } else {
                error_message_city.setVisibility(View.INVISIBLE);
            }
        }
        if (TextUtils.isEmpty(et_postalCode.getText().toString())) {
            error_message_postalCode.setText("Please enter postal code");
            error_message_postalCode.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkPostalCode(et_postalCode.getText().toString())) {
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
        if (TextUtils.isEmpty(requestDate.getText().toString())) {
            error_message_requestDate.setText("Please enter the request Date");
            error_message_requestDate.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkPhoneNumber(requestDate.getText().toString())) {
                error_message_requestDate.setText("Please enter a valid Date");
                error_message_requestDate.setVisibility(View.VISIBLE);
            } else {
                error_message_requestDate.setVisibility(View.INVISIBLE);
            }
        }
        if (TextUtils.isEmpty(requestTime.getText().toString())) {
            error_message_requestTime.setText("Please enter the request Time");
            error_message_requestTime.setVisibility(View.VISIBLE);
        } else {
            if (!sr.checkPhoneNumber(requestTime.getText().toString())) {
                error_message_requestTime.setText("Please enter a valid Time");
                error_message_requestTime.setVisibility(View.VISIBLE);
            } else {
                error_message_requestTime.setVisibility(View.INVISIBLE);
            }
        }
        if (sr.checkCity(et_city.getText().toString()) && sr.checkPhoneNumber(phoneNumber.getText().toString())
                && sr.checkPostalCode(et_postalCode.getText().toString()) && sr.checkStreetAddress(streetAddress.getText().toString())
                && sr.checkRequestDate(requestDate.getText().toString()) && sr.checkRequestTime(requestTime.getText().toString())) {
            postRequest(streetAddress.getText().toString() , et_city.getText().toString() , et_postalCode.getText().toString() , phoneNumber.getText().toString() , requestDate.getText().toString() , requestTime.getText().toString() , FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }

    //Sign In button action
    public void backButton(View view) {
        Intent back = new Intent(this, ClientShovelerPage.class);
        back.putExtra("state" , state);
        startActivity(back);
    }


    private void postRequest(String address , String city, String postalCode, String phone, String date , String time , String userID) {
        ShovelingRequest shovelingRequest = new ShovelingRequest(address , city , postalCode , phone , date , time , userID);
        mRequestDB.push().setValue(shovelingRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                streetAddress.setText(null);
                phoneNumber.setText(null);
                requestDate.setText(null);
                requestTime.setText(null);
                et_city.setText(null);
                et_postalCode.setText(null);
                finish();
                Intent intent = new Intent(UserShovelingRequest.this , PendingRequestsTab.class);
                startActivity(intent);
//                Toast.makeText(UserShovelingRequest.this , "Success!" , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void btnDateTime(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

}