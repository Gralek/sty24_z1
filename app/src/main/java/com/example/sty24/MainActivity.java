package com.example.sty24;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    private static final String DEFAULT_IMAGE_NUMBER = "000";

    private EditText editNumber;
    private EditText editFirstName;
    private EditText editLastName;
    private RadioGroup radioGroupEyeColor;
    private ImageView imagePhoto;
    private ImageView imageFingerprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Wprowadzenie danych do paszportu. Wykonał: 0000000000" );

        initViews();
        loadImages(DEFAULT_IMAGE_NUMBER);
        setListeners();
    }

    private void initViews() {
        editNumber = findViewById(R.id.editNumber);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        radioGroupEyeColor = findViewById(R.id.radioGroupEyeColor);
        imagePhoto = findViewById(R.id.imagePhoto);
        imageFingerprint = findViewById(R.id.imageFingerprint);
    }

    private void setListeners() {

        editNumber.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                loadImages(editNumber.getText().toString().trim());
            }
        });

        Button buttonOk = findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(view -> showSummary());
    }

    private void loadImages(String number) {
        setImageFromAssets(imagePhoto, number + "-zdjecie.jpg");
        setImageFromAssets(imageFingerprint, number + "-odcisk.jpg");
    }

    private void setImageFromAssets(ImageView imageView, String fileName) {
        try (InputStream stream = getAssets().open(fileName)) {
            Drawable drawable = Drawable.createFromStream(stream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {

            imageView.setImageDrawable(null);
        }
    }

    private void showSummary() {
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();

        String message;
        if (firstName.isEmpty() || lastName.isEmpty()) {
            message = "Wprowadź dane";
        } else {
            message = firstName + " " + lastName + " kolor oczu " + getSelectedEyeColor();
        }

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private String getSelectedEyeColor() {
        RadioButton selected = findViewById(radioGroupEyeColor.getCheckedRadioButtonId());
        return selected.getText().toString();
    }
}