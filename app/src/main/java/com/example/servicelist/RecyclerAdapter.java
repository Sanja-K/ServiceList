package com.example.servicelist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class RecyclerAdapter extends RealmRecyclerViewAdapter<ModelRealm, RecyclerAdapter.MyHolder> {
    private  static  final String Tag ="RecyclerAdapter";
    private Realm realm;
    private Context mContext;

    public  RecyclerAdapter(RealmResults <ModelRealm> list, Context context){
        super(list,true, true);
        this.mContext = context;
        this.realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final ModelRealm temp = getItem(position);

        Glide.with(mContext)
                .load(temp.getIcon())
                .override(120,120)
                .into(holder.imageView);

        holder.textView.setText(temp.getTitle());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, temp.getTitle(), Toast.LENGTH_SHORT).show();

                Uri address = Uri.parse(temp.getLink());
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                mContext.startActivity(Intent.createChooser(openlink, "Browser"));
            }
        });
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        LinearLayoutCompat linearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_service_widget);
            this.textView = itemView.findViewById(R.id.name_service_widget);
            this.linearLayout = itemView.findViewById(R.id.item_service);
        }
    }

}
