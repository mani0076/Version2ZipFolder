package com.movieinformation.versionnumber2.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.movieinformation.versionnumber2.databinding.MovieNameItemBinding;
import com.movieinformation.versionnumber2.model.ResponseData;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<ResponseData> movieList = new ArrayList<>();

    public MovieAdapter() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MovieNameItemBinding binding;

        public ViewHolder(MovieNameItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(ResponseData movieSearchModel) {
            binding.tvTitle.setText(movieSearchModel.getTitle());
            binding.tvActor.setText(movieSearchModel.getActors());
            binding.tvYear.setText(movieSearchModel.getYear());
            System.out.println("title " + movieSearchModel.getTitle() + ", actor: " + movieSearchModel.getActors() + ", image : " + movieSearchModel.getPoster());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieNameItemBinding binding = MovieNameItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(movieList.get(position));
        Glide.with(holder.itemView)
                .load(movieList.get(position).getPoster())
                .into(holder.binding.ivPoster);

    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addMovies(ArrayList<ResponseData> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }
}
