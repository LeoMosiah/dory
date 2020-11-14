package com.example.dory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.dory", Context.MODE_PRIVATE);
        String lastTimeLoggedIn = Calendar.getInstance().getTime().toString();
        prefs.edit().putString("lastTimeLoggedIn", lastTimeLoggedIn).apply();
        Log.d(TAG, lastTimeLoggedIn + "Saved with success");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.dory", Context.MODE_PRIVATE);
        String defaultLastTimeLoggedIn = Calendar.getInstance().getTime().toString();
        String lastTimeLoggedIn = prefs.getString("lastTimeLoggedIn", defaultLastTimeLoggedIn);
        Log.d(TAG, lastTimeLoggedIn);
        super.onCreate(savedInstanceState);
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
    }
}