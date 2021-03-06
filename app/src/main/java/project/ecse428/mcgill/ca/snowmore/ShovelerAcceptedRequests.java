package project.ecse428.mcgill.ca.snowmore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import backend.ShovelingRequest;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;


public class ShovelerAcceptedRequests extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mRequestDB;
    private DatabaseReference mUserDB;
    private RecyclerView recyclerView;
    private Dialog dialog = null;
    private Context context = null;
    private Query mQueryAcceptedRequestDB;
    public static FirebaseRecyclerAdapter<ShovelingRequest, requestPostHolder> firebaseRecyclerAdapter;
    String token = FirebaseInstanceId.getInstance().getToken();
    private static final String regToken = "regToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accepted_requests);
        setUpVariables();
        if (mAuth.getCurrentUser() == null) {
            goToLogin();
        }
        context = ShovelerAcceptedRequests.this;

    }

    private void goToLogin() {
        startActivity(new Intent(ShovelerAcceptedRequests.this, Login.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser() == null) {
            goToLogin();
        }
    }

    public void requestsButton(View view) {
        Intent requests = new Intent(ShovelerAcceptedRequests.this, ClientShovelerPage.class);
//        requests.putExtra("state", state);
        startActivity(requests);
    }

    public void setUpVariables() {
        mAuth = FirebaseAuth.getInstance();

        mUserDB = FirebaseDatabase.getInstance().getReference().child("Users");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_myRequest);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        String currentUserID = mAuth.getCurrentUser().getUid();
        mRequestDB = FirebaseDatabase.getInstance().getReference();

        mQueryAcceptedRequestDB = mRequestDB.child("accepted requests").orderByChild("shovelerID").equalTo(currentUserID);

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

    public void onRequestClick(View view) {
        TextView reqIDTextView = (TextView) view.findViewById(R.id.reqID);
        CharSequence reqID = reqIDTextView.getText();
        // parse to get JUST the request ID
        reqID = reqID.subSequence(12, reqID.length());
        createDialog(reqID);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ShovelingRequest, requestPostHolder>(
                ShovelingRequest.class,
                R.layout.shoveler_list_view_layout,
                requestPostHolder.class,
                //mQueryRequestDB         //use for posts made by current user
                mQueryAcceptedRequestDB     // use for accepted requests of current user
        ) {


            @Override
            protected void populateViewHolder(final requestPostHolder viewHolder, ShovelingRequest model, int position) {
                viewHolder.setAddress(model.getStreetAddress());
                viewHolder.setCity(model.getCity());
                viewHolder.setDate(model.getRequestDate());
                viewHolder.setTime(model.getRequestTime());
                viewHolder.setPhone(model.getClientNumber());
                viewHolder.setStatus(model.getisCancelled());
                //viewHolder.setPhone(model.getShovelerNumber());
                viewHolder.setPostalCode(model.getPostalCode());
                DatabaseReference ref = ShovelerAcceptedRequests.firebaseRecyclerAdapter.getRef(position);
                String reqID = ref.getKey();
                viewHolder.setReqID(reqID);

                mUserDB.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
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


    public void createDialog(final CharSequence requestID) {

        DatabaseReference thisReqRef = mRequestDB.child("accepted requests").child((String) requestID);
        ValueEventListener thisReqRefEventListener = thisReqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //check if value exists
                if (dataSnapshot.exists()) {
                    ShovelingRequest shovelingRequest = dataSnapshot.getValue(ShovelingRequest.class);

                    if (shovelingRequest.getStatus() == "Cancelled") {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ShovelerAcceptedRequests.this);
                        builder.setTitle("Remove this request?");

                        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mRequestDB.child("accepted requests").child((String) requestID).removeValue();

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("Database Error", error.getMessage());
            }
        });
    }

    public static class requestPostHolder extends RecyclerView.ViewHolder {

        View view;


        public requestPostHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setStatus(String status) {
            TextView statusTxtView = (TextView) view.findViewById(R.id.status);
            statusTxtView.setText("Status: " + status);
        }

        public void setCity(String city) {
            TextView userNameTxtView = (TextView) view.findViewById(R.id.city);
            userNameTxtView.setText("City: " + city);
        }

        public void setAddress(String address) {
            TextView userStatusTxtView = (TextView) view.findViewById(R.id.address);
            userStatusTxtView.setText("Address: " + address);
        }

        public void setPhone(String phone) {
            TextView userStatusTxtView = (TextView) view.findViewById(R.id.phone);
            userStatusTxtView.setText("Phone Number: " + phone);
        }

        public void setPostalCode(String postalCode) {
            TextView userStatusTxtView = (TextView) view.findViewById(R.id.postalCode);
            userStatusTxtView.setText("Postal Code: " + postalCode);
        }

        public void setDate(String date) {
            TextView userStatusTxtView = (TextView) view.findViewById(R.id.date_tv);
            userStatusTxtView.setText("Date: " + date);
        }

        public void setTime(String time) {
            TextView userStatusTxtView = (TextView) view.findViewById(R.id.time_tv);
            userStatusTxtView.setText("Time: " + time);
        }

        public void setUserName(String name) {
            TextView userNameTextView = (TextView) view.findViewById(R.id.user_tv);
            userNameTextView.setText("User: " + name);
        }

        public void setReqID(String reqId) {
            TextView reqIDTextView = (TextView) view.findViewById(R.id.reqID);
            reqIDTextView.setText("Request ID: " + reqId);
        }
    }
}
