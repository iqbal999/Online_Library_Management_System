package com.example.simu.olms.api;

import com.example.simu.olms.model.DefaultResponse;
import com.example.simu.olms.model.SearchBooksResponse;
import com.example.simu.olms.model.ShowAllIssuedBookResponse;
import com.example.simu.olms.model.ShowAllRequest;
import com.example.simu.olms.model.ShowIssueBooksResponse;
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
    @POST("api/student/studentSignup.php")
    Call<DefaultResponse> createUser(
            @Field("id") String id,
            @Field("dob") String dob,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/student/studentLogin.php")
    Call<StudentLoginResponse> stuLogin(
            @Field("id") String id,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("api/faculty/facultySignup.php")
    Call<DefaultResponse> facultySignUp(
            @Field("id") String id,
            @Field("dob") String dob,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/faculty/facultyLogin.php")
    Call<StudentLoginResponse> facLogin(
            @Field("id") String id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/general/searchBook.php")
    Call<List<SearchBooksResponse>> searchBookByName(
            @Field("book_name") String book_name,
            @Field("search_content") String search_content

    );

    @FormUrlEncoded
    @POST("api/student/issueBook.php")
    Call<DefaultResponse> issueBook(
            @Field("stu_id") String stu_id,
            @Field("book_id") int book_id
    );

    @FormUrlEncoded
    @POST("api/faculty/issueBookFaculty.php")
    Call<DefaultResponse> issueBookFaculty(
            @Field("fac_id") String stu_id,
            @Field("book_id") int book_id
    );

    @FormUrlEncoded
    @POST("api/general/showIssuedBooks.php")
    Call<List<ShowIssueBooksResponse>> showIssuedBooks(
            @Field("stu_id") String stu_id
    );

    @FormUrlEncoded
    @POST("api/faculty/showIssuedBooksFaculty.php")
    Call<List<ShowIssueBooksResponse>> showIssuedBooksFaculty(
            @Field("fac_id") String fac_id
    );

    @FormUrlEncoded
    @POST("api/student/returnBook.php")
    Call<DefaultResponse> returnBooks(
            @Field("sr_no") int sr_no
    );


    @FormUrlEncoded
    @POST("api/faculty/returnBook.php")
    Call<DefaultResponse> returnBooksFaculty(
            @Field("sr_no") int sr_no
    );


    @FormUrlEncoded
    @POST("api/general/showAllIssuedBooksOfUser.php")
    Call<List<ShowAllIssuedBookResponse>> showAllIssuedBooks(
            @Field("stu_id") String stu_id
    );

    @FormUrlEncoded
    @POST("api/general/requestForIssue.php")
    Call<DefaultResponse> requestForIssue(
            @Field("id_no") String id_no,
            @Field("sr_no") int sr_no,
            @Field("req_type") String request_type

    );

    @FormUrlEncoded
    @POST("api/general/requestForReturn.php")
    Call<DefaultResponse> requestForReturn(
            @Field("id_no") String id_no,
            @Field("sr_no") int sr_no,
            @Field("req_type") String request_type

    );

    @FormUrlEncoded
    @POST("api/general/showAllReturnRequest.php")
    Call<List<ShowAllRequest>> showAllReturnRequest(
            @Field("user_id") String id_no,
            @Field("user_type") String user_type


    );

    @FormUrlEncoded
    @POST("api/general/showAllRequest.php")
    Call<List<ShowAllRequest>> showAllRequest(
            @Field("user_id") String id_no

    );

    @FormUrlEncoded
    @POST("api/general/deleteRequest.php")
    Call<DefaultResponse> delRequest(
            @Field("req_id") String req_id

    );

    @FormUrlEncoded
    @POST("api/general/deleteReturnRequest.php")
    Call<DefaultResponse> delReturnRequest(
            @Field("req_id") String req_id

    );



}
