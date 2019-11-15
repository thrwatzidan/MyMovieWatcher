package com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MovieCast;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    private List<MovieCast> castList;
    private Context context;

    public CastAdapter(List<MovieCast> castList, Context context) {

        this.castList = castList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (!TextUtils.isEmpty(castList.get(position).getProfile_path())&& !castList.get(position).getProfile_path().equals(""))
            Glide.with(context).load(common.IMAGE_LINK + castList.get(position).getProfile_path()).into(holder.img_cast);
        else
            Glide.with(context).load(R.drawable.movie_app).into(holder.img_cast);

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_cast;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_cast = itemView.findViewById(R.id.img_cast);


        }
    }
}
