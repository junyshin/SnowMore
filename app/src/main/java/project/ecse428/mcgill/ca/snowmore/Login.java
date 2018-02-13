package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import backend.LoginBackend;



public class Login extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText email_login;
    private EditText password_login;
    private TextView error_message_password_login;
    private TextView error_message_email_login;

    private Button registration_button;
    private Button login_button;
    private LoginBackend loginBackend;
    private Dialog dialog = null;
    private Context context = null;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = Login.this;
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

    public void setUpVariables() {

        email_login = (EditText) findViewById(R.id.emailLogin);
        password_login = (EditText) findViewById(R.id.passwordLogin);
        error_message_password_login = (TextView) findViewById(R.id.error_message_password_login);
        error_message_email_login = (TextView) findViewById(R.id.error_message_email_login);


        login_button = (Button) findViewById(R.id.loginbutton);
        registration_button = (Button) findViewById(R.id.registerbuttonLogin);
        loginBackend = new LoginBackend();
    }

    //Sign In button action
    public void signInButton(View view) {
        login();
        Intent welcome = new Intent(this , WelcomePage.class);
        startActivity(welcome);

    }

    private void login() {
        mAuth.signInWithEmailAndPassword(email_login.getText().toString(), password_login.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }

    //Registration button action
    public void registerButton(View view) {
        Intent register = new Intent(this , UserRegistration.class);
        startActivity(register);
    }

    //Forgot Password button action
    public void forgotButton(View view) {
    }



}
