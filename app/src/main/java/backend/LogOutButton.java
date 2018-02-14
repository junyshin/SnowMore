package backend;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import project.ecse428.mcgill.ca.snowmore.Login;

public class LogOutButton extends AppCompatActivity {

    private static LogOutButton logout= new LogOutButton();
    private FirebaseAuth mAuth;

    public static void logOut(Button logOut){
        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                logout.logOutFunction();
            }
        });
    }
    private void logOutFunction(){
        Intent intent = new Intent(this, project.ecse428.mcgill.ca.snowmore.Login.class);
        mAuth = FirebaseAuth.getInstance();

        mAuth.signOut();
        startActivity(intent);
    }
}
