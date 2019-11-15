package com.dev.thrwat_zidan.mynurdsmoviewatcher.Retofit;


import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MovieSlider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieApi {

    @GET("3/discover/movie?")
    Call<Object> getMovieList(@Query("api_key") String api_key,
                              @Query("page") int page);

    @GET("3/movie/{id}/credits?")
    Call<Object> getMovieCredits(@Path("id") int id,
                                 @Query("api_key") String api_key
    );

    @GET("3/movie/{id}/videos?")
    Call<Object> getMovieVedio(@Path("id") int id,
                               @Query("api_key") String api_key
    );

    @GET("3/movie/now_playing?")
    Call<Object> getMovieSlider(@Query("api_key") String api_key);


    @GET("3/movie/popular?")
    Call<Object> getMoviePopular(@Query("api_key") String api_key);


    @GET("3/search/movie?")
    Call<Object> searchForMovie(@Query("api_key") String api_key,
                                @Query("query") String query);


    @GET("3/movie/now_playing?")
    Call<List<MovieSlider>> getMovieSlider2(@Query("api_key") String api_key);


//    @GET("movie/popular")
//    Call<String> getPopular(@Query("api_key") String api_key);
//
//
//
//
//    @GET("v2/top-headlines?sources=google-news")
//    Call<Object> getNewsFromGoogle(
//            @Query("apiKey") String apiKey);
}
