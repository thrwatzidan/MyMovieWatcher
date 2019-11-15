package com.dev.thrwat_zidan.mynurdsmoviewatcher.Interface;

  import android.widget.ImageView;

import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MoviesList;

public interface MovieItemClickListener {

   // void onMovieClick(Movie movie, ImageView movieImageView);
    void onMovieClicked(MoviesList movie, ImageView movieImageView);

}
