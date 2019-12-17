package com.anshul5404834.doctor_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


public class Retrofit {
    String at;
    public Retrofit(Context context,String name, String code,String times){
        String base_url="https://rekop-help.herokuapp.com/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(base_url)

                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();retrofit_interface retrofit_interface= retrofit.create(com.anshul5404834.doctor_app.retrofit_interface.class);
        Call<Object>retrofit_pojoCall= retrofit_interface.getaccesstoken(name,code);
        retrofit_pojoCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                at= response.body().toString();
                ProgressDialog progressDialog= new ProgressDialog(context);
                progressDialog.create();
                progressDialog.setMessage(context.getString(R.string.redirecting));
                progressDialog.show();
                Intent abc = new Intent(context, VideoActivity.class);
                abc.putExtra("at",at);
                abc.putExtra("code",code);
                context.startActivity(abc);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

                Log.d("abaakkabk", call.toString());
                Log.d("abaakkabk", t.toString());

            }
        });
       // retrofit_pojoCall.enqueue(new Callback<String>() {
       //     @Override
       //     public void onResponse(Call<retrofit_pojo> call, Response<retrofit_pojo> response) {
       //
       //
       //
       //
       //
       //
       //
       //     }
       //
       //     @Override
       //     public void onFailure(Call<retrofit_pojo> call, Throwable t) {
       //         Toast.makeText(context,"failed  1",Toast.LENGTH_SHORT).show();
       //
       //     }
       // });
    // retrofit_pojoCall.enqueue(new Callback<retrofit_pojo>() {
    //
    //     @Override
    //     public void onResponse(Call<retrofit_pojo> call, Response<retrofit_pojo> response) {
    //
    //     //    Log.d("this is retrofit tag",response.body().getAccesstoken().toString()+"we dont talk anymore , like we used to do , now i cant get you out of my brain");
    //      //      Log.d("this is retrofit tag","we dont talk anymore , like we used to do , now i cant get you out of my brain");
    //
    //     }
    //     @Override
    //     public void onFailure(Call<retrofit_pojo> call, Throwable t) {
    //         Toast.makeText(context,"failed   3",Toast.LENGTH_SHORT).show();
    //     }
    // });
    }
}
