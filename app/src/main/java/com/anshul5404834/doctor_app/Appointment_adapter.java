package com.anshul5404834.doctor_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Appointment_adapter extends ArrayAdapter {
    Context context;
    List<pojo_first>appointment_pojos;
    public Appointment_adapter( Context context,  List<pojo_first>appointment_pojos) {
        super(context,0,appointment_pojos);
        this.appointment_pojos=appointment_pojos;
        this.context=context;
    }

    @Override
    public View getView(int position,View convertView,  ViewGroup parent) {
        View m=convertView;
        if(m==null){
            m= LayoutInflater.from(context).inflate(R.layout.appointment_history,parent,false);
        }
        pojo_first pojo_first=appointment_pojos.get(position);
        TextView textView= m.findViewById(R.id.message_issue);
        textView.setText(pojo_first.getMessage());
        LinearLayout linearLayout=m.findViewById(R.id.my_linear_layout);
        linearLayout.removeAllViews();
        for(int i=0;i<pojo_first.specialtiy_pojos.size();i++){
            View m_view= LayoutInflater.from(context).inflate(R.layout.imageview,parent,false);
            specialtiy_pojo specialtiy_pojo=pojo_first.specialtiy_pojos.get(i);
            ImageView imageView=m_view.findViewById(R.id.image_issues);
            Glide.with(context).load(specialtiy_pojo.getimage()).apply(new RequestOptions().placeholder(R.drawable.boy)).into(imageView);
            linearLayout.addView(m_view);
        }

        return m;
    }
}
