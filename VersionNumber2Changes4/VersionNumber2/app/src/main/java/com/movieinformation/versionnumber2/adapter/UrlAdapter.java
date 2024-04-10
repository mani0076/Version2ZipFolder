package com.movieinformation.versionnumber2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.movieinformation.versionnumber2.R;
import com.movieinformation.versionnumber2.databinding.UrlItemBinding;
import com.movieinformation.versionnumber2.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.ViewHolder> {
    private Context context;
    private DeleteInterface deleteInterface;
    private List<ResponseData> urlList = new ArrayList<>();

    public UrlAdapter(Context context, DeleteInterface deleteInterface) {
        this.context = context;
        this.deleteInterface = deleteInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UrlItemBinding binding = UrlItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = urlList.get(position).getPoster();
        holder.binding.tvTitle.setText(urlList.get(position).getTitle());
        holder.binding.tvYear.setText(urlList.get(position).getYear());
        holder.binding.tvActors.setText(urlList.get(position).getActors());
        // Assuming you have an ImageView in your item layout
        // Glide can load URLs into an ImageView
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.urlimg);

        holder.binding.delete.setOnClickListener(v -> {
            // Handle delete button click
            deleteInterface.onDeleteButtonClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public void updateList(List<ResponseData> urls){
        urlList.clear();
        urlList.addAll(urls);
        notifyDataSetChanged();
    }

    /*public void addUrl(String url) {
        urlList.add(url);
        notifyItemInserted(urlList.size() - 1);
    }*/

    public void removeUrl(int position) {
        if (position >= 0 && position < urlList.size()) {
            urlList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface DeleteInterface {
        void onDeleteButtonClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        UrlItemBinding binding;

        public ViewHolder(UrlItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}