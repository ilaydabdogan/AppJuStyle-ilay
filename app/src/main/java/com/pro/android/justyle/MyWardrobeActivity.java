package com.pro.android.justyle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyWardrobeActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private  ImageAdapter mAdapter;

    private DatabaseReference mDatabaseRef;

    private List<Upload> mUploads;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wardrobe);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(MyWardrobeActivity.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(MyWardrobeActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MyWardrobeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position "+ position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "Whatever click at position "+ position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(this, "Delete "+ position, Toast.LENGTH_SHORT).show();

    }
}
