package com.example.dory;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String CONTENT = "com.example.dory.CONTENT";
    public static final String USER_GUID = "com.example.dory.USER_GUID";
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference studies = db.collection("studies");
    CollectionReference users = db.collection("users");


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.dory", Context.MODE_PRIVATE);
        String lastTimeLoggedIn = Calendar.getInstance().getTime().toString();
        prefs.edit().putString("lastTimeLoggedIn", lastTimeLoggedIn).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        users.document(mFirebaseUser.getUid()).get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               DocumentSnapshot document = task.getResult();
               assert document != null;
               if (!document.exists()) {
                   Map<String, Object> newUser = new HashMap<>();
                   newUser.put("firstName", "");
                   newUser.put("lastName", "");
                   newUser.put("avatar", "");
                   newUser.put("score", "");
                   users.document(mFirebaseUser.getUid()).set(newUser);
               }
           }
        });
        findViewById(R.id.welcome_message).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(USER_GUID, mFirebaseUser.getUid());
            startActivity(intent);
            finish();
        });
    }

    public void navigateToStudyLocation(View view) {
        studies.document(
                "cs8R7D9gLbNYcr1E6gPw").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Intent intent = new Intent(this, StudyActivity.class);
                intent.putExtra(CONTENT, Objects.requireNonNull(Objects.requireNonNull(document).get("content")).toString());
                startActivity(intent);
            }
        });
    }

    public void navigateToStudyAquarium(View view) {
        studies.document(
                "14XhsOC14E98Eti2IMtU").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Intent intent = new Intent(this, StudyActivity.class);
                intent.putExtra(CONTENT, Objects.requireNonNull(Objects.requireNonNull(document).get("content")).toString());
                startActivity(intent);
            }
        });    }

    public void navigateToStudyFilter(View view) {
                studies.document(
                "vXOv4G22Q1aGXSdDyBIc").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Intent intent = new Intent(this, StudyActivity.class);
                intent.putExtra(CONTENT, Objects.requireNonNull(Objects.requireNonNull(document).get("content")).toString());
                startActivity(intent);
            }
        });
    }

    public void navigateToStudySubstrate(View view) {
                studies.document(
                "9IQafeLLQm0cDbP0V1vQ").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Intent intent = new Intent(this, StudyActivity.class);
                intent.putExtra(CONTENT, Objects.requireNonNull(Objects.requireNonNull(document).get("content")).toString());
                startActivity(intent);
            }
        });
    }

    public void navigateToStudyHeater(View view) {
                studies.document(
                "rQWyyosNZvGjKzFOWYep").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Intent intent = new Intent(this, StudyActivity.class);
                intent.putExtra(CONTENT, Objects.requireNonNull(Objects.requireNonNull(document).get("content")).toString());
                startActivity(intent);
            }
        });
    }

    public void navigateToStudyWater(View view) {
                studies.document(
                "rgMxeJOFa9nRQWFjUSie").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Intent intent = new Intent(this, StudyActivity.class);
                intent.putExtra(CONTENT, Objects.requireNonNull(Objects.requireNonNull(document).get("content")).toString());
                startActivity(intent);
            }
        });
    }

    public void navigateToStudyNitrogenCycle(View view) {
                studies.document(
                        "0go4DyHzzLUOjqd9rc0p").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Intent intent = new Intent(this, StudyActivity.class);
                intent.putExtra(CONTENT, Objects.requireNonNull(Objects.requireNonNull(document).get("content")).toString());
                startActivity(intent);
            }
        });
    }

    public void navigateToQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(USER_GUID, mFirebaseUser.getUid());
        startActivity(intent);
    }


}