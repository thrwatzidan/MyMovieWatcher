package com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Interface.MovieItemClickListener;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MoviesList;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {

    private List<MoviesList> movieList = new ArrayList<>();
    private Context context;
    private MovieItemClickListener movieItemClickListener;

    public MoviesListAdapter(List<MoviesList> movieList, Context context,MovieItemClickListener listener) {
        this.movieList = movieList;
        this.context = context;
        this.movieItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_movie_title.setText(movieList.get(position).getTitle());

        if (!TextUtils.isEmpty(movieList.get(position).getPoster_path()))
           // Glide.with(context).load(common.IMAGE_LINK + movieList.get(position).getPoster_path()).into(holder.img_movie_item);

        Glide.with(context).load(common.IMAGE_LINK + movieList.get(position).getPoster_path())
                .apply(RequestOptions.placeholderOf(R.drawable.movie_app))
                .into(holder.img_movie_item);
        else
            Glide.with(context).load(R.drawable.movie_app).into(holder.img_movie_item);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_movie_item;
        private TextView txt_movie_title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_movie_title = itemView.findViewById(R.id.txt_movie_title);
            img_movie_item = itemView.findViewById(R.id.img_movie_item);


            itemView.setOnClickListener(view ->
                    movieItemClickListener.onMovieClicked(movieList.get(getAdapterPosition()),img_movie_item));

        }
    }
}
