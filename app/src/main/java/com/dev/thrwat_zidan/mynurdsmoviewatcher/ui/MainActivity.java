package com.dev.thrwat_zidan.mynurdsmoviewatcher.ui;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter.MoviesListAdapter;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter.SliderPgerAdapter;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Interface.MovieItemClickListener;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MovieSlider;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.MoviesList;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.databinding.ActivityMainBinding;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.ui.ViewModel.PopularMovieViewModel;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.ui.ViewModel.SliderViewModel;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.ui.ViewModel.TopMovieViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    private ActivityMainBinding binding;
    private SliderViewModel sliderModel;
    private TopMovieViewModel topMovieViewModel;
    private PopularMovieViewModel popularMovieViewModel;
    private SliderPgerAdapter adapter;
     private MoviesListAdapter moviesListAdapter;

    List<MovieSlider> list = new ArrayList<>();

    private int count = 2;
    private int Entered = 0;
    private boolean doubleBackToExitPressedOnce = false;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initViews();
        setUpSlider();

        //setup SliderViewModel
        sliderModel = ViewModelProviders.of(this).get(SliderViewModel.class);
        topMovieViewModel = ViewModelProviders.of(this).get(TopMovieViewModel.class);
        popularMovieViewModel = ViewModelProviders.of(this).get(PopularMovieViewModel.class);

        sliderModel.getSlider();
        topMovieViewModel.getTopMovie();
        popularMovieViewModel.getPopularMovies();


        sliderModel.sliderMutableLiveData.observe(this, movieSliders -> {

            list = movieSliders;
            adapter = new SliderPgerAdapter(MainActivity.this, movieSliders);
            binding.sliderPager.setAdapter(adapter);

        });


        topMovieViewModel.moviesListMutableLiveData.observe(this, moviesLists -> {
            moviesListAdapter = new MoviesListAdapter
                    (moviesLists, MainActivity.this, MainActivity.this);
         binding.recyclerMovie.setAdapter(moviesListAdapter);
        });


        popularMovieViewModel.popularMoviesListMutableLiveData.observe(this, moviesLists -> {
            moviesListAdapter = new MoviesListAdapter(moviesLists, MainActivity.this, MainActivity.this);
            binding.recyclerMoviePopular.setAdapter(moviesListAdapter);
        });
    }

    private void initViews() {
        binding.recyclerMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerMovie.setHasFixedSize(true);
        binding.recyclerMoviePopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerMoviePopular.setHasFixedSize(true);

        binding.edtSearch.setOnTouchListener(new openSearchBar());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieClicked(MoviesList movie, ImageView movieImageView) {

        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imgCover", movie.getBackdrop_path());
        intent.putExtra("imgURL", movie.getPoster_path());
        intent.putExtra("overView", movie.getOverview());
        //intent.putExtra("id", movie.getId());
        common.MOVIE_ID= String.valueOf((movie.getId()));

        Log.i("MOVIiiiiiiiiid", common.MOVIE_ID);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                (MainActivity.this, movieImageView, "sharedName");
        startActivity(intent, options.toBundle());


    }

    private void setUpSlider() {
        //setup timer

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new sliderTimer(), 4000, 6000);

        binding.indecator.setupWithViewPager(binding.sliderPager, true);

    }

    class sliderTimer extends TimerTask {
        @Override
        public void run() {

            MainActivity.this.runOnUiThread(() -> {

                if (binding.sliderPager.getCurrentItem() < list.size() - 1) {
                    binding.sliderPager.setCurrentItem(binding.sliderPager.getCurrentItem() + 1);

                } else {

                    binding.sliderPager.setCurrentItem(0);
                }

            });


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.home_menu, menu);
        imageView = new ImageView(this);
        imageView.setPadding(0, 0, 50, 0);
        imageView.setImageResource(R.drawable.ic_search_white_24dp);


        menu.getItem(0).setActionView(imageView);
        imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_then_rotate));
        imageView.setOnClickListener(view -> {


            if (count % 2 == 0) {
                imageView.setImageDrawable(getDrawable(R.drawable.ic_close_white_24dp));
                Drawable drawable = imageView.getDrawable();
                if (drawable instanceof AnimatedVectorDrawable) {
                    ((AnimatedVectorDrawable) drawable).start();
                }
                binding.edtSearch.setVisibility(View.VISIBLE);
                binding.edtSearch.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.from_right_current));
                Entered = 1;
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.ic_search_white_24dp));
                Drawable drawable = imageView.getDrawable();
                if (drawable instanceof AnimatedVectorDrawable) {
                    AnimatedVectorDrawable animatedVectorDrawable = ((AnimatedVectorDrawable) drawable);
                    animatedVectorDrawable.start();
                }
                binding.edtSearch.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.from_current_left));
                binding.edtSearch.setVisibility(View.GONE);
                Entered = 2;
            }
            count++;


        });

        return true;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            exitFromApp();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() ->
                doubleBackToExitPressedOnce = false, 2000);
    }

    private void exitFromApp() {

        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        intent.putExtra("EXIT", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Explode explode = new Explode();
            explode.setDuration(1100);
            explode.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }
    }

    private class openSearchBar implements View.OnTouchListener {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // final int DRAWABLE_LEFT = 0;
            //final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            //  final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (binding.edtSearch.getRight() - binding.edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // your action here
                    if (!TextUtils.isEmpty(binding.edtSearch.getText().toString())) {

                         Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("searchId", binding.edtSearch.getText().toString());
                        common.currentSearchedID = binding.edtSearch.getText().toString();
                        startActivity(intent);
                        Log.i("SEAAAAAAAAAAAAAAAAAArch", common.currentSearchedID);

                    } else {
                        Toast.makeText(MainActivity.this, "Search field Empty", Toast.LENGTH_SHORT).show();


                    }
                    binding.edtSearch.setText("");
                    binding.edtSearch.setVisibility(View.GONE);
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_search_white_24dp));
                    Entered = 2;
                    return false;
                }
            }
            return false;
        }
    }


}
