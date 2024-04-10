package com.movieinformation.versionnumber2.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.movieinformation.versionnumber2.R;
import com.movieinformation.versionnumber2.activity.MovieInfoActivity;
import com.movieinformation.versionnumber2.adapter.RatingAdapter;
import com.movieinformation.versionnumber2.database.DatabaseHelper;
import com.movieinformation.versionnumber2.databinding.FragmentMovieDetailBinding;
import com.movieinformation.versionnumber2.model.ResponseData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    FragmentMovieDetailBinding binding;

    DatabaseHelper db ;
    MovieInfoActivity mainActivity;
    private ArrayList<ResponseData.Rating> ratingList = new ArrayList<>();
   RatingAdapter ratingAdapter;
   ResponseData responseData;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MovieInfoActivity) getActivity();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
             responseData = (ResponseData) getArguments().getSerializable("model");
            System.out.println("GetModelDetail: "+responseData.getRatings());
        }
    }

    @SuppressLint("WrongThread")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment4
        binding = FragmentMovieDetailBinding.inflate(getLayoutInflater());
        Toast.makeText(requireContext(),"Data Loaded",Toast.LENGTH_SHORT).show();
        Glide.with(mainActivity)
                .load(responseData.getPoster())
                .into(binding.ivPoster);


        binding.tvTitle.setText(responseData.getTitle());
        binding.tvActor.setText(responseData.getActors());
        binding.tvYear.setText(responseData.getYear());
        binding.tvPlot.setText(responseData.getPlot());
        binding.tvRunTime.setText(responseData.getRuntime());
        ratingAdapter = new RatingAdapter();
        binding.rvList.setLayoutManager(new LinearLayoutManager(mainActivity));
        binding.rvList.setAdapter(ratingAdapter);
        ratingList.addAll(responseData.getRatings());
        ratingAdapter.addRating(ratingList);

        binding.btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(mainActivity);
                boolean dataSaved = db.insertMovies(responseData);
                if(dataSaved){
                    Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT).show();
                    mainActivity.getNavController().navigate(R.id.savedImageMovieFragment);

                }else{
                    Log.e("SaveImage", "Failed to save image URL: " + responseData.getPoster());
                }
            }
        });

        return binding.getRoot();
    }
}