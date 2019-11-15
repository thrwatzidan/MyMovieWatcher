package com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MovieSlider;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.Video;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Retofit.IMovieApi;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Retofit.RetrofitClient;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderPgerAdapter extends PagerAdapter {

    private Context context;
    private List<MovieSlider> slideList;
    private IMovieApi myAPI;
    private ArrayList<Video> videoList ;
    private FloatingActionButton floatingActionButton;
    private String MASTER_SITE = "YouTube";



    public SliderPgerAdapter(Context context, List<MovieSlider> slideList) {
        this.context = context;
        this.slideList = slideList;
         videoList = new ArrayList<>();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider_item, null);

        ImageView slid_img = view.findViewById(R.id.slider_img);
        TextView slid_txt =  view.findViewById(R.id.slid_txt_title);
          floatingActionButton = view.findViewById(R.id.floatingActionButton);

     //   getVideo(slideList.get(position).getId());




        if (!TextUtils.isEmpty(slideList.get(position).getPoster_path()))
            Glide.with(context).load(common.IMAGE_LINK + slideList.get(position).getPoster_path())
            .apply(RequestOptions.placeholderOf(R.drawable.movie_app))
                    .into(slid_img);
        else
            Glide.with(context).load(R.drawable.movie_app).into(slid_img);


        slid_txt.setText(slideList.get(position).getTitle());

        container.addView(view);

        return view;
    }


    public void seList(List<MovieSlider> movieSliders) {
        this.slideList = movieSliders;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return slideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


    private void getVideo(int id) {
        myAPI.getMovieVedio(id,common.DB_API)
                .enqueue(new Callback<Object>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {

                        if (response.isSuccessful() && response.body()!=null) {
                          //  Log.i("Sliveeedio", String.valueOf(response.body()));
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(json);
                                JSONArray result = jsonObject.getJSONArray("results");
                                Type type = new TypeToken<List<Video>>(){}.getType();
                                videoList = gson.fromJson(String.valueOf(result), type);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            for (Video m : videoList) {
//                                Log.i("Sliveeedio", m.getName());
//                            }

                            String videoKey = videoList.get(0).getKey();
                            String videoSite = videoList.get(0).getSite();

                            if (!TextUtils.isEmpty(videoSite)&&videoSite.equals(MASTER_SITE)){

                                floatingActionButton.setOnClickListener(v->{

//                                    Intent intent = new Intent(context, PlayerActivity.class);
//                                    intent.putExtra("videoKey", videoKey);
////                                ActivityOptions options =
////                                        ActivityOptions.makeSceneTransitionAnimation(MovieDetailActivity.this);
////                                startActivity(intent,options.toBundle());
//                                    context.startActivity(intent);
                                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                                });
                            }else{
                                Toast.makeText(context, "sorry no valid video", Toast.LENGTH_SHORT).show();
                            }
                            //viewCastData(movieCasts);

                        }

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.i("Sliveeedio", t.getMessage());

                    }
                });
    }
}
