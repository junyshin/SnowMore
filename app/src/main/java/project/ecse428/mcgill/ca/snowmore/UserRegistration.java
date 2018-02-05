package project.ecse428.mcgill.ca.snowmore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import backend.Registration;

public class UserRegistration extends AppCompatActivity {

    private EditText fullname;
    private EditText email;
    private EditText password;
    private TextView error_message_password;
    private TextView error_message_email;
    private TextView error_message_fullname;
    private Button registration_button;
    private Button signin_button;
    private Registration registration;
    private Dialog dialog = null;
    private Context context = null;
    private TextView email_dialog;
    private Button cancel_button_dialog;
    private Button confirm_button_dialog;
    private UserRegistration userRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Snow More");
        context = UserRegistration.this;
        dialog = new Dialog(context);
        setUpVariables();
        setUpDialog();
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
        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        error_message_password = (TextView) findViewById(R.id.error_message_password);
        error_message_email = (TextView) findViewById(R.id.error_message_email);
        error_message_fullname = (TextView) findViewById(R.id.error_message_fullname);

        signin_button = (Button) findViewById(R.id.signinbutton);
        registration_button = (Button) findViewById(R.id.registerbutton);
        registration = new Registration();
        userRegistration = new UserRegistration();
    }

    //Dialog Initialization
    public void setUpDialog() {
        dialog.setContentView(R.layout.confirmation_dialog);
        cancel_button_dialog = (Button) dialog.findViewById(R.id.cancel_button);
        confirm_button_dialog = (Button) dialog.findViewById(R.id.confirm_button);
        email_dialog = (TextView) dialog.findViewById(R.id.email_dialog);
    }


    //Sign In button action
    public void signInButton(View view) {
        Intent sign_in = new Intent(this , Login.class);
        startActivity(sign_in);
    }

    //Registration button action
    public void registerButton(View view) {
        if(email.getText().toString() == null) {
            error_message_email.setText("Please enter email");
            error_message_email.setVisibility(View.VISIBLE);
        }
        else {
            if(!registration.check_email(email.getText().toString())) {
                error_message_email.setText("Invalid email");
                error_message_email.setVisibility(View.VISIBLE);
            }
        }
        if(password.getText().toString() == null) {
            error_message_password.setText("Please enter password");
            error_message_password.setVisibility(View.VISIBLE);;
        }
        else {
            if(!registration.check_password((password.getText().toString()))) {
                error_message_password.setText("Password should be at least 8 characters long, and must contain uppercase, lowercase letters, digit, and special character.");
                error_message_password.setVisibility(View.VISIBLE);
            }
        }
        if(fullname.getText().toString() == null) {
            error_message_fullname.setText("Please enter your full name");
            error_message_fullname.setVisibility(View.VISIBLE);
        }
        else {
            if (!registration.check_name(fullname.getText().toString())) {
                error_message_fullname.setText("Please enter your full name");
                error_message_fullname.setVisibility(View.VISIBLE);
            }
        }
        if(registration.check_email(email.getText().toString()) && registration.check_password((password.getText().toString())) && registration.check_name(fullname.getText().toString())) {
            email_dialog.setText(email.getText().toString());
            dialog.show();
        }
    }

    //Dialog cancel button
    public void cancelDialog(View view) {
        dialog.dismiss();
    }

    //Dialog confirm button
    public void confirmDialog(View view) {
        Intent signin = new Intent(this , Login.class);
        startActivity(signin);
    }
}
