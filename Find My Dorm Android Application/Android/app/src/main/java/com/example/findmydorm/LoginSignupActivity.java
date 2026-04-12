package com.example.findmydorm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginSignupActivity extends AppCompatActivity {
    private EditText nameEt, phoneEt, passEt;
    private Button loginBtn, signupBtn;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        // bind views
        nameEt   = findViewById(R.id.editTextName);
        phoneEt  = findViewById(R.id.editTextPhone);
        passEt   = findViewById(R.id.editTextPassword);
        loginBtn  = findViewById(R.id.buttonLogin);
        signupBtn = findViewById(R.id.buttonSignup);

        prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        loginBtn.setOnClickListener(v -> loginUser());
        signupBtn.setOnClickListener(v -> signupUser());
    }

    private void loginUser() {
        String phone    = phoneEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Phone & password required", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.21.130.172/findmydorm/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        if (res.getBoolean("success")) {
                            JSONObject user = res.getJSONObject("user");
                            // save login state and user info
                            prefs.edit()
                                    .putBoolean("isLoggedIn", true)
                                    .putString("userPhone", user.getString("phone"))
                                    .putString("userName",  user.getString("name"))
                                    .putInt("userId",       user.getInt("id"))
                                    .apply();

                            // go to main screen
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this,
                                    res.optString("error", "Login failed"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this,
                                "Response parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this,
                        "Network error: " + error.getMessage(),
                        Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phone",    phone);
                params.put("password", password);
                // no name on login
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void signupUser() {
        String name     = nameEt.getText().toString().trim();
        String phone    = phoneEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Name, phone & password required", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.21.130.172/findmydorm/signup.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        if (res.getBoolean("success")) {
                            Toast.makeText(this,
                                    "Signup successful",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this,
                                    res.optString("error", "Signup failed"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this,
                                "Response parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this,
                        "Network error: " + error.getMessage(),
                        Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name",     name);
                params.put("phone",    phone);
                params.put("password", password);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
