package project.ecse428.mcgill.ca.snowmore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import backend.LogOutButton;




public class WelcomePage extends AppCompatActivity {

    private static Button logout_button;
    private FirebaseAuth mAuth;
    private Button clientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clientButton = (Button) findViewById(R.id.Client);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        logout_button = (Button) findViewById(R.id.logoutButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                logOutFunction(v);

            }
        });
    }

    public void logOutFunction(View view){
        mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(this, project.ecse428.mcgill.ca.snowmore.Login.class);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(intent);
    }

    public void clientButton(View view){
        Intent intent = new Intent(this, ClientShovelerPage.class);
        startActivity(intent);
    }

    public void requestButton(View view){
        Intent intent = new Intent(this, UserShovelingRequest.class);
        startActivity(intent);
    }
}
