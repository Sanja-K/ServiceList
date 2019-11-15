package com.example.servicelist;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class UploadDataForm  {
    private Context context;
    private static String TAG = "UploadDataForm ";
    private EditText name;
    private EditText comment;
    private ImageButton photoImg;

    private int statusCode;

    public UploadDataForm(Context context, EditText nameForm, EditText commentForm, ImageButton photoBtm) {
        this.name = nameForm;
        this.comment = commentForm;
        this.photoImg = photoBtm;
        this.context = context;
    }

    private RequestBody createPartFromString(String string) {
        return RequestBody.create( string,
                okhttp3.MultipartBody.FORM);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(partName);

        RequestBody requestFile =
                RequestBody.create(file,
                        MediaType.parse(context.getContentResolver().getType(fileUri)));
        Log.i(TAG, "prepareFilePart: " +file);

        return MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
    }

    public Integer sendData( Uri uriFile, String currentPhotoPath){
        this.statusCode = 0;

        String personName = name.getText().toString();
        String strComment = comment.getText().toString();

        RequestBody nameForm = createPartFromString(personName);
        RequestBody commentForm = createPartFromString(strComment);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://job.softinvent.ru/")
                .addConverterFactory( GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ServerApi service = retrofit.create(ServerApi.class);

        Call<JsonObject> call;

        if (uriFile != null) {
            MultipartBody.Part file = prepareFilePart(currentPhotoPath, uriFile);
             call = service.upload(nameForm, commentForm, file);
        }else{
             call = service.upload(nameForm, commentForm);
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.v("Upload", "success");
                Log.i(TAG, " SingleSubscriber Response ------ " + response);
                    Serialize(response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i(TAG,"Upload error:"+ call);
                Log.e("Upload error:", t.getMessage());
            }
        });

        return statusCode;
    }

    public  void Serialize(Response<JsonObject> response){
        try {
            switch (response.code()) {
                case 201: {
                    this.statusCode = 201;
                    JSONObject jsonObject = new JSONObject(new Gson()
                            .toJson(response.body()));
                    name.setText("");
                    comment.setText("");
                    photoImg.setBackgroundResource(R.drawable.camera_icon);

                    ResponseDialog("Данные успешно отправлены", " Имя файла: "+ jsonObject.getString("filename"));
                    break;
                }
                case 400:
                case 500:{
                  JSONObject  jObjError = new JSONObject(response.errorBody().string());
                    String error = jObjError.getString("error");
                    ResponseDialog("Ошибка отправки данных",error );
                    break;
                }
                default: {
                    ResponseDialog("Ошибка", "Ошибка ответа сервера ");
                    Log.i(TAG, "Ошибка ответа сервера: ");
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ResponseDialog(String title, String message){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
