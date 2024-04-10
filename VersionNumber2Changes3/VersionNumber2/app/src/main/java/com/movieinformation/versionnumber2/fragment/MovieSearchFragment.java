package com.movieinformation.versionnumber2.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.movieinformation.versionnumber2.R;
import com.movieinformation.versionnumber2.activity.MovieInfoActivity;
import com.movieinformation.versionnumber2.adapter.MovieAdapter;

import com.movieinformation.versionnumber2.databinding.FragmentMovieSearchBinding;
import com.movieinformation.versionnumber2.interfaces.ClickInterface;
import com.movieinformation.versionnumber2.model.ResponseData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieSearchFragment extends Fragment implements ClickInterface {

    FragmentMovieSearchBinding binding;

    MovieAdapter movieAdapter;
    MovieInfoActivity mainActivity;
    private RequestQueue queue;
    private   String API_KEY = "6c9862c2";
    private String API_URL = "http://www.omdbapi.com/?apikey="+API_KEY+"&t=";
    private ArrayList<ResponseData> movieList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieSearchFragment newInstance(String param1, String param2) {
        MovieSearchFragment fragment = new MovieSearchFragment();
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
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieSearchBinding.inflate(getLayoutInflater());
        movieAdapter = new MovieAdapter(this);
        binding.recyclerViewMovies.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMovies.setAdapter(movieAdapter);
        loadSearchedText();


        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextMovieTitle.getText().toString().isEmpty()){
                    binding.editTextMovieTitle.setError("Enter your Movie Name");
                }else{
                    binding.progressBar.setVisibility(View.VISIBLE);
                    searchMovies(binding.editTextMovieTitle.getText().toString());
                    saveMovie(binding.editTextMovieTitle.getText().toString());
                }
            }
        });
        return binding.getRoot();
    }
    private void saveMovie(String title) {
        /*
   Saves the authentication state of the user in SharedPreferences.
   Sets the "itemsearched" boolean flag to true.
   Saves the searched movie title under the key "title".
*/
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("itemsearched", true);
        editor.putString("title", title);
        editor.apply();
    }
    private void loadSearchedText() {
        /*
   Loads the searched movie title from SharedPreferences.
   Sets the movie title in the EditText field.
   Searches for movies based on the searched title if it is not empty.
*/

        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
        String searchedItem = sharedPreferences.getString("title", "");
        binding.editTextMovieTitle.setText(searchedItem);
        if(!searchedItem.isEmpty()) {
            searchMovies(searchedItem);
        }
    }


    private void searchMovies(String movieTitle) {
        try {
            String encodedTitle = URLEncoder.encode(movieTitle, "UTF-8");
            String url = API_URL + encodedTitle;
             queue = Volley.newRequestQueue(requireContext());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            binding.progressBar.setVisibility(View.GONE);
                            Log.e("Volley", "response " + response);

                            String title = response.optString("Title", "");
                            String year = response.optString("Year", "");
                            String runTime = response.optString("Runtime", "");
                            String actors = response.optString("Actors", "");
                            String poster = response.optString("Poster", "");
                            String plot = response.optString("Plot", "");
                            // List<ResponseData.Rating> ratingList = parseRatings(response);
                            Snackbar.make(binding.buttonSearch,"Search",Snackbar.LENGTH_SHORT).show();
                            List<ResponseData.Rating> ratingList = new ArrayList<>();
                            JSONArray ratingsArray = response != null ? response.optJSONArray("Ratings") : null;
                            if (ratingsArray != null) {
                                for (int i = 0; i < ratingsArray.length(); i++) {
                                    try {
                                        JSONObject ratingObject = ratingsArray.getJSONObject(i);
                                        String source = ratingObject.getString("Source");
                                        String value = ratingObject.getString("Value");
                                        ratingList.add(new ResponseData.Rating(source, value));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            ResponseData model = new ResponseData();
                            model.setTitle(title);
                            model.setYear(year);
                            model.setRuntime(runTime);
                            model.setActors(actors);
                            model.setPoster(poster);
                            model.setPlot(plot);
                            model.setRatings(ratingList);

                            movieList.clear();
                            movieList.add(model);
                            System.out.println("MovieList: " + movieList.toString());

                               movieAdapter.addMovies(movieList);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle errors
                            Log.e("Volley", "Error: " + error);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization","Bearer "+ API_KEY);
                    return headers;
                }
            };
            jsonObjectRequest.setTag(this);
            queue.add(jsonObjectRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(queue!=null){
            queue.cancelAll(this);
        }
    }
    @Override
    public void onDetail(ResponseData movieSearchModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", (Serializable) movieSearchModel);
        mainActivity.getNavController().navigate(R.id.movieDetailFragment,bundle);
    }

}