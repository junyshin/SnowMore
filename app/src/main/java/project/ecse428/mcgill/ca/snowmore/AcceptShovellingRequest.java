package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import backend.ShovelingRequest;

/**
 * Created by junshin on 2018-02-18.
 */

public class AcceptShovellingRequest extends AppCompatActivity {

    private EditText shovelerNumber;
    private TextView clientAddress;
    private TextView clientNumber;
    private TextView requestTime;
    private TextView requestDate;
    private TextView clientName;
    private TextView error_message_phoneNumber;
    private Button accept_button;
    private Button back_button;
    private Button logout_button;
    private Dialog dialog = null;
    private Context context = null;
    private ShovelingRequest shovelingRequest;

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference reqRef;
    private ValueEventListener reqRefEventListener;

    private String postID = null;

    //private Firebase mRootRef;
    //private String postID = "-L5lxOmHA8Qa8W18aWyV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_shovelling_request);

        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.postID = extras.getString("requestID");
            Log.d("Got request ID: ", this.postID);
        }

        context = AcceptShovellingRequest.this;
        setUpVariables();

        reqRef = myRef.child("requestPost").child(postID);
        reqRefEventListener = reqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //check if value exists
                if (dataSnapshot.exists()) {
                shovelingRequest = dataSnapshot.getValue(ShovelingRequest.class);
                Log.d("Got Request City", shovelingRequest.getCity());
                Log.d("Got Request Address", shovelingRequest.getStreetAddress());
                Log.d("Got Request postal code", shovelingRequest.getPostalCode());

                clientAddress.setText(shovelingRequest.getStreetAddress());
                clientAddress.append(", " + shovelingRequest.getCity());
                clientAddress.append(", " + shovelingRequest.getPostalCode());
                clientNumber.setText(shovelingRequest.getClientNumber());

                requestDate.setText(shovelingRequest.getRequestDate());
                requestTime.setText(shovelingRequest.getRequestTime());
                }

                else{
                    Toast.makeText(AcceptShovellingRequest.this, "Request no longer available.",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
                public void onCancelled(DatabaseError error){
                    Log.d("Database Error", error.getMessage());
                }
        });
    }

    //Back button action
    public void backToClientShovelerPage(View view) {
        Intent back = new Intent(this, ClientShovelerPage.class);
        startActivity(back);
    }

    //UI Initialization
    public void setUpVariables() {
        shovelerNumber = findViewById(R.id.shovelerPhone);

        clientAddress = (TextView) findViewById(R.id.addressClient);
        clientName = (TextView) findViewById(R.id.nameClient);
        clientNumber = (TextView) findViewById(R.id.clientPhone);
        requestDate = (TextView) findViewById(R.id.dateRequested);
        requestTime = (TextView) findViewById(R.id.timeRequested);

        error_message_phoneNumber = (TextView) findViewById(R.id.error_message_phoneNumber);

        accept_button = (Button) findViewById(R.id.acceptRequestButton);
        back_button = (Button) findViewById(R.id.backButton);
        logout_button = (Button) findViewById(R.id.logoutButton);

    }
  
    public void acceptButton(View view) {
        // check phone number
        if (TextUtils.isEmpty(shovelerNumber.getText().toString())) {
            error_message_phoneNumber.setText("Please enter your phone number");
            error_message_phoneNumber.setVisibility(View.VISIBLE);
        } else if (shovelerNumber.getText().toString().length() != 10) {
            error_message_phoneNumber.setText("Please enter a valid 10-digit phone number");
            error_message_phoneNumber.setVisibility(View.VISIBLE);
        } else {
            error_message_phoneNumber.setVisibility(View.INVISIBLE);
            shovelingRequest.setShovelerNumber(shovelerNumber.getText().toString());
            // add shovelerId to request Object
            String shovelerID = mAuth.getCurrentUser().getUid();
            shovelingRequest.setshovelerID(shovelerID);
            Map<String, Object> reqMap;
            reqMap = shovelingRequest.toMap();


            // Should there be a confirmation dialog?

            // move request from pending to accepted
            DatabaseReference acc_req = myRef.child("accepted requests").child(postID);

            // add a listener to the setValue task
            acc_req.updateChildren(reqMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // setting request in pending requests complete
                    if (task.isSuccessful()) {
                        // if successful remove the previously set eventlistener
                        reqRef.removeEventListener(reqRefEventListener);

                        // if successful, we can remove the request from the pending table
                        // add a listener for removing the request
                        reqRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AcceptShovellingRequest.this, "Request Accepted Successfully!.",
                                            Toast.LENGTH_LONG).show();
                                    //TODO: choose next activity, pass intent
                                    finish();
                                } else {
                                    // error updating firebase
                                    Toast.makeText(AcceptShovellingRequest.this, "Error!" + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        // error adding request to accepted list
                        Toast.makeText(AcceptShovellingRequest.this, "Error!" + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
