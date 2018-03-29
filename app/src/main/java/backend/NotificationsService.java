package backend;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by elsa on 29/03/18.
 */

public class NotificationsService extends FirebaseInstanceIdService {
    public static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh(){
        String reg_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, reg_token);

    }
}