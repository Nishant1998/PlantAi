package com.example.plantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;


public class PlantD extends AppCompatActivity {

    private static final String TAG = "PlantD";
    
    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    String selected_lang;
    int plantNumber;
    ImageView imageView;
    TextView headText,infoText;
    int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10;
    private static final String KEY_IMAGE_URI = "key_image_uri";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_d);
        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI);
        }
    }

    /** onStart get context and set content **/
    @Override
    protected void onStart() {
        super.onStart();
        imageView = findViewById(R.id.plantDicPic);
        headText = findViewById(R.id.plantDicName);
        infoText = findViewById(R.id.plantDicContent);

        plantNumber = getIntent().getIntExtra("plant_num",0);
        //get setting and change language
        selected_lang = load_setting();
        setLang();

    }


    /** OnClick Camera Button - start camera intent **/
// Constants
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_IMAGE = 2;

    private Uri imageUri;

    /** OnClick Camera Button - show options for Camera or Gallery **/
    public void startCamera(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source")
                .setItems(new String[]{"Camera", "Gallery"}, (dialog, which) -> {
                    if (which == 0) {
                        openCamera();
                    } else {
                        openGallery();
                    }
                })
                .show();
    }

    /** Open Camera **/
    private void openCamera() {
        Log.d(TAG, "openCamera: Starting camera intent.");

        // 1) Runtime CAMERA permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE // reuse your existing request code
            );
            Toast.makeText(this, "Grant Camera permission to take a photo.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2) Prepare output file in .../files/Pictures/
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir == null) {
            Toast.makeText(this, "Storage not available.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!storageDir.exists() && !storageDir.mkdirs()) {
            Toast.makeText(this, "Failed to create image directory.", Toast.LENGTH_SHORT).show();
            return;
        }
        File photoFile = new File(storageDir, "captured_image.jpg");

        // 3) Build content:// URI through FileProvider
        imageUri = FileProvider.getUriForFile(this, "com.example.plantai.provider", photoFile);
        Log.d(TAG, "openCamera: Image URI = " + imageUri);

        // 4) Launch camera intent with URI permissions
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // Grant to all camera activities (API 33+ uses ResolveInfoFlags)
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resInfoList;
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            resInfoList = pm.queryIntentActivities(
                    intent,
                    PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY)
            );
        } else {
            //noinspection deprecation
            resInfoList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        }
        for (ResolveInfo ri : resInfoList) {
            grantUriPermission(
                    ri.activityInfo.packageName,
                    imageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            );
        }

        try {
            //noinspection deprecation
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No camera app found.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "openCamera: No camera app", e);
        } catch (SecurityException se) {
            Toast.makeText(this, "Camera permission denied by system.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "openCamera: SecurityException", se);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (imageUri != null) outState.putParcelable(KEY_IMAGE_URI, imageUri);
    }



    /** Open Gallery **/
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE);
    }

    /** Handle Camera or Gallery Result **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Try to get a Uri from the result; many camera apps return null when using EXTRA_OUTPUT
                Uri uri = (data != null ? data.getData() : null);
                if (uri == null) uri = imageUri; // fallback to the Uri you created before launching camera

                // As a last resort, rebuild a Uri to the file we saved to
                if (uri == null) {
                    File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_image.jpg");
                    if (imageFile.exists()) {
                        uri = FileProvider.getUriForFile(this, "com.example.plantai.provider", imageFile);
                    }
                }

                if (uri == null) {
                    Log.e(TAG, "onActivityResult: Image URI is null after capture");
                    Toast.makeText(this, "Couldn’t get image from camera.", Toast.LENGTH_LONG).show();
                    return;
                }

                imageUri = uri; // keep it for later use
                Log.d(TAG, "onActivityResult: Captured image at " + uri);

                // Optionally verify the expected file exists, but proceed with the Uri regardless
                File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_image.jpg");
                if (!imageFile.exists()) {
                    Log.w(TAG, "onActivityResult: expected captured_image.jpg not found; proceeding with URI only");
                }

                cropImage(uri); // Proceed to crop
            }
            else if (requestCode == REQUEST_GALLERY_IMAGE) {
                if (data != null && data.getData() != null) {
                    imageUri = data.getData();
                    Log.d(TAG, "onActivityResult: Selected image from gallery = " + imageUri);
                    cropImage(imageUri); // Crop after selecting from the gallery
                } else {
                    Log.e(TAG, "onActivityResult: Gallery returned no data");
                    Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Log.d(TAG, "onActivityResult: Action Cancelled");
            Toast.makeText(this, "Action Cancelled", Toast.LENGTH_SHORT).show();
        }

    }



    /** Crop Image to Square **/
    private void cropImage(Uri uri) {
        Log.d(TAG, "cropImage: Started with URI: " + uri.toString());

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            Log.d(TAG, "cropImage: Original Image Size: " + bitmap.getWidth() + "x" + bitmap.getHeight());

            // Crop to square (smallest side)
            int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
            Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, size, size);
            Log.d(TAG, "cropImage: Cropped Image Size: " + croppedBitmap.getWidth() + "x" + croppedBitmap.getHeight());

            // Resize to 244x244
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(croppedBitmap, 224, 224, true);
            Log.d(TAG, "cropImage: Resized Image Size: " + resizedBitmap.getWidth() + "x" + resizedBitmap.getHeight());

            // Save the cropped & resized image
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "cropped_image.jpg");
            FileOutputStream out = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.d(TAG, "cropImage: Cropped image saved successfully at " + file.getAbsolutePath());

            // 🔥 ✅ Move to ResultsActivity after cropping
            Intent intent = new Intent(this, Result.class);
            intent.putExtra("image_path", file.getAbsolutePath());  // Send image path
            startActivity(intent);

        } catch (IOException e) {
            Log.e(TAG, "cropImage: Error processing image", e);
        }
    }






    /**  MENU BAR   **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=null;
        if (item.getItemId() == R.id.op_all_diseases) {
            intent = new Intent(this, AllDiseases.class);
        } else if (item.getItemId() == R.id.op_reports) {
            intent = new Intent(this, Reports.class);
        } else if (item.getItemId() == R.id.op_setting) {
            intent = new Intent(this, Settings.class);
        } else if (item.getItemId() == R.id.op_about) {
            intent = new Intent(this, About.class);
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    
    /**  LOADING SETTINGS & SET LANGUAGE **/
    void setLang() {
        int[] imageResources = {
                R.drawable.apple, R.drawable.blueberry, R.drawable.cherry, R.drawable.corn,
                R.drawable.grapes, R.drawable.orange, R.drawable.peach, R.drawable.pepper,
                R.drawable.potato, R.drawable.raspberry, R.drawable.soybean, R.drawable.strawberry,
                R.drawable.tomato
        };

        int[] nameResources = {
                R.string.apple_name, R.string.blueberry_name, R.string.cherry_name, R.string.corn_name,
                R.string.grapes_name, R.string.orange_name, R.string.peach_name, R.string.pepper_bell_name,
                R.string.potato_name, R.string.raspberry_name, R.string.soybean_name, R.string.strawberry_name,
                R.string.tomato_name
        };

        int[] infoResources = {
                R.string.apple_info, R.string.blueberry_info, R.string.cherry_info, R.string.corn_info,
                R.string.grapes_info, R.string.orange_info, R.string.peach_info, R.string.pepper_bell_info,
                R.string.potato_info, R.string.raspberry_info, R.string.soybean_info, R.string.strawberry_info,
                R.string.tomato_info
        };

        if (plantNumber >= 1 && plantNumber <= imageResources.length) {
            imageView.setImageResource(imageResources[plantNumber - 1]);
            headText.setText(getString(nameResources[plantNumber - 1]));
            infoText.setText(Html.fromHtml(getString(infoResources[plantNumber - 1]), Html.FROM_HTML_MODE_LEGACY));
        }
    }


    String load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        return mySharedPreference.getString(SHARED_APP_LANG,"en");
    }

}
