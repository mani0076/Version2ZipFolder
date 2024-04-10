package com.movieinformation.versionnumber2.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.movieinformation.versionnumber2.R;
import com.movieinformation.versionnumber2.activity.MovieInfoActivity;
import com.movieinformation.versionnumber2.adapter.UrlAdapter;
import com.movieinformation.versionnumber2.database.DatabaseHelper;
import com.movieinformation.versionnumber2.databinding.FragmentSavedImageMovieBinding;
import com.movieinformation.versionnumber2.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedImageMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedImageMovieFragment extends Fragment implements UrlAdapter.DeleteInterface {

    // TODO: Rename parameter arguments, choose names that match
    FragmentSavedImageMovieBinding binding;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private UrlAdapter adapter;
    List<ResponseData> urlList = new ArrayList<>();
    private DatabaseHelper db;
    MovieInfoActivity movieActivity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavedImageMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedImageMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedImageMovieFragment newInstance(String param1, String param2) {
        SavedImageMovieFragment fragment = new SavedImageMovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(requireContext());
        movieActivity = (MovieInfoActivity) getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSavedImageMovieBinding.inflate(getLayoutInflater());
         urlList = getAllUrlsFromDatabase();
         adapter = new UrlAdapter(requireContext(),this);
        binding.rvList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvList.setAdapter(adapter);
        adapter.updateList(urlList);
        return binding.getRoot();
    }
    private List<ResponseData> getAllUrlsFromDatabase() {
        /*
   Retrieves all URLs from the local database using DatabaseHelper.
   Returns a list of ResponseData objects containing the URLs.
*/
        DatabaseHelper  db = new DatabaseHelper(requireContext());
        return db.getAllUrls();

    }

    @Override
    public void onResume() {
        super.onResume();
    //    movieActivity.binding.toolbar.setTitle(getResources().getString(R.string.saved_image));
    }

    @Override
    public void onDeleteButtonClicked(int position) {
        /*
   Shows an alert dialog for confirming the deletion of an item.
   If the user confirms deletion, the item at the specified position is deleted from the database.
   Updates the UI accordingly.
*/
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_alert))
                .setMessage(getString(R.string.delete_message))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String urlToDelete = urlList.get(position).getPoster();
                        boolean isDeleted = db.deleteUrl(urlToDelete);
                        if (isDeleted) {
                            urlList.clear();
                            urlList =
                                    getAllUrlsFromDatabase();
                            adapter.updateList(urlList);

                           Toast.makeText(requireContext(),getString(R.string.item_deleted_message), Toast.LENGTH_SHORT).show();

                        } else {
                            //Toast.makeText(requireContext(),getString(R.string.failed_delete), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();

    }
}