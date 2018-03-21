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

import backend.User;


public class Login extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText email_login;
    private EditText password_login;
    private TextView error_message_password_login;
    private TextView error_message_email_login;

    private User user;

    private Button registration_button;
    private Button login_button;
    private AlertDialog dialog;
    private Context context = null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
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
        user = new User();
    }

    //Sign In button action leads to a welcome page FOR NOW! (TEST)
    public void signInButton(View view) {
        login();
    }

    private void login() {
        if(TextUtils.isEmpty(email_login.getText().toString())) {
            error_message_email_login.setText("Please enter email");
            error_message_email_login.setVisibility(View.VISIBLE);
        }
        else {
            if(!user.check_email(email_login.getText().toString())) {
                error_message_email_login.setText("Invalid email");
                error_message_email_login.setVisibility(View.VISIBLE);
            }
            else {
                error_message_email_login.setVisibility(View.INVISIBLE);
            }
        }
        if(TextUtils.isEmpty(password_login.getText().toString())) {
            error_message_password_login.setText("Please enter email");
            error_message_password_login.setVisibility(View.VISIBLE);
        }
        else {
            if(!user.check_password(password_login.getText().toString())) {
                error_message_password_login.setText("Invalid email");
                error_message_password_login.setVisibility(View.VISIBLE);
            }
            else {
                error_message_password_login.setVisibility(View.INVISIBLE);
            }
        }
        if(!TextUtils.isEmpty(email_login.getText().toString()) && !TextUtils.isEmpty(password_login.getText().toString())) {
            checkLogin();
        }
    }

    public void checkLogin(){
        mAuth.signInWithEmailAndPassword(email_login.getText().toString(), password_login.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent welcome = new Intent(context , ClientShovelerPage    .class);
                            startActivity(welcome);
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
        if(TextUtils.isEmpty(email_login.getText().toString())) {
            createDialog("ERROR" , "Please enter email address");
        }
        else {
            mAuth.sendPasswordResetEmail(email_login.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                        createDialog("Email sent" , "Reset password link successfully sent to the following email: " + email_login.getText().toString());
                    }
                }
            });
        }
    }
    public void createDialog(String title , String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
