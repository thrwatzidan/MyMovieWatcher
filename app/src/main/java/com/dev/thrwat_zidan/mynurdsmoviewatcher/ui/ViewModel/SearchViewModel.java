package com.dev.thrwat_zidan.mynurdsmoviewatcher.ui.ViewModel;

import android.util.Log;
import android.view.View;

import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.SearchMovie;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Retofit.RetrofitClient;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {


    public  MutableLiveData<List<SearchMovie>> searchMutableLiveData = new MutableLiveData<>();


    public void getSearchedResult() {

        RetrofitClient.getInstance().getSearchedMovies()
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
                                Type type = new TypeToken<List<SearchMovie>>(){}.getType();

                                searchMutableLiveData.setValue(gson.fromJson(String.valueOf(result), type));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                         }

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.i("seeearch", String.valueOf(t.getMessage()));

                    }
                });




    }
}
