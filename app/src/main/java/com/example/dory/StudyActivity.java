package com.example.dory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.CONTENT);
        TextView textView = findViewById(R.id.study_content_id);
        textView.setText(message);
    }
}