package com.example.demoretrofit.WebAPI;

import com.example.demoretrofit.DataModel.HeroViewModel;
import com.example.demoretrofit.DataModel.JobDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetroAPI {

     String base_url = "https://simplifiedcoding.net/demos/";
     String base_url2 = "https://reqres.in/api/";

    @POST("users")
    Call<JobDataModel> createPost(@Body JobDataModel dataModal);

    @GET("marvel")
    Call<List<HeroViewModel>> getHeroes();
}
