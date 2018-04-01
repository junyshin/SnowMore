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

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            goToLogin();
        }
    }

    private void goToLogin() {
        startActivity(new Intent(WelcomePage.this , Login.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAuth.getCurrentUser() == null) {
            goToLogin();
        }
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

    public void myAccRequestsButton(View view){
        Intent intent = new Intent(this, AcceptedRequestsTab.class);
        startActivity(intent);
    }

    public void myPendRequestsButton(View view){
        Intent intent = new Intent(this, PendingRequestsTab.class);
        startActivity(intent);
    }

    public void myShovelerRequestsButton(View view) {
        Intent intent = new Intent(this, ShovelerAcceptedRequests.class);
    }
}
