package com.anshul5404834.doctor_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class booking_history_adapter extends ArrayAdapter {

    List<firebase_booking_pojo>list;
    Context context;
    String at;
    public booking_history_adapter( Context context,List<firebase_booking_pojo>list) {
        super(context, 0, list);
        this.context=context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View m=convertView;
        if(m==null){
           m= LayoutInflater.from(context).inflate(R.layout.booking_history,parent,false);
        }
final firebase_booking_pojo firebase_booking_pojo=list.get(position);
        Button button=m.findViewById(R.id.code_booking_layout);
        Button button1=m.findViewById(R.id.time_booking_adapter);
        Button button2=m.findViewById(R.id.join);
        button1.setText(String.format("%s", firebase_booking_pojo.getTime()));
        button.setText(String.format(context.getString(R.string.code)+" =  %s", firebase_booking_pojo.getCode()));
        DateFormat dateFormat2= new SimpleDateFormat("mm");
        String string_time=dateFormat2.format(Calendar.getInstance().getTime());
        DateFormat dateFormat3= new SimpleDateFormat("dd");
        String date=dateFormat3.format(Calendar.getInstance().getTime());
        DateFormat dateFormat4= new SimpleDateFormat("HH");
        String hour=dateFormat4.format(Calendar.getInstance().getTime());
        int firebase_time=Integer.parseInt(firebase_booking_pojo.getTime().substring(17,19));
        int firebase_hour=Integer.parseInt(firebase_booking_pojo.getTime().substring(14,16));
        int firebase_date=Integer.parseInt(firebase_booking_pojo.getTime().substring(0,2));
        int min=Integer.parseInt(string_time);
        int datee=Integer.parseInt(date);
        int houre=Integer.parseInt(hour);
        Log.d("anshul",String.valueOf(firebase_date));
        Log.d("anshul",String.valueOf(datee));
        Log.d("anshul",String.valueOf(firebase_hour));
        Log.d("anshul",String.valueOf(houre));
        Log.d("anshul",String.valueOf(firebase_time));
        Log.d("anshul",String.valueOf(min));
        button2.setVisibility(View.INVISIBLE);
        if(firebase_booking_pojo.getPaid()){
        if(firebase_date==datee&&firebase_hour==houre&&(firebase_time-min<=10 &&firebase_time-min>=0
        ||min-firebase_time<=10 &&min-firebase_time>=0)){button2.setVisibility(View.VISIBLE);}
        }



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit= new Retrofit(context, FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),firebase_booking_pojo.getCode(),firebase_booking_pojo.getTime());

            }
        });
        return m;
    }
}
