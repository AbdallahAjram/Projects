package com.example.findmydorm;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewDorm extends AppCompatActivity {
    private static final String BASE_URL = "http://10.21.130.172/findmydorm/";

    private Button     buttonBack;
    private ProgressBar progressBar;
    private ScrollView  contentScroll;
    private ImageView   imageView;
    private TextView    tvName, tvLocation, tvPrice, tvShortDesc, tvOwnerName;
    private Button      buttonCall;
    private String      ownerPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dorm);

        // bind views
        buttonBack    = findViewById(R.id.buttonBack);
        progressBar   = findViewById(R.id.progressBar);
        contentScroll = findViewById(R.id.contentScroll);
        imageView     = findViewById(R.id.imageViewDetail);
        tvName        = findViewById(R.id.textViewName);
        tvLocation    = findViewById(R.id.textViewLocation);
        tvPrice       = findViewById(R.id.textViewPrice);
        tvShortDesc   = findViewById(R.id.textViewShortDescription);
        tvOwnerName   = findViewById(R.id.textViewOwnerName);
        buttonCall    = findViewById(R.id.buttonCall);

        // initial UI
        progressBar.setVisibility(View.VISIBLE);
        contentScroll.setVisibility(View.GONE);
        buttonBack.setOnClickListener(v -> finish());

        int dormId = getIntent().getIntExtra("dorm_id", -1);
        if (dormId < 0) {
            Toast.makeText(this, "Invalid dorm selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        fetchDormDetails(dormId);
    }

    private void fetchDormDetails(int dormId) {
        String url = BASE_URL + "dorm_info.php?dorm_id=" + dormId;
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                this::handleResponse,
                err -> {
                    Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
                    finish();
                }
        );
        Volley.newRequestQueue(this).add(req);
    }

    private void handleResponse(JSONObject response) {
        try {
            if (!response.getBoolean("success")) {
                Toast.makeText(this,
                        response.optString("message","Dorm not found"),
                        Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            JSONObject d = response.getJSONObject("dorm");

            // populate
            tvName.setText(d.getString("dorm_name"));
            tvLocation.setText(d.getString("location"));
            tvPrice.setText(String.format("$%.2f", d.getDouble("price")));
            tvShortDesc.setText(d.getString("short_description"));

            String ownerName = d.getString("owner_name");
            ownerPhoneNumber = d.getString("owner_phone");
            tvOwnerName.setText("Owner: " + ownerName);

            JSONArray imgs = d.getJSONArray("images");
            if (imgs.length() > 0) {
                String rel = imgs.getString(0);
                if (rel.startsWith("/")) rel = rel.substring(1);
                Glide.with(this)
                        .load(BASE_URL + rel)
                        .centerCrop()
                        .placeholder(R.drawable.background)
                        .error(R.drawable.background)
                        .into(imageView);
            }

            // dialer on click
            buttonCall.setOnClickListener(v ->
                    startActivity(new Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:" + ownerPhoneNumber)
                    ))
            );

            // show content
            progressBar.setVisibility(View.GONE);
            contentScroll.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Data format error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
