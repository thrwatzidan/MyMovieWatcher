package com.dev.thrwat_zidan.mynurdsmoviewatcher.ui;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.thrwat_zidan.mynurdsmoviewatcher.Adapter.SearchAdapter;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Interface.SearchItemCliclListener;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Model.SearchMovie;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.Retofit.IMovieApi;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.common.common;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.databinding.ActivitySearchBinding;
import com.dev.thrwat_zidan.mynurdsmoviewatcher.ui.ViewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity implements SearchItemCliclListener {

    private SearchViewModel searchViewModel;
    private ActivitySearchBinding binding;
    private SearchAdapter searchAdapter;


       private String searchId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        initViews();

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.getSearchedResult();


        searchViewModel.searchMutableLiveData.observe(this, searchMovies -> {

            if (searchMovies.size() != 0) {
                searchAdapter = new SearchAdapter(searchMovies, SearchActivity.this, SearchActivity.this);
                binding.recyclerSearch.setAdapter(searchAdapter);
                binding.txtSearchErea.setText(new StringBuilder("All movies have ").append(searchId).append(" word"));

            } else {
                binding.recyclerSearch.setVisibility(View.GONE);
                binding.txtSearchErea.setText("Ca not find any film with this " + searchId);
            }


        });


    }


    @SuppressLint("LongLogTag")
    private void initViews() {

        binding.recyclerSearch.setHasFixedSize(true);
        binding.recyclerSearch.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        searchId = getIntent().getExtras().getString("searchId");

        Log.i("SEAAAAAAAAAAAAAAAAAArch22", common.currentSearchedID);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieClicked(SearchMovie movie, ImageView movieImageView) {


        Intent intent = new Intent(SearchActivity.this, MovieDetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imgCover", movie.getBackdrop_path());
        intent.putExtra("imgURL", movie.getPoster_path());
        intent.putExtra("overView", movie.getOverview());
        intent.putExtra("id", movie.getId());

        common.MOVIE_ID= String.valueOf((movie.getId()));

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                (SearchActivity.this, movieImageView, "sharedName");
        startActivity(intent, options.toBundle());


    }

}
