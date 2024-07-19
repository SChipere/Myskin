package com.example.myskin;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private LinearLayout tile2, tile3;
    private Random random = new Random();
    private int currentTileIndex = 0;
    private RecyclerView recyclerViewDoctors;
    private DoctorsAdapter doctorsAdapter;
    private List<Doctor> doctorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tile2 = findViewById(R.id.tile2);
        tile3 = findViewById(R.id.tile3);
        recyclerViewDoctors = findViewById(R.id.recyclerViewDoctors);

        startSequentialAnimation();

        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(this));
        doctorsAdapter = new DoctorsAdapter(doctorList);
        recyclerViewDoctors.setAdapter(doctorsAdapter);

        loadDoctorsFromBack4App();
    }

    private void loadDoctorsFromBack4App() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctors");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        ParseFile profilePicture = object.getParseFile("Profile_picture");
                        String profilePictureUrl = (profilePicture != null) ? profilePicture.getUrl() : null;

                        Doctor doctor = new Doctor(
                                object.getString("Doctor_id"),
                                object.getString("Name"),
                                object.getString("lastname"),
                                object.getString("location"),
                                profilePictureUrl,
                                object.getString("Phone")
                        );
                        doctorList.add(doctor);
                    }
                    doctorsAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startSequentialAnimation() {
        View[] tiles = {tile2, tile3};
        animateTile(tiles[currentTileIndex]);
    }

    private void animateTile(View tile) {
        tile.setPivotX(tile.getWidth() / 2);
        tile.setPivotY(tile.getHeight() / 2);

        boolean flipOnXAxis = random.nextBoolean();
        ObjectAnimator flipAnimation;

        if (flipOnXAxis) {
            flipAnimation = ObjectAnimator.ofFloat(tile, "rotationX", 0f, 360f);
        } else {
            flipAnimation = ObjectAnimator.ofFloat(tile, "rotationY", 0f, 360f);
        }

        flipAnimation.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(flipAnimation);

        animatorSet.setStartDelay(3000);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                currentTileIndex = (currentTileIndex + 1) % 2;
                startSequentialAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    // Method to handle tile1 click and open MainActivity3
    public void openMainActivity4(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
        startActivity(intent);
    }
    public void openMainActivity3(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity(intent);
    }
    public void openMainActivity1(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }
}
