package com.example.findmydorm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://10.21.130.172/findmydorm/";

    private SharedPreferences prefs;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<DormItem> dorms = new ArrayList<>();
    private Spinner spinnerLocation;
    private List<String> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerLocation = findViewById(R.id.spinnerLocation);
        recyclerView = findViewById(R.id.recyclerViewDorms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(dorms);
        recyclerView.setAdapter(adapter);

        fetchLocations();

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String loc = locations.get(pos);
                if ("All".equals(loc)) {
                    fetchDormList();
                } else {
                    fetchDormListByLocation(loc);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // honor whatever filter is currently chosen
        String selected = (String) spinnerLocation.getSelectedItem();
        if (selected != null && !"All".equals(selected)) {
            fetchDormListByLocation(selected);
        } else {
            fetchDormList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        View v = menu.findItem(R.id.action_profile).getActionView();
        v.setOnClickListener( _ignored -> onOptionsItemSelected(menu.findItem(R.id.action_profile)) );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {

            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        if (id == R.id.action_rent) {

            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
            startActivity(new Intent(this,
                    isLoggedIn ? AddDorm.class : LoginSignupActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchLocations() {
        String url = BASE_URL + "spinner_dorm.php";
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                resp -> {
                    try {
                        if (!resp.getBoolean("success")) throw new JSONException("no locs");
                        JSONArray arr = resp.getJSONArray("locations");
                        locations.clear();
                        locations.add("All");
                        for (int i = 0; i < arr.length(); i++) {
                            locations.add(arr.getString(i));
                        }
                        ArrayAdapter<String> aa = new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_dropdown_item,
                                locations
                        );
                        spinnerLocation.setAdapter(aa);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Could not load locations", Toast.LENGTH_SHORT).show();
                    }
                },
                err -> Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(req);
    }

    private void fetchDormList() {
        loadDormsFrom(BASE_URL + "list_alldorms.php");
    }

    private void fetchDormListByLocation(String loc) {
        try {
            String q = URLEncoder.encode(loc, StandardCharsets.UTF_8.name());
            loadDormsFrom(BASE_URL + "filter_by_location.php?location=" + q);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid location", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDormsFrom(String url) {
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                resp -> {
                    try {
                        if (!resp.getBoolean("success")) {
                            Toast.makeText(this,
                                    resp.optString("message","No dorms found"),
                                    Toast.LENGTH_SHORT).show();
                            dorms.clear();
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        JSONArray arr = resp.getJSONArray("dorms");
                        dorms.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject o = arr.getJSONObject(i);
                            int id       = o.getInt("dorm_id");
                            String name  = o.getString("dorm_name");
                            double price = o.getDouble("price");
                            String loc   = o.getString("location");
                            ArrayList<String> images = new ArrayList<>();
                            JSONArray imgs = o.getJSONArray("images");
                            for (int j = 0; j < imgs.length(); j++) {
                                String rel = imgs.getString(j);
                                Uri full = Uri.parse(BASE_URL)
                                        .buildUpon()
                                        .appendEncodedPath(rel.startsWith("/") ? rel.substring(1) : rel)
                                        .build();
                                images.add(full.toString());
                            }
                            dorms.add(new DormItem(id, name, price, "", loc, images, "", ""));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parse error", Toast.LENGTH_SHORT).show();
                    }
                },
                err -> Toast.makeText(this,
                        "Network error: " + err.getMessage(),
                        Toast.LENGTH_LONG).show()
        );
        Volley.newRequestQueue(this).add(req);
    }
}
