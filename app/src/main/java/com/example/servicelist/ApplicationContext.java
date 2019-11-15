package com.example.servicelist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationContext extends Application {

    public Realm realm;

    @Override
    public void onCreate(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        this.realm.setDefaultConfiguration(config);

        RetrofitSingle retrofitSingle =new RetrofitSingle( );
        retrofitSingle.init();
        super.onCreate();


    }
}
