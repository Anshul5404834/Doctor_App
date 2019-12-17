package com.anshul5404834.doctor_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class check_code extends AppCompatActivity {
    String docid;
    String codee;
    String time;
    Button next;
    Button enter_code;
    TextView doc_name;
    String amountee;
    TextView amount;
    TextView date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.enter_code),Toast.LENGTH_SHORT).show();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_code_to_book);

        date = findViewById(R.id.textView);
        final Animation myAnim2 = AnimationUtils.loadAnimation(this, R.anim.button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.rotation);
        amount = findViewById(R.id.textView2);

        doc_name = findViewById(R.id.textView3);
        ImageButton imageButton= findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.startAnimation(myAnim);
                enter_code.setVisibility(View.VISIBLE);
                onRestart();
            }
        });

        enter_code = findViewById(R.id.button);

        next = findViewById(R.id.button2);
        enter_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_code.startAnimation(myAnim2);
                AlertDialog.Builder builder=new AlertDialog.Builder(check_code.this);
                EditText editText= new EditText(getApplicationContext());
                builder.setView(editText);
                builder.setTitle(getString(R.string.enter_code));
                editText.setHint(getString(R.string.enter_code));
                editText.setTextColor(Color.BLACK);
                editText.setHintTextColor(Color.BLACK);
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        {   set(editText.getText().toString());}
                        enter_code.setVisibility(View.INVISIBLE);
                        {
                            Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.enter_valid_code),Toast.LENGTH_SHORT);
                        }

                    }
                });
                builder.create();
                builder.show();
            }
        });
        next.setVisibility(View.INVISIBLE);
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        next.startAnimation(myAnim2);
        Intent intent= new Intent(check_code.this,Booking.class);
        DatabaseReference databaseReference=database.getReference().child("Appointment");
        DatabaseReference reference =databaseReference.push();
        reference.setValue(new appointment_pojo(docid
                , FirebaseAuth.getInstance().getCurrentUser().getUid(),
                time
                ,codee
                ,false));
intent.putExtra("ref",reference.getKey());
intent.putExtra("amount",amountee);
        startActivity(intent);
    }
});
    }
    public void set(String code){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference().child("booking");
        Query query=databaseReference.orderByChild("code").equalTo(code);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                pojo_doctor_book_code pojo_doctor_book_code=dataSnapshot.getValue(com.anshul5404834.doctor_app.pojo_doctor_book_code.class);
                doc_name.setText(pojo_doctor_book_code.getDocName());
                if(pojo_doctor_book_code.getPay()){amount.setText("Rs "+pojo_doctor_book_code.getAmount());}
                else {amount.setVisibility(View.GONE);}
                date.setText(pojo_doctor_book_code.getTime());
                 next.setVisibility(View.VISIBLE);
                 docid=pojo_doctor_book_code.getDocid();
                 time=pojo_doctor_book_code.getTime();
                 codee=pojo_doctor_book_code.getCode();
                 amountee=pojo_doctor_book_code.getAmount();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
