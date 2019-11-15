package com.example.servicelist;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Single;

public interface ServerApi {

    @GET("services.php?os=android")
    Single<JsonArray> resp(/*@Query("os") String TypeMobileOs*/);


    @Multipart
    @POST("upload.php")
    Call<JsonObject> upload(
            @Part("name") RequestBody  name,
            @Part("comment") RequestBody comment,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("upload.php")
    Call<JsonObject> upload(
            @Part("name") RequestBody  name,
            @Part("comment") RequestBody comment);
}
