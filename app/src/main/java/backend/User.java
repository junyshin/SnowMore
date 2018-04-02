package backend;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elsa on 10/02/18.
 * check_password check_email and check_name Created by lucien on 2018-02-05.
 */

public class User {


    private String name;
    private String email;
    private String username;
    private String userID;

    private boolean password_checked;
    private boolean name_checked;
    private boolean email_checked;

    public User(String name, String email , String userID , String username){
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.username = username;

        password_checked = false;
        name_checked = false;
        email_checked = false;
    }

    public User(){
        // Default constructor
        password_checked = false;
        name_checked = false;
        email_checked = false;
    }


    public boolean check_password(String password) {
        password_checked = false;
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
        this.email_checked = false;
        String[] parsed = email.split("@");
        if(parsed.length == 2) {
            System.out.println(parsed[1]);
            String[] splitted = parsed[1].split("\\.");
            if(splitted.length >= 2){
                this.email_checked = true;
                this.email = email;
            }
        }
        return email_checked;
    }

    public boolean check_name (String name) {
        this.name_checked = false;
        String[] parsed = name.split(" ");
        if(parsed.length >= 2) {
            for (int i = 0 ; i < name.length() ; i++) {
                char x = name.charAt(i);
                if(Character.isDigit(x)) {
                    return name_checked;
                }
            }
            this.name_checked = true;
            this.name = name;
        }
        return name_checked;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public void setName(String name){
        this.check_name(name);
    }

    public void setEmail(String email){
        this.check_email(email);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        return result;
    }

    public boolean check_username (String username) {
        this.username = username;
        boolean username_checked = true;
        return username_checked;
    }
}