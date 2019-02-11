package com.example.iqbal.olms.api;

import com.example.iqbal.olms.model.DefaultResponse;
import com.example.iqbal.olms.model.StudentLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("studentSignup.php")
    Call<DefaultResponse> createUser(
            @Field("id") String id,
            @Field("dob") String dob,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("studentLogin.php")
    Call<StudentLoginResponse> stuLogin(
            @Field("id") String id,
            @Field("password") String password
    );

}
