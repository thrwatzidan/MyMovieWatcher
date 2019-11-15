package com.dev.thrwat_zidan.mynurdsmoviewatcher.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter.CastAdapter;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MovieCast;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.databinding.ActivityMovieDetailBinding;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.ui.ViewModel.CastViewModel;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private String MASTER_SITE = "YouTube";
    private  ActivityMovieDetailBinding binding;
    private CastViewModel castViewModel;
    private int Id;
    private CastAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        initViews();

        castViewModel = ViewModelProviders.of(this).get(CastViewModel.class);
        castViewModel.GetMovieCastDetail();

        castViewModel.CastListMutableLiveData.observe(this, movieCasts -> {

            while (movieCasts.size() > 10) movieCasts.remove(movieCasts.size() - 1);

            if (movieCasts.size() != 0) {
                adapter = new CastAdapter(movieCasts, MovieDetailActivity.this);
                binding.recyclerCast.setAdapter(adapter);

            } else {
                Toast.makeText(MovieDetailActivity.this, "No Cast Data", Toast.LENGTH_SHORT).show();
                binding.recyclerCast.setVisibility(View.GONE);
            }


        });
    }

    private void initViews() {
        binding.recyclerCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        String movieTitle = getIntent().getExtras().getString("title");
        String imgResourceId = getIntent().getExtras().getString("imgURL");
        String imgCoverId = getIntent().getExtras().getString("imgCover");
        String overView = getIntent().getExtras().getString("overView");
        Id = getIntent().getExtras().getInt("id");

        Glide.with(this).load(common.IMAGE_LINK+imgResourceId).into(binding.imgDetailMovieThumbinal);
        //  Log.i("Immmmmmmmmage", common.IMAGE_LINK + imgResourceId);
        Glide.with(this).load(common.IMAGE_LINK+imgCoverId).into(binding.imgDetailMovieCover);
        binding.txtDetailMovieTitle.setText(movieTitle);
        binding.txtMovieDetailDesc.setText(overView);
        getSupportActionBar().setTitle(movieTitle);

        //Setup Anim
        binding.imgDetailMovieCover.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scal_animation));
        binding.fabPlay.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scal_animation));
        binding.recyclerCast.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerCast.setHasFixedSize(true);
    }
}
