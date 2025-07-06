package com.example.careerrecommender1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.careerrecommender1.util.Constants;
public class RetrofitClient {

    private static Retrofit retrofit = null;


    public static ApiService getApiService() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
