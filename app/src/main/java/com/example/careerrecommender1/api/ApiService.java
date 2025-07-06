package com.example.careerrecommender1.api;

import com.example.careerrecommender1.model.ApiResponse;
import com.example.careerrecommender1.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/auth/register")
    Call<ApiResponse> registerUser(@Body User user);

    @POST("/api/auth/login")
    Call<ApiResponse> loginUser(@Body User user);

}
