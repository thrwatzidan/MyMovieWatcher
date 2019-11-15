package com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Interface.SearchItemCliclListener;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.SearchMovie;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<SearchMovie> searchMovieList = new ArrayList<>();
    private Context context;
    private SearchItemCliclListener searchItemCliclListener;

    public SearchAdapter(List<SearchMovie> searcheList, Context context,SearchItemCliclListener listener) {
        this.searchMovieList = searcheList;
        this.context = context;
        this.searchItemCliclListener = listener;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_movie_title.setText(searchMovieList.get(position).getTitle());

        if (!TextUtils.isEmpty(searchMovieList.get(position).getPoster_path()))
            Glide.with(context).load(common.IMAGE_LINK + searchMovieList.get(position).getPoster_path()).into(holder.img_movie_item);
        else
            Glide.with(context).load(R.drawable.movie_app).into(holder.img_movie_item);
    }


    @Override
    public int getItemCount() {
        return searchMovieList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_movie_item;
        private TextView txt_movie_title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_movie_title = itemView.findViewById(R.id.txt_movie_title);
            img_movie_item = itemView.findViewById(R.id.img_movie_item);


            itemView.setOnClickListener(view ->
                    searchItemCliclListener.onMovieClicked(searchMovieList.get(getAdapterPosition()),img_movie_item));

        }
    }
}
