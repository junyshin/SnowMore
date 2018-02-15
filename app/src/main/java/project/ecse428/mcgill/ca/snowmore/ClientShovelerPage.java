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

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private Dialog dialog = null;
    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shoveler_page);

        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference();
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
}
