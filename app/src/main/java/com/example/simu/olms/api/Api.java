package com.example.simu.olms.api;

import com.example.simu.olms.model.DefaultResponse;
import com.example.simu.olms.model.SearchBooksResponse;
import com.example.simu.olms.model.StudentLoginResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface Api {



    @FormUrlEncoded
    @POST("api/studentSignup.php")
    Call<DefaultResponse> createUser(
            @Field("id") String id,
            @Field("dob") String dob,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/studentLogin.php")
    Call<StudentLoginResponse> stuLogin(
            @Field("id") String id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/searchBook.php")
    Call<List<SearchBooksResponse>> searchBookByName(
            @Field("book_name") String book_name
    );

    @FormUrlEncoded
    @POST("api/issueBook.php")
    Call<DefaultResponse> issueBook(
            @Field("stu_id") String stu_id,
            @Field("book_id") int book_id
    );

}
