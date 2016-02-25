package com.example.poorwa.healthfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWeight extends AppCompatActivity {

    Button weightButton;
    EditText weightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        weightText = (EditText) findViewById(R.id.weightText);
        weightButton = (Button) findViewById(R.id.weightButton);

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] array = getIntent().getStringArrayExtra("KEY");
                Meal meal = new Meal();
                meal.setDate(array[0]);
                meal.setIngredient(array[1]);
                meal.setCalories(String.valueOf(Float.valueOf(array[2]) * Float.valueOf(weightText.getText().toString()) / 100));
                meal.setType(array[3]);

                MealDataInterface dataInterface = new MealDataInterface(getBaseContext());

                dataInterface.insert(meal);

                if(dataInterface.z == -1) {
                    dataInterface.update(meal);
                }

                Toast.makeText(getBaseContext(), "Ingredient " + meal.getIngredient() + " Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
