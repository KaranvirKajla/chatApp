package com.example.chatapp;

import Adapter.FriendAdapter;
import Adapter.UserAdapter;
import Models.User;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    private Uri imageUri;
    private FriendAdapter friendAdapter;
    private ProgressBar progress;
    private List<User> mFriends;
    private FirebaseAuth mAuth;
    ImageView add;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = findViewById(R.id.progress);
        mAuth = FirebaseAuth.getInstance();
        mFriends = new ArrayList<>();
        friendAdapter = new FriendAdapter(MainActivity.this,mFriends);
        add = findViewById(R.id.add_friend);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(friendAdapter);
        onAddClick();
        readFriends();
    }

    private void readFriends() {
        mFriends.clear();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid()).child("Friends");
        final DatabaseReference mUsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFriends.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    final String email = snapshot.getValue(String.class);
                    Log.d("friendmain","email   "+email);
                    Query query = mUsersRef.orderByChild("email").equalTo(email).limitToFirst(1);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                User friend = snapshot.getValue(User.class);
                                Log.d("friendmain","finalllllllllllllll " + friend.getEmail().toString());
                                mFriends.add(friend);

                            }
                            friendAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
        case R.id.logout:
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
            return true;
            case R.id.profilePic:
                startActivity(new Intent(MainActivity.this,ProfilePicActivity.class));
               // CropImage.activity().start(this);
                return true;
        default:
        Toast.makeText(MainActivity.this,"Defalu",Toast.LENGTH_SHORT).show();
        return true;
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult((data));
             imageUri = result.getUri();
        }else{
            Toast.makeText(MainActivity.this,"Try again",Toast.LENGTH_LONG).show();
        }
    }*/

    private void onAddClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddFriend.class));
            }
        });
    }

}
