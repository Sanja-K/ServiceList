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
        super.onCreate();

    }
}
