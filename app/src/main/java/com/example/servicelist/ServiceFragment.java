package com.example.servicelist;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {

    private static String TAG = "ServiceFragment";

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "FirstFragment.onCreate()",
                Toast.LENGTH_LONG).show();


      /*  Realm realm = Realm.getDefaultInstance();
        RealmResults<ModelRealm> results;
        results = realm.where(ModelRealm.class)
                .findAll();*/

       // RecyclerView recyclerView = findViewById(R.id.recycler_view);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
       /* RetrofitSingle retrofitSingle =new RetrofitSingle();
        retrofitSingle.init();*/
        Log.d("Fragment 1", "onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new RecyclerAdapter(getData(),getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Log.d("Fragment 1", "onCreateView");
        return view;
    }

    public RealmResults getData(){
        Realm realm =Realm.getDefaultInstance();
        RealmResults <ModelRealm> results;
        results =realm.where(ModelRealm.class).findAll();

        Log.i(TAG, "RealmResults getData: " +results);
        return results;
    }
}
