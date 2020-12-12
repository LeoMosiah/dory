package com.example.dory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {
    private final String TAG = "QuizActivity";
    int quizStep = 0;
    String guid;
    String score;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<QuizModel> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = getIntent();
        guid = intent.getStringExtra(MainActivity.USER_GUID);
        DocumentReference docRef = db.collection("users").document(guid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                score = Objects.requireNonNull(document.get("score")).toString();
            }
        });
        db.collection("questions").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())) {
                    QuizModel quizModel = document.toObject(QuizModel.class);
                    questions.add(quizModel);
                }
                prepareQuiz(quizStep);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void prepareQuiz(int position) {
        TextView questionView = findViewById(R.id.quiz_question);
        questionView.setText(questions.get(position).question);
        ArrayList<String> answers = new ArrayList<>(questions.get(position).wrongAnswers);
        answers.add(questions.get(position).correctAnswer);
        Collections.shuffle(answers);
        TextView answer1 = findViewById(R.id.answer1);
        answer1.setText(answers.get(0));
        answer1.setOnClickListener(v -> {
            if (answer1.getText().toString().equals(questions.get(position).correctAnswer)) {
                onCorrectAnswer();
            } else {
                onWrongAnswer();
            }
        });
        TextView answer2 = findViewById(R.id.answer2);
        answer2.setText(answers.get(1));
        answer2.setOnClickListener(v -> {
            if (answer2.getText().toString().equals(questions.get(position).correctAnswer)) {
                onCorrectAnswer();
            } else {
                onWrongAnswer();
            }
        });
        TextView answer3 = findViewById(R.id.answer3);
        answer3.setText(answers.get(2));
        answer3.setOnClickListener(v -> {
            if (answer3.getText().toString().equals(questions.get(position).correctAnswer)) {
                onCorrectAnswer();
            } else {
                onWrongAnswer();
            }
        });
    }

    protected void onCorrectAnswer() {
        DocumentReference docRef = db.collection("users").document(guid);
        Map<String, Object> updatedUser = new HashMap<>();
        int newScore = Integer.parseInt(score) + 1;
        String stringNewScore = String.valueOf(newScore);
        updatedUser.put("score", stringNewScore);
        docRef.update(updatedUser);
        score = stringNewScore;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Congratulations you got it right");
        if (quizStep == questions.size() - 1) {
            alert.setPositiveButton("Finish", (dialog, whichButton) -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        } else {
            alert.setPositiveButton("Next Question", (dialog, whichButton) -> {
                quizStep ++;
                prepareQuiz(quizStep);
            });
        }
        alert.show();
    }

    protected void onWrongAnswer() {
        if (Integer.parseInt(score) > 0) {
            DocumentReference docRef = db.collection("users").document(guid);
            Map<String, Object> updatedUser = new HashMap<>();
            int newScore = Integer.parseInt(score) - 1;
            String stringNewScore = String.valueOf(newScore);
            updatedUser.put("score", stringNewScore);
            docRef.update(updatedUser);
            score = stringNewScore;
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Sorry you got it wrong");
        if (quizStep == questions.size() - 1) {
            alert.setPositiveButton("Finish", (dialog, whichButton) -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        } else {
            alert.setPositiveButton("Next Question", (dialog, whichButton) -> {
                quizStep ++;
                prepareQuiz(quizStep);
            });
        }
        alert.setNegativeButton("Retry", (dialog, whichButton) -> {
        });
        alert.show();
    }
}