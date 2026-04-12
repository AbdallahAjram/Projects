package com.example.findmydorm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.Holder> {
    private static final String BASE_URL = "http://10.21.130.172/findmydorm/";
    private final Context ctx;
    private final List<DormItem> items;

    public ListingAdapter(Context ctx, List<DormItem> items) {
        this.ctx= ctx;
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx)
                .inflate(R.layout.item_listing, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int pos) {
        DormItem d = items.get(pos);

        h.tvName.setText(d.getDormName());
        h.tvLoc .setText(d.getLocation());
        h.tvPrice.setText(String.format("$%.2f", d.getPrice()));

        h.btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(ctx, EditListingActivity.class);
            i.putExtra("dorm_id",d.getId());
            i.putExtra("price", d.getPrice());
            i.putExtra("location", d.getLocation());
            i.putExtra("description", d.getShortDescription());
            ctx.startActivity(i);
        });


        h.btnDelete.setOnClickListener(v ->
                new android.app.AlertDialog.Builder(ctx)
                        .setTitle("Delete listing?")
                        .setMessage("Are you sure you want to delete this dorm?")
                        .setPositiveButton("Yes", (__, ___) -> {
                            String url = BASE_URL + "delete_dorm.php";
                            StringRequest req = new StringRequest(
                                    Request.Method.POST, url,
                                    response -> {
                                        Toast.makeText(ctx, "Deleted", Toast.LENGTH_SHORT).show();
                                        items.remove(pos);
                                        notifyItemRemoved(pos);
                                        notifyItemRangeChanged(pos, items.size());
                                    },
                                    error -> Toast.makeText(ctx, "Delete failed", Toast.LENGTH_SHORT).show()
                            ) {
                                @Override
                                protected Map<String,String> getParams() {
                                    Map<String,String> p = new HashMap<>();
                                    p.put("dorm_id", String.valueOf(d.getId()));
                                    return p;
                                }
                            };
                            Volley.newRequestQueue(ctx).add(req);
                        })
                        .setNegativeButton("No", null)
                        .show()
        );
    }

    @Override public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView tvName, tvLoc, tvPrice;
        Button btnEdit, btnDelete;

        Holder(@NonNull View v) {
            super(v);
            tvName   = v.findViewById(R.id.tvListingName);
            tvLoc    = v.findViewById(R.id.tvListingLoc);
            tvPrice  = v.findViewById(R.id.tvListingPrice);
            btnEdit  = v.findViewById(R.id.btnEdit);
            btnDelete= v.findViewById(R.id.btnDelete);
        }
    }
}
