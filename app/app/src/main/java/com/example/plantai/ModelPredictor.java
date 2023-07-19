//package com.example.plantai;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.util.Log;
//
//import org.tensorflow.lite.DataType;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//
//public class ModelPredictor {
//    private static final String TAG = "ModelPredictor";
//    private static final int INPUT_SIZE = 224;  // Model input size
//    private static final int NUM_CLASSES = 38;  // Model output classes
//    private static final int TOP_K = 3;  // Get top 3 predictions
//
//    private Model model;  // Auto-generated model class
//
//    /** Constructor: Loads the TFLite model */
//    public ModelPredictor(Context context) throws IOException {
//        try {
//            model = Model.newInstance(context); // Load model
//            Log.d(TAG, "Model loaded successfully.");
//        } catch (Exception e) {
//            Log.e(TAG, "Error loading model: " + e.getMessage(), e);
//            throw new IOException("Failed to load model.tflite", e);
//        }
//    }
//
//    /** Preprocess Bitmap & Convert to TensorBuffer */
//    private TensorBuffer convertBitmapToTensorBuffer(Bitmap bitmap) {
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
//        TensorBuffer inputBuffer = TensorBuffer.createFixedSize(new int[]{1, INPUT_SIZE, INPUT_SIZE, 3}, DataType.FLOAT32);
//
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * 3);
//        byteBuffer.order(ByteOrder.nativeOrder());
//
//        int[] intValues = new int[INPUT_SIZE * INPUT_SIZE];
//        resizedBitmap.getPixels(intValues, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE);
//
//        for (int pixelValue : intValues) {
//            byteBuffer.putFloat(((pixelValue >> 16) & 0xFF) / 255.0f);
//            byteBuffer.putFloat(((pixelValue >> 8) & 0xFF) / 255.0f);
//            byteBuffer.putFloat((pixelValue & 0xFF) / 255.0f);
//        }
//
//        inputBuffer.loadBuffer(byteBuffer);
//        return inputBuffer;
//    }
//
//    /** Perform inference and return top predictions */
//    public Prediction[] predict(Bitmap bitmap) {
//        Log.d(TAG, "Starting prediction...");
//        try {
//            // Convert input
//            TensorBuffer inputFeature = convertBitmapToTensorBuffer(bitmap);
//
//            // Run inference
//            Model.Outputs outputs = model.process(inputFeature);
//            TensorBuffer outputFeature = outputs.getOutputFeature0AsTensorBuffer();
//
//            Log.d(TAG, "Prediction completed successfully.");
//            return getTopPredictions(outputFeature.getFloatArray());
//        } catch (Exception e) {
//            Log.e(TAG, "Error during inference: " + e.getMessage(), e);
//            return null;
//        }
//    }
//
//    /** Get Top K Predictions */
//    private Prediction[] getTopPredictions(float[] output) {
//        PriorityQueue<Prediction> pq = new PriorityQueue<>((a, b) -> Float.compare(b.confidence, a.confidence));
//
//        for (int i = 0; i < output.length; i++) {
//            pq.add(new Prediction(i, output[i]));
//        }
//
//        Prediction[] topResults = new Prediction[TOP_K];
//        for (int i = 0; i < TOP_K; i++) {
//            topResults[i] = pq.poll();
//        }
//
//        return topResults;
//    }
//
//    /** Close model resources */
//    public void close() {
//        if (model != null) {
//            model.close();
//            Log.d(TAG, "Model interpreter closed.");
//        }
//    }
//
//    /** Class to store prediction result */
//    public static class Prediction {
//        public int labelIndex;
//        public float confidence;
//
//        public Prediction(int labelIndex, float confidence) {
//            this.labelIndex = labelIndex;
//            this.confidence = confidence;
//        }
//    }
//}
