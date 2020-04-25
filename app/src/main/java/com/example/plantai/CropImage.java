package com.example.plantai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CropImage extends AppCompatActivity {
    private static final String TAG = "CropImage";

    public ProgressBar progressBar;
    ImageView imageView;
    Button scanButton;
    String jsonData;
    boolean flag = false;
    int[] max = new int[3];
    Double[] probability = new Double[38];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);


        imageView = findViewById(R.id.cropedImageView);
        scanButton = findViewById(R.id.scanBtn);
        progressBar = findViewById(R.id.progressBar);


        /** Reading click image which is saved as : '/Android/PlantAi/test.jpg' **/
        File imgFile = new File(Environment.getExternalStorageDirectory()
                + "/Android/PlantAi/test.jpg");

        // If image is present start Ucrop
        if (imgFile.exists()) {
            Uri imageUri = Uri.fromFile(imgFile);
            if (imageUri != null) {
                startCrop(imageUri);
            } else
                Log.d(TAG, "onCreate: ImageUri is null");
        }

    }


    private void startCrop(Uri uri) {
        /** init UCrop to our click image and save it as crop.jpg **/

        UCrop ucrop = UCrop.of(uri, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                + "/Android/PlantAi/crop.jpg")));

        ucrop.withAspectRatio(1, 1);
        ucrop.withOptions(getCropOption());
        ucrop.start(CropImage.this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            if (uri != null) {
                Bitmap bitmap;
                //TODO RESIZE img and display
                //imageView.setImageURI(uri);
                try {
                    Log.d(TAG, "onActivityResult: Reading Image : from UCrop (bitmap)");
                    bitmap = MediaStore.Images.Media
                            .getBitmap(this.getContentResolver(), uri);
                    Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 256,
                            256, true);
                    imageView.setImageBitmap(newBitmap);

                    //Save bitmap image
                    {
                        Log.d(TAG, "onActivityResult: Saving image : in PlantAi as crop.jpg");
                        File dir = new File(Environment
                                .getExternalStorageDirectory() + "/Android/PlantAi");

                        String filename = "crop.jpg";
                        File file = new File(dir, filename);

                        OutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(file);
                            if (outputStream != null);

                        } catch (Exception e) {
                            Log.d(TAG, "onActivityResult: outputstream is NULL");
                            e.printStackTrace();
                        }
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        Log.d(TAG, "onActivityResult: Image saved at : " + dir.getPath() + filename);
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    callApi();


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private void callApi() {
        boolean isCon = isConnected();
        Log.d("CONNECTION", "" + isCon);
        if (isCon) {
            flag = true;

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Log.d("req", "Resquesting ...");

            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("photo", Environment.getExternalStorageDirectory()
                                    + "/Android/PlantAi/crop.jpg",
                            RequestBody.create(MediaType.parse("image/jpg"),
                                    new File(Environment.getExternalStorageDirectory()
                                            + "/Android/PlantAi/crop.jpg")))
                    .build();
            Log.d("req", "Body created");


            Request request = new Request.Builder()
                    .url("http://35.226.157.128:80/PlantAi/upload-manager.php")
                    .method("POST", body)
                    .addHeader("Content-Transfer-Encoding", "multipart/form-data")
                    .build();
            Log.d("req", "Request Build.");

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    CropImage.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CropImage.this,
                                    "No response from server.", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {

                        jsonData = response.body().string();

                        if (response.isSuccessful()) {
                            Log.d("REQUEST", "REQUEST SUCCESSFUL");
                            Log.d("REQUEST", "RESPONCE ====> " + jsonData);


                        } else {
                            Log.d("REQUEST", "REQUEST FAIL");
                        }
                    } catch (IOException e) {
                        Log.v("ERROR", "Exception caught : ", e);
                    }
                }
            });
        } else {
            Log.e("ERROR", "NO INTERNET");
            Toast toast = Toast.makeText(this, "No Internet !!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /** UCrop options **/
    private UCrop.Options getCropOption() {

        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(100);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);

        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        options.setCropFrameColor(getResources().getColor(R.color.colorPrimary));


        options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
        options.setCropGridColor(getResources().getColor(R.color.colorPrimary));

        options.setFreeStyleCropEnabled(false);
        return options;
    }


    /** Menu Bar **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.op_profile:
                intent = new Intent(this, profile.class);
                break;
            case R.id.op_reports:
                intent = new Intent(this, Reports.class);
                break;
            case R.id.op_setting:
                intent = new Intent(this, Settings.class);
                break;
            case R.id.op_about:
                intent = new Intent(this, About.class);
                break;

        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    //INternet and request part

    public void scanClick(View view) throws InterruptedException {


        progressBar.setVisibility(View.VISIBLE);

        if (!isConnected()) {
            Log.e(TAG, "scanClick:  No Internet");
            Toast toast = Toast.makeText(this, "No Internet !!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            int count = 10;
            System.out.println("set visible ");

            while (jsonData == null) {
                Log.d("wait", "wait");
                System.out.println("COUNT ====>" + count);
                if (count == 0) {
                    System.out.println("COUNT ====> STARTING TOAST" + count);
                    Toast toast = Toast.makeText(this, "Server timeout.", Toast.LENGTH_SHORT);
                    toast.show();
                    progressBar.setVisibility(View.INVISIBLE);
                    count = 99;
                    break;
                } else {
                    Thread.sleep(1000);
                    count--;
                }
            }
            if (count != 99) {
                Intent intent = new Intent(this, Result.class);
                intent.putExtra("jsonresult", jsonData);
                startActivity(intent);
            }
        }

    }

    /** Check internet connection **/
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        System.out.println("Connected =====>" + isConnected);
        return isConnected;
    }
}
