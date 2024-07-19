package com.example.myskin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity3 extends AppCompatActivity {

    private TextView textViewResults, textViewCamera, textViewSend;
    private ImageView imageView;
    private static final int CAMERA_REQUEST_CODE = 12;
    private Bitmap selectedBitmap;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textViewResults = findViewById(R.id.textViewResults);
        textViewSend = findViewById(R.id.textViewSend);
        imageView = findViewById(R.id.imageView);
        textViewCamera = findViewById(R.id.textViewCamera);

        textViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        textViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedBitmap != null) {
                    new StartYoloTask().execute();
                    startTimer();
                    textViewCamera.setVisibility(View.GONE);
                    textViewSend.setText("...Processing...");
                    textViewSend.setClickable(false);
                    textViewSend.setEnabled(false);
                } else {
                    textViewResults.setText("Error: No image selected");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedBitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(selectedBitmap);
            textViewSend.setVisibility(View.VISIBLE);
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {
                textViewResults.setText("Processing image: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                textViewResults.setText("Image processed");
            }
        }.start();
    }

    private class StartYoloTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://b6b5-102-117-164-164.ngrok-free.app/start_yolo_execution");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imageBytes = outputStream.toByteArray();

                connection.getOutputStream().write(imageBytes);
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
                    return s.hasNext() ? s.next() : "";
                } else {
                    return "Error: YOLO execution failed";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity3.this, "Server: " + result, Toast.LENGTH_SHORT).show();
            textViewResults.setText(result);
            textViewSend.setEnabled(true);
            textViewSend.setClickable(true);

            if (result.contains("Processing done")) {
                stopTimer();
                openNewActivity("https://0555-102-117-164-5.ngrok-free.app/processed_image");
            } else if (result.contains("Write error") || result.contains("YOLO execution failed")) {
                stopTimer();
                textViewCamera.setVisibility(View.VISIBLE);
                textViewSend.setVisibility(View.GONE);
                textViewSend.setClickable(true);
                textViewSend.setText("Send Image for Processing");
            }
        }
    }

    private void openNewActivity(String imageUrl) {
        Intent intent = new Intent(this, MainActivity5.class);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
        finish();
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
