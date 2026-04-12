package com.example.findmydorm;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddDorm extends AppCompatActivity {
    private static final int PICK_IMAGE_REQ      = 1001;
    private static final int CAMERA_IMAGE_REQ    = 1002;
    private static final int CAMERA_PERMISSION   = 2001;

    private EditText    etDormName, etPrice, etLocation, etShortDesc;
    private Button      btnChooseImage, btnTakePhoto, btnSubmit;
    private ImageView   ivPreview;
    private ProgressBar loader;
    private ScrollView  formContainer;
    private Uri         selectedImageUri;
    private SharedPreferences prefs;
    private Handler     uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dorm);

        prefs         = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        uiHandler     = new Handler(Looper.getMainLooper());
        formContainer = findViewById(R.id.scrollForm);
        loader        = findViewById(R.id.progressLoader);
        etDormName    = findViewById(R.id.etDormName);
        etPrice       = findViewById(R.id.etPrice);
        etLocation    = findViewById(R.id.etLocation);
        etShortDesc   = findViewById(R.id.etShortDesc);
        btnChooseImage= findViewById(R.id.btnChooseImage);
        btnTakePhoto  = findViewById(R.id.btnTakePhoto);
        ivPreview     = findViewById(R.id.ivPreview);
        btnSubmit     = findViewById(R.id.btnSubmit);

        // 1) Gallery pick
        btnChooseImage.setOnClickListener(v -> {
            Intent pick = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            if (pick.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(pick, PICK_IMAGE_REQ);
            }
        });

        // 2) Camera capture (only CAMERA permission now)
        btnTakePhoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{ Manifest.permission.CAMERA },
                        CAMERA_PERMISSION);

            } else {
                launchCamera();
            }
        });

        // 3) Submit (with 3s loader)
        btnSubmit.setOnClickListener(v -> {
            formContainer.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
            uiHandler.postDelayed(this::submitDorm, 3000);
        });
    }

    private void launchCamera() {
        Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cam.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cam, CAMERA_IMAGE_REQ);
        } else {
            Toast.makeText(this,
                    "No camera app available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this,
                        "Camera permission is required to take photos",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) return;

        if (requestCode == PICK_IMAGE_REQ) {
            // User picked from gallery
            selectedImageUri = data.getData();
            ivPreview.setImageURI(selectedImageUri);

        } else if (requestCode == CAMERA_IMAGE_REQ) {
            // User snapped a photo (thumbnail)
            Bitmap bmp = data.getParcelableExtra("data");
            if (bmp != null) {
                // Convert bitmap to Uri for upload
                selectedImageUri = Uri.parse(
                        MediaStore.Images.Media.insertImage(
                                getContentResolver(), bmp, "DormPic", null
                        )
                );
                ivPreview.setImageURI(selectedImageUri);
            } else {
                Toast.makeText(this,
                        "Camera returned no image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitDorm() {
        String dormName  = etDormName.getText().toString().trim();
        String price     = etPrice.getText().toString().trim();
        String location  = etLocation.getText().toString().trim();
        String desc      = etShortDesc.getText().toString().trim();
        String userPhone = prefs.getString("userPhone", "");

        if (dormName.isEmpty() || price.isEmpty() ||
                location.isEmpty() || desc.isEmpty() ||
                selectedImageUri == null)
        {
            Toast.makeText(this,
                    "All fields + a photo are required",
                    Toast.LENGTH_SHORT).show();
            loader.setVisibility(View.GONE);
            formContainer.setVisibility(View.VISIBLE);
            return;
        }

        // Create dorm entry on server
        String url = "http://10.21.130.172/findmydorm/add_dorm.php";
        StringRequest addReq = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        if (res.getBoolean("success")) {
                            int dormId = res.getInt("dorm_id");
                            uploadImage(dormId);
                        } else {
                            Toast.makeText(this,
                                    res.optString("error","Add dorm failed"),
                                    Toast.LENGTH_SHORT).show();
                            loader.setVisibility(View.GONE);
                            formContainer.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this,
                            "Network error: "+error.getMessage(),
                            Toast.LENGTH_LONG).show();
                    loader.setVisibility(View.GONE);
                    formContainer.setVisibility(View.VISIBLE);
                }
        ) {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> p = new HashMap<>();
                p.put("user_phone",        userPhone);
                p.put("dorm_name",         dormName);
                p.put("price",             price);
                p.put("short_description", desc);
                p.put("location",          location);
                return p;
            }
        };
        Volley.newRequestQueue(this).add(addReq);
    }

    private void uploadImage(int dormId) {
        byte[] imageData;
        try (InputStream is = getContentResolver()
                .openInputStream(selectedImageUri)) {
            imageData = toBytes(is);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Image read failed", Toast.LENGTH_SHORT).show();
            loader.setVisibility(View.GONE);
            formContainer.setVisibility(View.VISIBLE);
            return;
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(
                imageData, MediaType.parse("image/*")
        );
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dorm_id", String.valueOf(dormId))
                .addFormDataPart("images[]","upload.jpg", fileBody)
                .build();

        okhttp3.Request req = new okhttp3.Request.Builder()
                .url("http://10.21.130.172/findmydorm/upload_dorm_images.php")
                .post(body)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override public void onFailure(Call c, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(AddDorm.this,
                            "Image upload failed", Toast.LENGTH_SHORT).show();
                    loader.setVisibility(View.GONE);
                    formContainer.setVisibility(View.VISIBLE);
                });
            }
            @Override public void onResponse(Call c, Response resp) {
                runOnUiThread(() -> {
                    Toast.makeText(AddDorm.this,
                            "Dorm added successfully!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }

    private byte[] toBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) bos.write(buf, 0, len);
        return bos.toByteArray();
    }
}
