package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import backend.User;

public class UserRegistration extends AppCompatActivity {

    private EditText name_edittxt;
    private EditText email_edittxt;
    private EditText password_edittxt;
    private EditText username_edittxt;
    private TextView error_message_password;
    private TextView error_message_email;
    private TextView error_message_fullname;
    private TextView error_message_username;
    private Button registration_button;
    private Button signin_button;
    private User user;
    private Dialog dialog = null;
    private Context context = null;

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference mUsersDB;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        setUpVariables();
        context = UserRegistration.this;
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

    //UI Initialization
    public void setUpVariables() {
        name_edittxt = (EditText) findViewById(R.id.fullname);
        email_edittxt = (EditText) findViewById(R.id.email);
        password_edittxt = (EditText) findViewById(R.id.password);
        username_edittxt = (EditText) findViewById(R.id.username);
        error_message_password = (TextView) findViewById(R.id.error_message_password);
        error_message_email = (TextView) findViewById(R.id.error_message_email);
        error_message_fullname = (TextView) findViewById(R.id.error_message_fullname);
        error_message_username = (TextView) findViewById(R.id.error_message_username);
        signin_button = (Button) findViewById(R.id.signinbutton);
        registration_button = (Button) findViewById(R.id.registerbutton);
        user = new User();
        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference();
        mUsersDB = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    //Sign In button action
    public void signInButton(View view) {
        Intent sign_in = new Intent(this , Login.class);
        startActivity(sign_in);
    }

    //Registration button action
    public void registerButton(View view) {
        if(TextUtils.isEmpty(email_edittxt.getText().toString())) {
            error_message_email.setText("Please enter email");
            error_message_email.setVisibility(View.VISIBLE);
        }
        else {
            if(!user.check_email(email_edittxt.getText().toString())) {
                error_message_email.setText("Invalid email");
                error_message_email.setVisibility(View.VISIBLE);
            }
            else {
                error_message_email.setVisibility(View.INVISIBLE);
            }
        }
        if(TextUtils.isEmpty(username_edittxt.getText().toString())) {
            error_message_username.setText("Please enter username");
            error_message_username.setVisibility(View.VISIBLE);
        }
        else {
            if(!user.check_username(username_edittxt.getText().toString())) {
                error_message_username.setText("Username has already been used");
                error_message_username.setVisibility(View.VISIBLE);
            }
            else {
                error_message_username.setVisibility(View.INVISIBLE);
            }
        }
        if(TextUtils.isEmpty(password_edittxt.getText().toString())) {
            error_message_password.setText("Please enter password");
            error_message_password.setVisibility(View.VISIBLE);;
        }
        else {
            if(!user.check_password((password_edittxt.getText().toString()))) {
                error_message_password.setText("Password should be at least 8 characters long, and must contain uppercase and lowercase letters, at least one digit and one special character");
                error_message_password.setVisibility(View.VISIBLE);
            }
            else {
                error_message_password.setVisibility(View.INVISIBLE);
            }
        }
        if(TextUtils.isEmpty(name_edittxt.getText().toString())) {
            error_message_fullname.setText("Please enter your full name");
            error_message_fullname.setVisibility(View.VISIBLE);
        }
        else {
            if (!user.check_name(name_edittxt.getText().toString())) {
                error_message_fullname.setText("Please enter a valid full name");
                error_message_fullname.setVisibility(View.VISIBLE);
            }
            else {
                error_message_fullname.setVisibility(View.INVISIBLE);
            }
        }
        if(user.check_email(email_edittxt.getText().toString()) && user.check_password((password_edittxt.getText().toString())) && user.check_name(name_edittxt.getText().toString())) {
            createDialog();
        }
    }

    private void showAlertDialog(String title , String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void addNewUser(final String name , String email , String password , final String username) {
        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    // error registering user
                    showAlertDialog("Error!" , task.getException().getMessage());
                }
                else {
                    //success
                    final FirebaseUser currentUser = task.getResult().getUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();
                    currentUser.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            User newUser = new User(currentUser.getDisplayName() , currentUser.getEmail() , currentUser.getUid() , username);
                            mUsersDB.child(currentUser.getUid()).setValue(newUser);
                            // take the user home
                            finish();
                            startActivity(new Intent(UserRegistration.this , Login.class));
                        }
                    });
                }
            }
        });
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm email?");
        builder.setMessage(email_edittxt.getText().toString());
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addNewUser(name_edittxt.getText().toString() , email_edittxt.getText().toString() , password_edittxt.getText().toString() , username_edittxt.getText().toString());
//                startActivity(new Intent(UserRegistration.this , Login.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}