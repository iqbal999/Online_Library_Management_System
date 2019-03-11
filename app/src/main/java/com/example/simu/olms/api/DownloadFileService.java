package com.example.simu.olms.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadFileService {

    @Streaming
    @GET
    Call<ResponseBody> downloadFileUrl(@Url String fileUrl);
}
