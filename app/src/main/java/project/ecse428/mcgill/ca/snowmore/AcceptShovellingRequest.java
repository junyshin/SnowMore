package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private Firebase mRootRef;
    private String postID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_shovelling_request);

        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference();
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Snow More");
        context = AcceptShovellingRequest.this;
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
}
