package com.example.servicelist;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import io.realm.Realm;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RealmController {

      Realm realm;

     public RealmController ( ){
         Log.i(TAG, "RealmController: ");
         this.realm = Realm.getDefaultInstance();
     }

   public void updateInfo(final JsonArray jsonArray){
       realm = Realm.getDefaultInstance();
         realm.executeTransaction(new Realm.Transaction() {
             @Override
                 public void execute(Realm realm) {
                 Log.i(TAG, "execute: " +jsonArray);
                 realm.createOrUpdateAllFromJson(ModelRealm.class , String.valueOf(jsonArray));
             }
         });
     }
}
