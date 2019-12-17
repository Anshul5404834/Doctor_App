package com.anshul5404834.doctor_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class All_stuff extends AppCompatActivity {
    private ImageView profile_photo;
    String phone;


    String uid;

    DateFormat dateFormat2= new SimpleDateFormat("dd/MM");
    String date=dateFormat2.format(Calendar.getInstance().getTime());
    DateFormat dateFormat3= new SimpleDateFormat("HH:mm");
    String time=dateFormat3.format(Calendar.getInstance().getTime());
    String string_time=date+" at "+ time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TextView age=findViewById(R.id.textView4);
        TextView gender=findViewById(R.id.textView5);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.button);
        final Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.rotation);
     //   final Animation anim3 = AnimationUtils.loadAnimation(this, R.anim.rotation);


        try{
            FirebaseAuth.getInstance().getCurrentUser().getUid();

            {
                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            }

            {
                phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            }}catch (Exception e){
            Intent intent= new Intent(this,PhoneAuthActivity.class);
            startActivity(intent);
        }
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference().child("age_gender");
        try{
            Query query=reference.orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    age_gender_pojo age_gender_pojo= dataSnapshot.getValue(com.anshul5404834.doctor_app.age_gender_pojo.class);
                    String s1=age_gender_pojo.getAge()+getString(R.string.yearss);
                    age.setText(s1);
                    gender.setText(age_gender_pojo.getGender());
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
            });}catch (Exception E){}
        date=date+"/2019";


        try{
        phone= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();}catch (Exception e){}
        profile_photo = findViewById(R.id.profile_photo);
        final TextView name=findViewById(R.id.username);
        name.setText(getApplicationContext().getString(R.string.edit_username));
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setAnimation(anim2);
                final EditText editText= new EditText(getApplicationContext());
                AlertDialog.Builder builder= new AlertDialog.Builder(All_stuff.this);
                if(builder==null){
                    Toast.makeText(getApplicationContext(),"builder is empty",Toast.LENGTH_SHORT).show();}
                builder.setView(editText);
                builder.setTitle(getApplicationContext().getString(R.string.update_username));
                editText.setTextColor(Color.BLACK);
                editText.setHint(getApplicationContext().getString(R.string.enter_username));
                editText.setHintTextColor(Color.GRAY);
                builder.setPositiveButton(getApplicationContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name.setText(editText.getText().toString());
                        if(editText.getText().toString().trim().isEmpty()){name.setText(getString(R.string.update_username));}
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(
                                new UserProfileChangeRequest.Builder().
                                        setDisplayName(editText.getText().toString()).build()
                        );
                    }
                });
                builder.setNegativeButton(getApplicationContext().getString(R.string.cancel),null);
                builder.create();
                builder.show();
            }
        });
        try{
        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            Uri url=  FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
            Glide.with(getApplicationContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.boy)).load(url).into(profile_photo);
        }catch (Exception e){}
        if(profile_photo.getDrawable()==null){profile_photo.setImageResource(R.drawable.boy);}
if(name.getText().length()==0){name.setText(getString(R.string.update_username));}
        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_photo.setAnimation(anim2);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using "), 2112);
            }
        });
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age.setAnimation(anim2);
                final EditText editText= new EditText(getApplicationContext());
                AlertDialog.Builder builder= new AlertDialog.Builder(All_stuff.this);
                if(builder==null){
                    Toast.makeText(getApplicationContext(),"builder is empty",Toast.LENGTH_SHORT).show();}
                builder.setView(editText);
                builder.setTitle(getApplicationContext().getString(R.string.update_age));
                editText.setTextColor(Color.BLACK);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHint(getApplicationContext().getString(R.string.enter_age));
                editText.setHintTextColor(Color.GRAY);
                builder.setCancelable(false);
                builder.setPositiveButton(getApplicationContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        age.setText(editText.getText().toString());
                        if(editText.getText().toString().trim().isEmpty()){age.setText(getString(R.string.age));}
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference reference=firebaseDatabase.getReference().child("age_gender");
                 //  reference.push().child("age").setValue(1);
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("age").setValue(editText.getText().toString());
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                    }
                });
                builder.setNegativeButton(getApplicationContext().getString(R.string.cancel),null);
                builder.create();
                builder.show();
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(All_stuff.this);
                if(builder==null){
                    builder.setCancelable(false);
                    builder.setTitle("Choose Appropriate gender");
                    Toast.makeText(getApplicationContext(),"builder is empty",Toast.LENGTH_SHORT).show();}

                builder.setNegativeButton(getString(R.string.female), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference reference=firebaseDatabase.getReference().child("age_gender");
                        gender.setText(R.string.female);
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("gender").setValue(getString(R.string.female));
                    }
                });
                builder.setPositiveButton(getString(R.string.male), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference reference=firebaseDatabase.getReference().child("age_gender");
                        gender.setText(R.string.male);
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("gender").setValue(getString(R.string.male));
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                    }
                });
                builder.setNeutralButton(getApplicationContext().getString(R.string.others), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference reference=firebaseDatabase.getReference().child("age_gender");
                        gender.setText(R.string.male);
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("gender").setValue(getString(R.string.others));
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                    }
                });
                builder.create();
                builder.show();
            }
        });

        Button have_apponitment_button=findViewById(R.id.login);

        Button take_appointment_button=findViewById(R.id.continue_button_first);
        take_appointment_button.setText(getApplicationContext().getString(R.string.book));
        have_apponitment_button.setText(getApplicationContext().getString(R.string.join));

        take_appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                take_appointment_button.startAnimation(myAnim);
                Intent i= new Intent(All_stuff.this ,check_code.class);
                startActivity(i);
            }
        });
        have_apponitment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                have_apponitment_button.startAnimation(myAnim);
                Intent i= new Intent(All_stuff.this, booking_history.class);
                startActivity(i);

            }
        });
        Button button=findViewById(R.id.history_issues);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.startAnimation(myAnim);
                Intent i= new Intent(All_stuff.this, Appointment_history.class);
                startActivity(i);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2112){ if(resultCode==RESULT_OK){
            Uri uri = data.getData();
            FirebaseAuth.getInstance().getCurrentUser().updateProfile( new UserProfileChangeRequest.Builder().setPhotoUri(uri).build());
            Glide.with(getApplicationContext()).load(uri).into(profile_photo);}}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
      //  finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();finish();
       // try{onDestroy();}catch (Exception e){}

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            Intent intent= new Intent(this,PhoneAuthActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
