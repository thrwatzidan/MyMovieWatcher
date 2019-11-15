package com.dev.thrwat_zidan.mynurdsmoviewatcher.ui.ViewModel;

import android.util.Log;

import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MovieSlider;
 import com.dev.thrwat_zidan.mynurdsmoviewatcher.Retofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderViewModel extends ViewModel {

       public MutableLiveData<List<MovieSlider>> sliderMutableLiveData = new MutableLiveData<>();
        List<MovieSlider> sliderLists = new ArrayList<>();

    public void getSlider() {

        RetrofitClient.getInstance()
                .getMovieSlide()
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()) {

                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(json);
                                JSONArray result = jsonObject.getJSONArray("results");
                                Type type = new TypeToken<List<MovieSlider>>() {
                                }.getType();
                                 //sliderLists = gson.fromJson(String.valueOf(result), type);
                                sliderMutableLiveData.setValue(gson.fromJson(String.valueOf(result), type));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            // Log.i("Sliddddddddddddr", String.valueOf(sliderLists.size()));


                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.i("SLIDER_LOG", t.getMessage());

                    }
                });


    }

}
