package com.example.dory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    int PICK_IMAGE_REQUEST = 22;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String guid;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        guid = intent.getStringExtra(MainActivity.USER_GUID);
        DocumentReference docRef = db.collection("users").document(guid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                user = document.toObject(UserModel.class);
                EditText firstName = findViewById(R.id.user_first_name);
                EditText lastName = findViewById(R.id.user_last_name);
                TextView score = findViewById(R.id.user_score);
                ImageButton imageButton = findViewById(R.id.user_avatar);
                firstName.setText(user.getFirstName());
                lastName.setText(user.getLastName());
                score.setText(String.format("Score: %s", user.getScore()));
                if (!user.getAvatar().isEmpty()) {
                    Picasso.get().load(user.getAvatar()).into(imageButton);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickOnImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
           Uri filePath = data.getData();
           try {
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
               StorageReference avatarsRef = storageReference.child("users/avatars" + guid);
               avatarsRef.putFile(filePath).addOnSuccessListener(taskSnapshot -> avatarsRef.getDownloadUrl().addOnSuccessListener(uri -> {
                   user.setAvatar(uri.toString());
                   Map<String, Object> updatedUser = new HashMap<>();
                   updatedUser.put("avatar", uri.toString());
                   DocumentReference docRef = db.collection("users").document(guid);
                   docRef.update(updatedUser);
                   AlertDialog.Builder alert = new AlertDialog.Builder(this);
                   alert.setTitle("Avatar updated successfully");
                   alert.setPositiveButton("Ok", (dialog, whichButton) -> {
                   });
                   alert.show();
               }));
               ImageButton imageButton = findViewById(R.id.user_avatar);
               imageButton.setImageBitmap(bitmap);
           } catch (IOException e) {
               e.printStackTrace();
           }
        }
    }

    public void onSave (View view) {
        EditText firstName = findViewById(R.id.user_first_name);
        EditText lastName = findViewById(R.id.user_last_name);
        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("firstName", String.valueOf(firstName.getText()));
        updatedUser.put("lastName", String.valueOf(lastName.getText()));
        updatedUser.put("avatar", user.getAvatar());
        updatedUser.put("score", user.getScore());
        DocumentReference docRef = db.collection("users").document(guid);
        docRef.set(updatedUser);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Information updated successfully");
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
        });
        alert.show();
    }

    public void onSignOut(View view) {
        mFirebaseAuth.signOut();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }
}