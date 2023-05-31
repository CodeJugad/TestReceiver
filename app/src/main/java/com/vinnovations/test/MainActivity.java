package com.vinnovations.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<String> listTestName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        // ------------------------
        databaseReference = FirebaseDatabase.getInstance().getReference().child("all-tests/");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    total_ques = dataSnapshot.getChildrenCount();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        // Retrieve the values from each child object
                        String listItemName = String.valueOf(childSnapshot.getKey());
//                        Log.d("Tag", listItemName);
                        listTestName.add(listItemName);

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, listTestName);
                    listView.setAdapter(adapter);



                } else {
                    // Data does not exist at the specified location
//                    Toast.makeText(TestActivity.this,"data not exist",Toast.LENGTH_SHORT).show();
                }
            }
            //
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occurred
//                Toast.makeText(TestActivity.this,"database error occured",Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                String selectedItem = (String) parent.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,TestActivity.class);
                i.putExtra("testName", selectedItem);
                startActivity(i);
                finish();
            }
        });


//        TextView txt =  findViewById(R.id.txt);
//        txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, TestActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }
}