package backend;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by elsa on 09/02/18.
 *
 */

public class Fireapp extends Application {
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
