package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.authentication.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import backend.ShovelingRequest;

/**
 * Created by junshin on 2018-02-14.
 */

public class ClientShovelerPage extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private DatabaseReference mRequestDB;
    private DatabaseReference mUserDB;
    private RecyclerView recyclerView;
    private Dialog dialog = null;
    private Context context = null;
    private static FirebaseRecyclerAdapter<ShovelingRequest , requestPostHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shoveler_page);
        setUpVariables();
        if(mAuth.getCurrentUser() == null) {
            goToLogin();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Snow More");
        context = ClientShovelerPage.this;
        //setUpVariables();
    }

    private void goToLogin() {
        startActivity(new Intent(ClientShovelerPage.this , Login.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAuth.getCurrentUser() == null) {
            goToLogin();
        }
    }


    public void setUpVariables() {
        mAuth = FirebaseAuth.getInstance();
        mRequestDB = FirebaseDatabase.getInstance().getReference().child("pending requests");
        mUserDB = FirebaseDatabase.getInstance().getReference().child("Users");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
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

    @Override
    protected void onStart() {
        super.onStart();

        this.firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ShovelingRequest, requestPostHolder>(ShovelingRequest.class , R.layout.list_view_layout , requestPostHolder.class , mRequestDB) {
            @Override
            protected void populateViewHolder(final requestPostHolder viewHolder, ShovelingRequest model, int position) {
                viewHolder.setAddress(model.getStreetAddress());
                viewHolder.setCity(model.getCity());
                viewHolder.setDate(model.getRequestDate());
                viewHolder.setTime(model.getRequestTime());
                viewHolder.setPhone(model.getClientNumber());
                viewHolder.setPostalCode(model.getPostalCode());

                // set the request id in the item view
                DatabaseReference ref = ClientShovelerPage.firebaseRecyclerAdapter.getRef(position);
                String reqID = ref.getKey();
                viewHolder.setReqID(reqID);
                mUserDB.child(model.getUserID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String username = dataSnapshot.child("name").getValue(String.class);
                        viewHolder.setUserName(username);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    // shoveler adds post
    public void addPostButton(View view) {
        Intent request = new Intent(this, UserShovelingRequest.class);
        startActivity(request);
    }


    public static class requestPostHolder extends RecyclerView.ViewHolder{
        View view;

        public requestPostHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setCity(String city){
            TextView userNameTxtView = (TextView)view.findViewById(R.id.city);
            userNameTxtView.setText("City: " + city);
        }

        public void setAddress(String address){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.address);
            userStatusTxtView.setText("Address: " + address);
        }

        public void setPhone(String phone){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.phone);
            userStatusTxtView.setText("Phone Number: " + phone);
        }

        public void setPostalCode(String postalCode){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.postalCode);
            userStatusTxtView.setText("Postal Code: " + postalCode);
        }

        public void setDate(String date){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.date_tv);
            userStatusTxtView.setText("Date: " + date);
        }

        public void setTime(String time){
            TextView userStatusTxtView = (TextView)view.findViewById(R.id.time_tv);
            userStatusTxtView.setText("Time: " + time);
        }

        public void setUserName (String name) {
            TextView userNameTextView = (TextView)view.findViewById(R.id.user_tv);
            userNameTextView.setText("User: " + name);
        }

        public void setReqID (String reqId){
            TextView reqIDTextView = (TextView)view.findViewById(R.id.reqID);
            reqIDTextView.setText(reqId);
        }

    }

    public void onRequestClick(View v) {
        TextView reqIDTextView = (TextView)v.findViewById(R.id.reqID);
        CharSequence reqID = reqIDTextView.getText();
        Intent acceptIntent = new Intent(ClientShovelerPage.this, AcceptShovellingRequest.class);
        acceptIntent.putExtra("requestID", (String) reqID);
        startActivity(acceptIntent);
    }

}