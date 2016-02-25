package com.example.poorwa.healthfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    EditText nameText, weightText, heightText;
    Button saveButton;
    Profile profile;
    RadioGroup genderGroup;
    RadioButton male, female;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameText = (EditText) findViewById(R.id.nameText);
        weightText = (EditText) findViewById(R.id.weightText);
        heightText = (EditText) findViewById(R.id.heightText);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

        saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile = new Profile();
                float height = Float.valueOf(heightText.getText().toString());
                float weight = Float.valueOf(weightText.getText().toString());
                profile.setName(nameText.getText().toString());
                profile.setWeight(weightText.getText().toString());
                profile.setHeight(heightText.getText().toString());
                profile.setGender(gender);
                ProfileDataInterface dataInterface = new ProfileDataInterface(getBaseContext());
                dataInterface.insert(profile);
                Toast.makeText(getBaseContext(), "User Successfully Added", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void getGender(View view) {

        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.male:
                if (isChecked) {
                    gender = "16";
                } else {
                    gender = "";
                }
                break;
            case R.id.female:
                if (isChecked) {
                    gender = "11";
                } else {
                    gender = "";
                }
                break;
        }
    }

}
