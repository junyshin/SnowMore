package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import backend.LoginBackend;



public class Login extends AppCompatActivity {

    private EditText email_login;
    private EditText password_login;
    private TextView error_message_password_login;
    private TextView error_message_email_login;

    private Button registration_button;
    private Button login_button;
    private LoginBackend loginBackend;
    private Dialog dialog = null;
    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
       // toolbar.setTitle("Snow More");
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

        if(TextUtils.isEmpty(email_login.getText().toString())) {
            error_message_email_login.setText("Please enter email");
            error_message_email_login.setVisibility(View.VISIBLE);
        }
        else {
            if(!loginBackend.check_email_login(email_login.getText().toString())) {
                error_message_email_login.setText("Invalid email");
                error_message_email_login.setVisibility(View.VISIBLE);
            }
            else {
                error_message_email_login.setVisibility(View.INVISIBLE);
            }
        }
        if(TextUtils.isEmpty(password_login.getText().toString())) {
            error_message_password_login.setText("Please enter password");
            error_message_password_login.setVisibility(View.VISIBLE);;
        }
        else {
            if(!loginBackend.check_password_login((password_login.getText().toString()))) {
                error_message_password_login.setText("Invalid password");
                error_message_password_login.setVisibility(View.VISIBLE);
            }
            else {
                error_message_password_login.setVisibility(View.INVISIBLE);
            }
        }

        if(loginBackend.check_email_login(email_login.getText().toString()) && loginBackend.check_password_login((password_login.getText().toString()))) {
            Toast toast = Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_SHORT);
            toast.show();
        }

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
