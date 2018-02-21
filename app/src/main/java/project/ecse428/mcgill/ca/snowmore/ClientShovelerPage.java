package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

import backend.ShovelingRequest;

/**
 * Created by junshin on 2018-02-14.
 */

public class ClientShovelerPage extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myRef;
    private Firebase mRootRef;
    private String postID;

    private Dialog dialog = null;
    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shoveler_page);

        mAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = myFirebaseDatabase.getReference();
        myRef = myFirebaseDatabase.getReference().child("requestPost");
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Snow More");
        context = ClientShovelerPage.this;
        //setUpVariables();
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

    //Shoveler request button action
    public void addButton(View view) {
        Intent request = new Intent(this, UserShovelingRequest.class);
        startActivity(request);
    }

    //Back button action
    public void backButton(View view) {
        Intent back = new Intent(this, WelcomePage.class);
        startActivity(back);
    }

    //Logout button action
    public void logoutButton(View view) {
        mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(this, project.ecse428.mcgill.ca.snowmore.Login.class);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(intent);
    }

    private void collectData(Map<String,Object> requestInfo) {

        ArrayList<String> address = new ArrayList<>();
        ArrayList<String> city = new ArrayList<>();
        ArrayList<String> phoneNumbers = new ArrayList<>();
        ArrayList<String> postalCode = new ArrayList<>();

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            address.add((String) singleUser.get("address"));
        }

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            city.add((String) singleUser.get("city"));
        }

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            phoneNumbers.add((String) singleUser.get("phone"));
        }

        //iterate through each post request, ignoring their UID
        for (Map.Entry<String, Object> entry : requestInfo.entrySet()){

            Map singleUser = (Map) entry.getValue();
            postalCode.add((String) singleUser.get("postalCode"));
        }

//        System.out.println(phoneNumbers.toString());
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ShovelingRequest> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ShovelingRequest>(
                ShovelingRequest.class,
                R.layout.list_view_layout,
                ShovelingRequest.class

        ) {
            @Override
            protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, Object model, int position) {
                viewHolder.
            }

            @Override
            protected void populateViewHolder(final StatusViewHolder viewHolder, final ShovelingRequest model, int position) {


                viewHolder.setUserStatus(model.getUserStatus());

                //query the user with the model id which is the row's user id
                mUserDB.child(model.getUserId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userName = dataSnapshot.child("displayName").getValue(String.class);
                        String photoUrl = dataSnapshot.child("photoUrl").getValue(String.class);

                        viewHolder.setUserName(userName);

                        try {
                            viewHolder.setUserPhotoUrl(getApplicationContext(), photoUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /**Listen to image button click**/
                viewHolder.userImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //go to profile
                        Intent goToProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                        goToProfile.putExtra("USER_ID", model.getUserId());
                        startActivity(goToProfile);
                    }
                });




            }
        };

        mHomeRecycler.setAdapter(firebaseRecyclerAdapter);

    }


    public static class requestPostHolder extends RecyclerView.ViewHolder{

        View view;

        public TextView city;
        public TextView address;
        public TextView postalCode;
        public TextView phone;

        public requestPostHolder(View itemView) {
            super(itemView);

            view = itemView;
        }
        public void setCity(String city){
            TextView userNameTxtView = (TextView)view.findViewById(R.id.city);
            userNameTxtView.setText(city);
        }

        public void setAddress(String address){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.address);
            userStatusTxtView.setText(address);
        }

        public void setPhone(String phone){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.address);
            userStatusTxtView.setText(phone);
        }

        public void setPostalCode(String postalCode){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.address);
            userStatusTxtView.setText(postalCode);
        }

    }

}
