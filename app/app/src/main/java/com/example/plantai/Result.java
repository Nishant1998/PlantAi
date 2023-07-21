package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class Result extends AppCompatActivity implements View.OnClickListener, DiseaseCardsAdapter.DiseaseCardOnClickListener {
    private static final String TAG = "ResultActivity";

    private String selected_lang;
    private int[] max = new int[3];
    private JSONArray jprob, array;
    private ImageButton ib;
    private ImageView uiv, loadingGif;
    private TextView resultTextView;
    private RecyclerView recyclerView;
    private ArrayList<DiseaseCard> diseaseCardList;
    private String[] diseaseName = new String[39];

    private Interpreter preinterperter, interperter;

    private int[] diseaseImages = {
            R.drawable.dp00_0, R.drawable.dp01_0, R.drawable.dp02_0, R.drawable.dp03_0,
            R.drawable.dp04_0, R.drawable.dp05_0, R.drawable.dp06_0, R.drawable.dp07_0,
            R.drawable.dp08_0, R.drawable.dp09_0, R.drawable.dp10_0, R.drawable.dp11_0,
            R.drawable.dp12_0, R.drawable.dp13_0, R.drawable.dp14_0, R.drawable.dp15_0,
            R.drawable.dp16_0, R.drawable.dp17_0, R.drawable.dp18_0, R.drawable.dp19_0,
            R.drawable.dp20_0, R.drawable.dp21_0, R.drawable.dp22_0, R.drawable.dp23_0,
            R.drawable.dp24_0, R.drawable.dp25_0, R.drawable.dp26_0, R.drawable.dp27_0,
            R.drawable.dp28_0, R.drawable.dp29_0, R.drawable.dp30_0, R.drawable.dp31_0,
            R.drawable.dp32_0, R.drawable.dp33_0, R.drawable.dp34_0, R.drawable.dp35_0,
            R.drawable.dp36_0, R.drawable.dp37_0, R.drawable.dp38_0
    };

    // === Prediction gating ===
    private static final int BACKGROUND_INDEX = 4;   // your background class
    private static final float TAU_ITEM = 0.25f;     // per-item display threshold (prob >= 0.25)



    DatabaseHelper myDB;

    private void __init__() {
        uiv = findViewById(R.id.userImageView);
        ib = findViewById(R.id.imageButton);
        loadingGif = findViewById(R.id.loadingGif);
        resultTextView = findViewById(R.id.mostsimlarTextView);
        recyclerView = findViewById(R.id.recyclerViewDisease);
        myDB = new DatabaseHelper(this);




        // Load GIF Animation
        Glide.with(this).asGif().load(R.drawable.scanning_loader).into(loadingGif);
        Log.d(TAG, "Initialization complete.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.d(TAG, "Activity started.");

        __init__();
        selected_lang = load_setting();
        setLang();

        // Get image path from intent
        String imagePath = getIntent().getStringExtra("image_path");
        if (imagePath != null) {
            Log.d(TAG, "Image path received: " + imagePath);
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                System.out.println("XYZ size=" + bm.getWidth() + "x" + bm.getHeight() + " config=" + bm.getConfig());
                int[] px = new int[bm.getWidth() * bm.getHeight()];
                bm.getPixels(px, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
                for (int i = 0; i < Math.min(10, px.length); i++) System.out.println("XYZ pixel[" + i + "]=" + Integer.toHexString(px[i]));


                uiv.setImageBitmap(bitmap);
                Log.d(TAG, "Image successfully loaded into ImageView.");

                AssetManager assetManager = this.getAssets();
                try {
//                    preinterperter = new Interpreter(loadModelFile(assetManager, "model_imagenet.tflite"));
                    interperter = new Interpreter(loadModelFile(assetManager, "model.tflite"));
                    int[] ishape = interperter.getInputTensor(0).shape();
                    Log.d(TAG, "Model Input shape: " + Arrays.toString(ishape));  // e.g. [1, 224, 224, 3] or [1, 3, 224, 224]
                    Log.d(TAG, "Model Input dtype: " + interperter.getInputTensor(0).dataType());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                runModelInBackground(bitmap, new ModelCallback() {
                    @Override
                    public void onResult(JSONObject resultJson) throws JSONException {
                        // Use the resultJson object in UI
                        System.out.println("Received JSON in onCreate: " + resultJson.toString());
                        try {
                            // 1) Apply policy: handle background-at-top, remove background in top-3, apply thresholds
                            JSONObject displayJson = applyDisplayPolicy(resultJson);

                            if (displayJson == null) {
                                // Top-1 was background -> show toast, hide list, do NOT save
                                Toast.makeText(Result.this, R.string.msg_no_leaf_found, Toast.LENGTH_LONG).show();
                                Result.this.resultTextView.setText(R.string.msg_no_leaf_found);
                                Result.this.loadingGif.setVisibility(View.INVISIBLE);
                                Result.this.recyclerView.setVisibility(View.GONE);
                                return;
                            }

                            JSONArray filtered = displayJson.getJSONArray("predictions");
                            if (filtered.length() == 0) {
                                // Nothing survived thresholds -> show message, hide list, do NOT save
                                Toast.makeText(Result.this, R.string.msg_low_confidence, Toast.LENGTH_LONG).show();
                                Result.this.resultTextView.setText(R.string.msg_low_confidence);
                                Result.this.loadingGif.setVisibility(View.INVISIBLE);
                                Result.this.recyclerView.setVisibility(View.GONE);
                                return;
                            }

                            // 2) Render cards from filtered predictions (1..3)
                            genrateDiseaseData(displayJson);
                            diseaseRecyclerViewConfig();

                            // 3) Save filtered results to history
                            addData(displayJson, bitmap);

                            Result.this.resultTextView.setText(getString(R.string.top_pred));
                            Result.this.loadingGif.setVisibility(View.INVISIBLE);
                            Result.this.recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

            } else {
                Log.e(TAG, "Image file does not exist.");
            }
        } else {
            Log.e(TAG, "No image path received.");
        }
    }

    private JSONObject applyDisplayPolicy(JSONObject resultJson) throws JSONException {
        JSONArray preds = resultJson.getJSONArray("predictions"); // already sorted desc in your code

        // Case: background at top -> block (no display, no save)
        JSONObject top = preds.getJSONObject(0);
        if (top.getInt("index") == BACKGROUND_INDEX) {
            return null;
        }

        // Filter: remove background anywhere + apply per-item threshold; keep up to 3
        JSONArray kept = new JSONArray();
        for (int i = 0; i < preds.length() && kept.length() < 3; i++) {
            JSONObject e = preds.getJSONObject(i);
            int idx = e.getInt("index");
            double p = e.getDouble("prob");
            if (idx == BACKGROUND_INDEX) continue;      // drop background in top-2/3
            if (p < TAU_ITEM) continue;                 // thresholding
            kept.put(e);
        }

        JSONObject out = new JSONObject();
        out.put("predictions", kept);
        return out;
    }

    public MappedByteBuffer loadModelFile(AssetManager assetManager, String file_name) throws IOException {
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(file_name);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length);
    }

    private void runModelInBackground(final Bitmap bitmap, final ModelCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ByteBuffer inputBuffer = preprocessBitmap(bitmap); // Convert image to tensor
//                float[][] image_net_output = new float[1][1000];  // Model output buffer
                float[][] output = new float[1][39];  // Model output buffer
//                preinterperter.run(inputBuffer, image_net_output); // Run inference
                interperter.run(inputBuffer, output); // Run inference

                // Print result shape
                System.out.println("Model output shape: " + output.length + " x " + output[0].length);
                System.out.println("OUTPUT" + output);
                JSONObject json_output;
                try {
                    json_output = postProcessOutput(output);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("JOUTPUT" + json_output);
                // Switch back to UI thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callback.onResult(json_output);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }

    // Callback interface to return JSON to the main thread
    interface ModelCallback {
        void onResult(JSONObject resultJson) throws JSONException;
    }

    private ByteBuffer preprocessBitmap(Bitmap bitmap) {
        // Expecting [1,3,224,224] NCHW, FLOAT32
        int[] ishape = interperter.getInputTensor(0).shape();   // [1,3,224,224]
        int C = ishape[1], H = ishape[2], W = ishape[3];
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, W, H, true);

        int[] px = new int[W * H];
        resized.getPixels(px, 0, W, 0, 0, W, H);

        final float[] MEAN = {0.485f, 0.456f, 0.406f};
        final float[] STD  = {0.229f, 0.224f, 0.225f};

        ByteBuffer buf = ByteBuffer.allocateDirect(4 * C * H * W).order(ByteOrder.nativeOrder());

        // R plane
        for (int y = 0; y < H; y++) {
            int row = y * W;
            for (int x = 0; x < W; x++) {
                int c = px[row + x];
                float r = ((c >> 16) & 0xFF) / 255.0f;
                buf.putFloat((r - MEAN[0]) / STD[0]);
            }
        }
        // G plane
        for (int y = 0; y < H; y++) {
            int row = y * W;
            for (int x = 0; x < W; x++) {
                int c = px[row + x];
                float g = ((c >> 8) & 0xFF) / 255.0f;
                buf.putFloat((g - MEAN[1]) / STD[1]);
            }
        }
        // B plane
        for (int y = 0; y < H; y++) {
            int row = y * W;
            for (int x = 0; x < W; x++) {
                int c = px[row + x];
                float b = (c & 0xFF) / 255.0f;
                buf.putFloat((b - MEAN[2]) / STD[2]);
            }
        }

        buf.rewind();
        return buf;
    }

    private JSONObject postProcessOutput(float[][] output) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        // Extract raw model outputs (logits or probabilities depending on the model)
        float[] logits = output[0];

        // ---------- Step 1: Apply Softmax ----------
        // To convert raw logits to probabilities [0,1] that sum to 1
        float maxLogit = Float.NEGATIVE_INFINITY;
        for (float val : logits) {
            if (val > maxLogit) maxLogit = val;   // For numerical stability
        }

        float sumExp = 0f;
        float[] probabilities = new float[logits.length];
        for (int i = 0; i < logits.length; i++) {
            probabilities[i] = (float) Math.exp(logits[i] - maxLogit);
            sumExp += probabilities[i];
        }
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] /= sumExp;  // Normalize
        }
        // ---------- End Softmax ----------

        // ---------- Step 2: Sort indices by probability ----------
        Integer[] indices = new Integer[probabilities.length];
        for (int i = 0; i < probabilities.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return Float.compare(probabilities[b], probabilities[a]); // Descending order
            }
        });
        // ---------- End Sorting ----------

        // ---------- Step 3: Build JSON with sorted predictions ----------
        for (int i = 0; i < probabilities.length; i++) {
            JSONObject entry = new JSONObject();
            entry.put("index", indices[i]);                  // class index
            entry.put("prob", probabilities[indices[i]]);    // softmax probability
            jsonArray.put(entry);
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("predictions", jsonArray);

        return resultJson;
    }



    private void genrateDiseaseData(JSONObject resultJson) throws JSONException {
        System.out.println("Received JSON: " + resultJson.toString());
        JSONArray predictionsArray = resultJson.getJSONArray("predictions");
        diseaseCardList = new ArrayList<>();

        int limit = Math.min(3, predictionsArray.length());
        for (int i = 0; i < limit; i++) {
            JSONObject entry = predictionsArray.getJSONObject(i);
            int index = entry.getInt("index");
            double prob = entry.getDouble("prob");
            String probabilityText = String.format("%.2f%%", prob * 100);

            diseaseCardList.add(new DiseaseCard(diseaseImages[index], diseaseName[index], probabilityText));
            max[i] = index;  // keep for AboutDisease click-through
        }
        // optional: clear unused max slots
        for (int i = limit; i < 3; i++) max[i] = -1;
    }

    private void diseaseRecyclerViewConfig() {
        //config
        recyclerView = findViewById(R.id.recyclerViewDisease);

        //performance
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DiseaseCardsAdapter adapter = new DiseaseCardsAdapter(diseaseCardList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void addData(JSONObject resultJson, Bitmap bitmap) throws JSONException {
        JSONArray predictionsArray = resultJson.getJSONArray("predictions");
        int n = Math.min(3, predictionsArray.length());

        // If nothing to save (policy already showed message)
        if (n == 0) return;

        // Collect up to 3 indices & probs, pad rest
        int[] idx = new int[] { -1, -1, -1 };
        int[] pp  = new int[] {  0,  0,  0 };

        for (int i = 0; i < n; i++) {
            JSONObject e = predictionsArray.getJSONObject(i);
            idx[i] = e.getInt("index");
            pp[i]  = (int) Math.round(e.getDouble("prob") * 100);
        }

        Date d = Calendar.getInstance().getTime();
        String date = DateFormat.getDateInstance().format(d);
        String time = DateFormat.getTimeInstance().format(d);

        String imagePath = saveImage(bitmap);
        if (imagePath != null) {
            boolean isInserted = myDB.insertData(
                    date, time,
                    idx[0], idx[1], idx[2],
                    pp[0], pp[1], pp[2],
                    imagePath,
                    resultJson.toString() // store filtered JSON you actually displayed
            );
            if (isInserted) {
                Log.i(TAG, "Data inserted successfully.");
            } else {
                Log.e(TAG, "Failed to insert data into database.");
            }
        } else {
            Log.e(TAG, "Image save failed, skipping database insert.");
        }
    }


    /** Saves Bitmap as a JPEG image and returns the file path */
    private String saveImage(Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "plant_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/PlantAi");

        ContentResolver resolver = getContentResolver();
        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (imageUri != null) {
            try (OutputStream out = resolver.openOutputStream(imageUri)) {
                assert out != null;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                Log.i(TAG, "Image saved successfully: " + imageUri.toString());
                return imageUri.toString();  // ✅ Return the saved image URI
            } catch (IOException e) {
                Log.e(TAG, "Failed to save image", e);
            }
        } else {
            Log.e(TAG, "Failed to create image file in MediaStore");
        }
        return null;
    }



    String load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences("Settings", MODE_PRIVATE);
        return mySharedPreference.getString("app_lang", "en");
    }

    private void setLang() {
        diseaseName[0] = getString(R.string.dname_01);
        diseaseName[1] = getString(R.string.dname_02);
        diseaseName[2] = getString(R.string.dname_03);
        diseaseName[3] = getString(R.string.dname_04);
        diseaseName[4] = getString(R.string.dname_05);
        diseaseName[5] = getString(R.string.dname_06);
        diseaseName[6] = getString(R.string.dname_07);
        diseaseName[7] = getString(R.string.dname_08);
        diseaseName[8] = getString(R.string.dname_09);
        diseaseName[9] = getString(R.string.dname_10);
        diseaseName[10] = getString(R.string.dname_11);
        diseaseName[11] = getString(R.string.dname_12);
        diseaseName[12] = getString(R.string.dname_13);
        diseaseName[13] = getString(R.string.dname_14);
        diseaseName[14] = getString(R.string.dname_15);
        diseaseName[15] = getString(R.string.dname_16);
        diseaseName[16] = getString(R.string.dname_17);
        diseaseName[17] = getString(R.string.dname_18);
        diseaseName[18] = getString(R.string.dname_19);
        diseaseName[19] = getString(R.string.dname_20);
        diseaseName[20] = getString(R.string.dname_21);
        diseaseName[21] = getString(R.string.dname_22);
        diseaseName[22] = getString(R.string.dname_23);
        diseaseName[23] = getString(R.string.dname_24);
        diseaseName[24] = getString(R.string.dname_25);
        diseaseName[25] = getString(R.string.dname_26);
        diseaseName[26] = getString(R.string.dname_27);
        diseaseName[27] = getString(R.string.dname_28);
        diseaseName[28] = getString(R.string.dname_29);
        diseaseName[29] = getString(R.string.dname_30);
        diseaseName[30] = getString(R.string.dname_31);
        diseaseName[31] = getString(R.string.dname_32);
        diseaseName[32] = getString(R.string.dname_33);
        diseaseName[33] = getString(R.string.dname_34);
        diseaseName[34] = getString(R.string.dname_35);
        diseaseName[35] = getString(R.string.dname_36);
        diseaseName[36] = getString(R.string.dname_37);
        diseaseName[37] = getString(R.string.dname_38);
        diseaseName[38] = getString(R.string.dname_39);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.imageButton) {
            intent = new Intent(Result.this, Home.class);
        } else if (v.getId() == R.id.userImageView) {
            intent = new Intent(Result.this, ImageSlider.class);
            intent.putExtra("id", 999);
            intent.putExtra("image_path", getIntent().getStringExtra("image_path"));
        } else {
            return;
        }
        startActivity(intent);
    }

    @Override
    public void OnDiseaseCardClick(int position) {
        Intent intent = new Intent(Result.this, AboutDisease.class);
        intent.putExtra("did", max[position] + 1);
        startActivity(intent);
    }
}
