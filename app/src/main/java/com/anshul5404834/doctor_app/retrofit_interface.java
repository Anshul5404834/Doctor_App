package com.anshul5404834.doctor_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface retrofit_interface {
    @GET("https://rekop-help.herokuapp.com/")
    Call<Object> getaccesstoken(@Query("identity") String alice, @Query("room") String example);
}
//?identity={ID}&room={RN}