package com.movieinformation.versionnumber2.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.movieinformation.versionnumber2.databinding.RatingItemBinding;
import com.movieinformation.versionnumber2.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private List<ResponseData.Rating> ratingList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSource;
        private final TextView tvValue;

        public ViewHolder(RatingItemBinding binding) {
            super(binding.getRoot());
            tvSource = binding.tvSoruce;
            tvValue = binding.tvValue;
        }

        public void bindData(ResponseData.Rating rating) {
            tvSource.setText(rating.getSource());
            tvValue.setText(rating.getValue());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RatingItemBinding binding = RatingItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(ratingList.get(position));
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public void addRating(ArrayList<ResponseData.Rating> ratings) {
        ratingList.addAll(ratings);
        notifyDataSetChanged();
    }
}
