package com.movieinformation.versionnumber2.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.movieinformation.versionnumber2.R;
import com.movieinformation.versionnumber2.databinding.ActivityMovieInfoBinding;

public class MovieInfoActivity extends AppCompatActivity {

    NavController navController;
    ActivityMovieInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movie_fragment);
        navController = navHostFragment.getNavController();
        setSupportActionBar(binding.toolbar);


    }
    public NavController getNavController() {
        return navController;
    }



}