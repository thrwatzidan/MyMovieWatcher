package com.dev.thrwat_zidan.mynurdsmoviewatcher.Retofit;

import android.annotation.SuppressLint;
import android.util.Log;

import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/";

    private IMovieApi iMovieApi;
    private static RetrofitClient INSTANCE;

    public RetrofitClient() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iMovieApi = retrofit.create(IMovieApi.class);
    }

    public static RetrofitClient getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new RetrofitClient();
        }
        return INSTANCE;
    }

    public Call<Object> getMovieSlide() {
        return iMovieApi.getMovieSlider(common.DB_API);
    }

    public Call<Object> getTopMovieList() {
        return iMovieApi.getMovieList(common.DB_API, 1);
    }

    public Call<Object> getPopularMovies() {
        return iMovieApi.getMoviePopular(common.DB_API);
    }

    public Call<Object> getCastDetail() {
        return iMovieApi.getMovieCredits(Integer.parseInt(common.MOVIE_ID), common.DB_API);

    }

    @SuppressLint("LongLogTag")
    public Call<Object> getSearchedMovies() {
        Log.i("SEAAAAAAAAAAAAAAAAAArch3", common.currentSearchedID);

        return iMovieApi.searchForMovie(common.DB_API, common.currentSearchedID);
    }

}
