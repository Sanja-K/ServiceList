package com.example.servicelist;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import rx.SingleSubscriber;
import rx.schedulers.Schedulers;


public class RetrofitSingle {

    private static final String TAG = "RetrofitSingleLogged";
    RealmController realmController;

    public RetrofitSingle( ){
        this.realmController = new RealmController( ) ;
    }

    public  void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://job.softinvent.ru/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        ServerApi messagesApi = retrofit.create(ServerApi.class);
        messagesApi.resp()
               .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<JsonArray>(){
                               @Override
                               public void onSuccess(JsonArray responses) {
                                   Log.i(TAG, " SingleSubscriber " + responses);
                                   realmController.updateInfo(responses);
                               }

                               @Override
                               public void onError(Throwable error) {
                                   Log.i(TAG, " SingleSubscriber error " + error);
                               }
                           });
    }
}
