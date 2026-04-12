package com.example.findmydorm;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<DormItem> dormList;
    public MyAdapter(List<DormItem> dormList) {
        this.dormList = dormList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        DormItem dorm = dormList.get(pos);

        holder.tvName.setText(dorm.getDormName());
        holder.tvLocation.setText(dorm.getLocation());
        holder.tvPrice.setText("$" + dorm.getPrice());

        if (!dorm.getImages().isEmpty()) {
            Glide.with(holder.imageView.getContext())
                    .load(dorm.getImages().get(0))
                    .centerCrop()
                    .placeholder(R.drawable.background)
                    .error(R.drawable.background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.background);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ViewDorm.class);
            i.putExtra("dorm_id", dorm.getId());      // ← critical line
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return dormList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName, tvLocation, tvPrice;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView  = itemView.findViewById(R.id.imageView);
            tvName     = itemView.findViewById(R.id.textView);
            tvLocation = itemView.findViewById(R.id.textView2);
            tvPrice    = itemView.findViewById(R.id.textView3);
        }
    }
}
