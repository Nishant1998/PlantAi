package com.example.plantai;

import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class RequestApi {

    public String result;


    public void doRequest()
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Log.d("req" , "Resquesting ...");
        //MediaType mediaType = MediaType.parse("text/plain");

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("photo",Environment.getExternalStorageDirectory()
                                +"/Android/PlantAi/crop.jpg",
                        RequestBody.create(MediaType.parse("image/jpg"),
                                new File(Environment.getExternalStorageDirectory()
                                        +"/Android/PlantAi/crop.jpg")))
                .build();
        Log.d("req" , "Body created");


        Request request = new Request.Builder()
                .url("http://35.226.157.128:80/PlantAi/upload-manager.php")
                .method("POST", body)
                .addHeader("Content-Transfer-Encoding", "multipart/form-data")
                .build();
        Log.d("req" , "Request Build.");


        try {
            Log.d("req" , "Sending request");

            Response response = client.newCall(request).execute();
            System.out.println("Response ====>  " + response.body().string());
            result = response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult()
    {
        return result;
    }

}

