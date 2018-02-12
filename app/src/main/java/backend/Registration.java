package backend;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import android.support.annotation.NonNull;
import android.util.Log;


/**
 * Created by lucien on 2018-02-05.
 */

public class Registration {
    private String password;
    private String email;
    private String name;
    private Firebase users_table;


    public boolean check_password(String password) {
        this.password = password;
        boolean password_checked = false;
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;
        if(password.length() >= 8) {
            for(int i = 0 ; i < password.length() ; i++) {
                char x = password.charAt(i);
                if(Character.isUpperCase(x)) {
                    hasUpperCase = true;
                }
                if(Character.isLowerCase(x)) {
                    hasLowerCase = true;
                }
                if(Character.isDigit(x)) {
                    hasDigit = true;
                }
                if((x >= '!' && x <= '/') || (x >= ':' && x <= '@') || (x >= '[' && x <= '`') || (x >= '{' && x<= '~')) {
                    hasSpecialCharacter = true;
                }
                if(hasDigit && hasLowerCase && hasUpperCase && hasLowerCase && hasSpecialCharacter) {
                    password_checked = true;
                    break;
                }
            }
        }
        return password_checked;
    }

    public boolean check_email(String email) {
        this.email = email;
        boolean email_checked = false;
        String[] parsed = email.split("@");
        if(parsed.length == 2) {
            System.out.println(parsed[1]);
            String[] splitted = parsed[1].split("\\.");
            if(splitted.length == 2){
                email_checked = true;
            }
        }
        return email_checked;
    }

    public boolean check_name (String name) {
        this.name = name;
        boolean name_checked = false;
        String[] parsed = name.split(" ");
        if(parsed.length >= 2) {
            for (int i = 0 ; i < name.length() ; i++) {
                char x = name.charAt(i);
                if(Character.isDigit(x)) {
                    return name_checked;
                }
            }
            name_checked = true;
        }
        return name_checked;
    }

}
