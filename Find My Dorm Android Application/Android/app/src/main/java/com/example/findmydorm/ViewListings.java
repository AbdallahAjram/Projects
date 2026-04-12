package com.example.findmydorm;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewListings extends AppCompatActivity {
    private static final String BASE_URL = "http://10.21.130.172/findmydorm/";

    private RecyclerView           rv;
    private ListingAdapter         adapter;
    private ArrayList<DormItem>    listings = new ArrayList<>();
    private String phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listings);

        rv = findViewById(R.id.recyclerViewListings);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListingAdapter(this, listings);
        rv.setAdapter(adapter);

        phone = getIntent().getStringExtra("phone");
        if (phone == null || phone.isEmpty()) {
            Toast.makeText(this, "No user phone", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        fetchMyListings(phone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMyListings(phone);
    }

    private void fetchMyListings(String phone) {
        String url = BASE_URL + "user_listings.php?phone=" + Uri.encode(phone);
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                resp -> {
                    try {
                        if (!resp.getBoolean("success")) {
                            Toast.makeText(this,
                                    resp.optString("message","No listings"),
                                    Toast.LENGTH_SHORT).show();
                            listings.clear();
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        JSONArray arr = resp.getJSONArray("dorms");
                        listings.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject o = arr.getJSONObject(i);

                            int id       = o.getInt("id");
                            String name  = o.getString("dorm_name");
                            double price = o.getDouble("price");
                            String loc   = o.getString("location");
                            // pull the new field:
                            String desc  = o.optString("short_description","");

                            listings.add(new DormItem(
                                    id,
                                    name,
                                    price,
                                    desc,
                                    loc,
                                    new ArrayList<>(),
                                    "", ""
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this,
                                "Parse error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                },
                err -> Toast.makeText(this,
                        "Network error: " + err.getMessage(),
                        Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(req);
    }

}
