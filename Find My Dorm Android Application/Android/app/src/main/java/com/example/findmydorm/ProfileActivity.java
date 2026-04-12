package com.example.findmydorm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://10.21.130.172/findmydorm/";
    private SharedPreferences prefs;
    private TextView tvName, tvPhone, tvCount;
    private Button btnLogout, btnViewListings;

    @Override
    protected void onResume() {
        super.onResume();
        String phone = prefs.getString("userPhone", null);
        if (phone != null) fetchProfile(phone);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        tvName = findViewById(R.id.textViewProfileName);
        tvPhone = findViewById(R.id.textViewProfilePhone);
        tvCount = findViewById(R.id.textViewProfileDormCount);
        btnLogout = findViewById(R.id.buttonLogout);
        btnViewListings = findViewById(R.id.buttonViewListings);

        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        String phone = prefs.getString("userPhone", null);
        if (phone == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            fetchProfile(phone);
        }
    }

    private void fetchProfile(String phone) {
        String url = BASE_URL + "get_user_info.php?phone=" + Uri.encode(phone);
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                this::handleResponse,
                err -> Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(req);
    }

    private void handleResponse(JSONObject resp) {
        try {
            if (!resp.getBoolean("success")) {
                Toast.makeText(this,
                        resp.optString("message","Fetch failed"),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject u = resp.getJSONObject("user");
            String name = u.getString("name");
            String phone = u.getString("phone");
            int count = u.getInt("dorm_count");

            tvName.setText("Name: "  + name);
            tvPhone.setText("Phone: " + phone);
            tvCount.setText("Your listings: " + count);

            if (count > 0) {
                btnViewListings.setVisibility(View.VISIBLE);
                btnViewListings.setOnClickListener(v -> {
                    Intent i = new Intent(this, ViewListings.class);
                    i.putExtra("phone", phone);
                    startActivity(i);
                });
            } else {
                btnViewListings.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Data error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
