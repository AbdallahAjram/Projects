package com.example.findmydorm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditListingActivity extends AppCompatActivity {
    private int    dormId;
    private EditText etPrice, etLocation, etDesc;
    private Button   btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);

        Toolbar tb = findViewById(R.id.toolbar_edit);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etPrice    = findViewById(R.id.etEditPrice);
        etLocation = findViewById(R.id.etEditLocation);
        etDesc     = findViewById(R.id.etEditDesc);
        btnSave    = findViewById(R.id.btnSaveEdit);

        Intent in = getIntent();
        dormId    = in.getIntExtra("dorm_id", -1);
        double p  = in.getDoubleExtra("price",  0);
        String loc= in.getStringExtra("location");
        String d  = in.getStringExtra("description");

        // populate fields
        etPrice.setText(String.valueOf(p));
        etLocation.setText(loc);
        etDesc.setText(d);

        btnSave.setOnClickListener(v -> doSave());
    }

    private void doSave() {
        String price    = etPrice.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String desc     = etDesc.getText().toString().trim();

        if (price.isEmpty() || location.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.21.130.172/findmydorm/edit_dorm.php";
        StringRequest req = new StringRequest(Request.Method.POST, url,
                resp -> {
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    // signal to parent to refresh
                    setResult(RESULT_OK);
                    finish();
                },
                err -> Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> p = new HashMap<>();
                p.put("dorm_id",     String.valueOf(dormId));
                p.put("price",       price);
                p.put("location",    location);
                p.put("description", desc);
                return p;
            }
        };

        Volley.newRequestQueue(this).add(req);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
