package com.vinnovations.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {


    int count = 0,result =0;
    long total_ques=0;
    TextView txt_question;
    ImageView img_question;
    RadioGroup radio_group;
    RadioButton radio_optionA,radio_optionB,radio_optionC,radio_optionD;
    Button btn_next;
    String checkAnswer;
    LinearLayout radio_layout;
    String testName;
    DatabaseReference databaseReference;
    ArrayList<String> listAnswer = new ArrayList<>();
    ArrayList<Uri> listImageUri = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        img_question = findViewById(R.id.img_question);
        radio_group = findViewById(R.id.radio_group);
        radio_optionA = findViewById(R.id.radio_optionA);
        radio_optionB = findViewById(R.id.radio_optionB);
        radio_optionC = findViewById(R.id.radio_optionC);
        radio_optionD = findViewById(R.id.radio_optionD);
        btn_next = findViewById(R.id.btn_next);
        radio_layout = findViewById(R.id.radio_layout);
        testName = "main";

        txt_question = findViewById(R.id.txt_question);
        txt_question.setVisibility(View.INVISIBLE);

        // ------------------------------- firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("all-tests/" + testName +"/");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    total_ques = dataSnapshot.getChildrenCount();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        // Retrieve the values from each child object
                        String answer = String.valueOf(childSnapshot.child("answer").getValue());
                        String imageUrl = childSnapshot.child("imageUrl").getValue(String.class);
                        Uri imageUri = Uri.parse(imageUrl);
                        listAnswer.add(answer);
                        listImageUri.add(imageUri);

                        // --------------------
                        if(count==0){
                            Glide.with(TestActivity.this)
                                    .load(listImageUri.get(count))
                                    .into(img_question);
                            checkAnswer = String.valueOf(listAnswer.get(count));
                            count++;
                        }
                    }


                } else {
                    // Data does not exist at the specified location
                    Toast.makeText(TestActivity.this,"data not exist",Toast.LENGTH_SHORT).show();
                }
            }
//
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occurred
                Toast.makeText(TestActivity.this,"database error occured",Toast.LENGTH_SHORT).show();
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count==total_ques){
                    count++;
                    btn_next.setText("Exit >>");
                    //get selected radio button from radioGroup
                    int selectedId = radio_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    if(selectedId != -1){
                        RadioButton selected_radio_btn = findViewById(selectedId);

                        if(checkAnswer.equals(selected_radio_btn.getText())){
                            result++;
                        }
                    }

                    radio_layout.setVisibility(View.GONE);
                    img_question.setVisibility(View.GONE);
                    txt_question.setVisibility(View.VISIBLE);
                    txt_question.setText("Your Result: "+result);
                    txt_question.setTextColor(getResources().getColor(R.color.purple_200));
                    txt_question.setTextSize(50.0f);


                }else if(count<total_ques){
                    int selectedId = radio_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    if(selectedId != -1){
                        RadioButton selected_radio_btn = findViewById(selectedId);

                        if(checkAnswer.equals(selected_radio_btn.getText())){
                            result++;
                        }
                    }
                    radio_group.clearCheck();

                    Glide.with(TestActivity.this)
                            .load(listImageUri.get(count))
                            .into(img_question);
                    checkAnswer = listAnswer.get(count);
                    count++;
                    if(count==total_ques){
                        btn_next.setText("Submit >>");
                    }

                }else{
                    Intent i = new Intent(TestActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();

                }

            }
        });


    }
}