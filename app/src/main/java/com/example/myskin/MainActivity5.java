package com.example.myskin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity5 extends AppCompatActivity {

    private ImageView processedImageView;
    private LinearLayout questionnaireLayout;
    private List<CheckBox> symptomCheckBoxes;
    private Map<String, Set<String>> diseaseSymptomsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        processedImageView = findViewById(R.id.imageView);
        questionnaireLayout = findViewById(R.id.questionnaireLayout);

        // Initialize possible diseases and their symptoms
        diseaseSymptomsMap = new HashMap<>();
        diseaseSymptomsMap.put("ringworms", Set.of("Fever", "Itching"));
        diseaseSymptomsMap.put("boils", Set.of("Localized infection", "General discomfort"));
        diseaseSymptomsMap.put("gangrene", Set.of("Open sores"));
        diseaseSymptomsMap.put("hives", Set.of("Itching", "Rapid spread", "General discomfort"));
        diseaseSymptomsMap.put("monkeypox", Set.of("Fever", "Painful blisters", "Swollen lymph nodes", "Rapid spread"));
        diseaseSymptomsMap.put("psoriasis", Set.of("Itching", "Red patches", "Scaly skin"));

        // Initialize symptom checkboxes
        symptomCheckBoxes = new ArrayList<>();
        initializeQuestionnaire();

        // Get the image URL passed from MainActivity3
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");

        new DownloadProcessedImageTask().execute(imageUrl);
    }

    private void initializeQuestionnaire() {
        String[] symptoms = {
                "Fever", "Itching", "Painful blisters", "Red patches",
                "Swollen lymph nodes", "Scaly skin", "Open sores",
                "Rapid spread", "Localized infection", "General discomfort"
        };

        for (String symptom : symptoms) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(symptom);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterDiseases();
                }
            });
            symptomCheckBoxes.add(checkBox);
            questionnaireLayout.addView(checkBox);
        }
    }

    private void filterDiseases() {
        Set<String> selectedSymptoms = new HashSet<>();
        for (CheckBox checkBox : symptomCheckBoxes) {
            if (checkBox.isChecked()) {
                selectedSymptoms.add(checkBox.getText().toString());
            }
        }

        Set<String> filteredDiseases = new HashSet<>(diseaseSymptomsMap.keySet());

        for (Map.Entry<String, Set<String>> entry : diseaseSymptomsMap.entrySet()) {
            String disease = entry.getKey();
            Set<String> symptoms = entry.getValue();
            if (!selectedSymptoms.containsAll(symptoms)) {
                filteredDiseases.remove(disease);
            }
        }

        displayFilteredDiseases(filteredDiseases);
    }

    private void displayFilteredDiseases(Set<String> diseases) {
        TextView predictionsTextView = findViewById(R.id.textViewPredictions);
        if (diseases.isEmpty()) {
            predictionsTextView.setText("No matching diseases found.");
        } else {
            predictionsTextView.setText("Possible diseases: " + diseases.toString());
        }
    }

    private class DownloadProcessedImageTask extends AsyncTask<String, Void, byte[]> {

        @Override
        protected byte[] doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

                return output.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(byte[] result) {
            if (result != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                processedImageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(MainActivity5.this, "Failed to download image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
