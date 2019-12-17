package com.anshul5404834.doctor_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Appointment_history extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ListView listView=findViewById(R.id.listview);
        List<pojo_first>appointment_pojos= new ArrayList<>();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= firebaseDatabase.getReference().child("Appointment");
        Appointment_adapter appointment_adapter= new Appointment_adapter(getApplicationContext(),appointment_pojos);
        listView.setAdapter(appointment_adapter);
        Query query1 =databaseReference.orderByChild("userid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                appointment_pojo appointment_pojo= dataSnapshot.getValue(com.anshul5404834.doctor_app.appointment_pojo.class);
                if(appointment_pojo.getPaid()){
                    final String[] s1 = new String[1];
                    DatabaseReference databaseReference1=firebaseDatabase.getReference().child("issue");
                    Query query2 =databaseReference1.orderByKey().equalTo(dataSnapshot.getKey());
                    query2.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (dataSnapshot.exists()) {
                                List<specialtiy_pojo> specialtiy_pojos = new ArrayList<>();
                                for(DataSnapshot dataSnapshot1: dataSnapshot.child("Images").getChildren()){
                                    specialtiy_pojo specialtiy_pojo=dataSnapshot1.getValue(com.anshul5404834.doctor_app.specialtiy_pojo.class);
                                                  specialtiy_pojos.add(specialtiy_pojo);

                                   //
                                }
                                isssue_structure_pojo isssue_structure_pojo= dataSnapshot.getValue(isssue_structure_pojo.class);
                                s1[0]=isssue_structure_pojo.getMessage();

                                Toast.makeText(getApplicationContext(),String.valueOf(specialtiy_pojos.size()), Toast.LENGTH_SHORT).show();
                                appointment_adapter.add(new pojo_first(specialtiy_pojos,s1[0]));
                            }
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
                    Query query=databaseReference1.child(dataSnapshot.getKey()).child("Images");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){





                           //     appointment_adapter.add(new pojo_first(specialtiy_pojos,s1[0]));
                            }




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    })          ;
                //   query.addChildEventListener(new ChildEventListener() {
                //       @Override
                //       public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //           if(dataSnapshot.exists()){
                //               specialtiy_pojo specialtiy_pojo=dataSnapshot.getValue(com.anshul5404834.doctor_app.specialtiy_pojo.class);
                //               specialtiy_pojos.add(specialtiy_pojo);
                //                  appointment_adapter.add(new pojo_first(specialtiy_pojos,s1[0]));
                //               Log.d("images",specialtiy_pojo.getimage());
                //           }
                //
                //       }
                //
                //       @Override
                //       public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //
                //       }
                //
                //       @Override
                //       public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //
                //       }
                //
                //       @Override
                //       public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //
                //       }
                //
                //       @Override
                //       public void onCancelled(@NonNull DatabaseError databaseError) {
                //
                //       }
                //   });
                }
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
