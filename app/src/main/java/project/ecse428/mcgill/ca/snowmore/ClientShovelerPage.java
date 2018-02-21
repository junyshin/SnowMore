package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by junshin on 2018-02-14.
 */

public class ClientShovelerPage extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myRef;
    private Firebase mRootRef;
    private String postID;
    private TextView city;

    private Dialog dialog = null;
    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shoveler_page);
        setUpVariables();
        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference().child("requestPost");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectData((Map<String,Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle database error
            }
        });
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Snow More");
        context = ClientShovelerPage.this;
        //setUpVariables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_registration, menu);
        return true;
    }

    public void setUpVariables() {
        city = (TextView) findViewById(R.id.city);
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

    //Shoveler request button action
    public void addButton(View view) {
        Intent request = new Intent(this, UserShovelingRequest.class);
        startActivity(request);
    }

    //Back button action
    public void backButton(View view) {
        Intent back = new Intent(this, WelcomePage.class);
        startActivity(back);
    }

    //Logout button action
    public void logoutButton(View view) {
        mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(this, project.ecse428.mcgill.ca.snowmore.Login.class);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(intent);
    }

    // details of request button
    public void detailsButton(View view) {
    }

    private void collectData(Map<String,Object> requestInfo) {

        ArrayList<String> address = new ArrayList<>();
        ArrayList<String> city = new ArrayList<>();
        ArrayList<String> phoneNumbers = new ArrayList<>();
        ArrayList<String> postalCode = new ArrayList<>();

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            address.add((String) singleUser.get("address"));
        }

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            city.add((String) singleUser.get("city"));
        }

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            phoneNumbers.add((String) singleUser.get("phone"));
        }

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            postalCode.add((String) singleUser.get("postalCode"));
        }

//        System.out.println(phoneNumbers.toString());
    }
}
