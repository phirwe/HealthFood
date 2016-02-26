package com.example.poorwa.healthfood;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddIngredient extends AppCompatActivity {

    Button saveIngredient, cancelIngredient;
    HealthFood healthFood;
    EditText name, calorieValue;
    Spinner type;
    ArrayAdapter typeAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        saveIngredient = (Button) findViewById(R.id.saveIngredient);
        cancelIngredient = (Button) findViewById(R.id.cancelIngredient);
        name = (EditText) findViewById(R.id.name);
        type = (Spinner) findViewById(R.id.type);
        calorieValue = (EditText) findViewById(R.id.calorieValue);
        
        healthFood = new HealthFood();

        typeAdapter = ArrayAdapter.createFromResource(this, R.array.food_types, android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
        
        
        saveIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty() || type.getSelectedItemPosition() == 0 ||
                        calorieValue.getText().toString().isEmpty()) {

                    String displayText = "You have not completed all fields!";
                    displayAlert(displayText);


                } else {

                    healthFood.setIngredient(name.getText().toString());
                    healthFood.setType(type.getSelectedItem().toString());
                    healthFood.setCalorieValue(calorieValue.getText().toString());

                    HealthFoodDataInterface dataInterface = new HealthFoodDataInterface(getBaseContext());
                    dataInterface.insert(healthFood);
                    if (dataInterface.z == -1) {
                        dataInterface.update(healthFood);
                    }
                    healthFood = dataInterface.getInfo(healthFood.getIngredient());
                    Log.println(Log.ASSERT, "Ingredient Inserted", healthFood.getIngredient());

                    Toast.makeText(getBaseContext(), "Ingredient Inserted", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        cancelIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Ingredient Addition Canceled", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        

    }

    public void displayAlert(String displayText) {
        new AlertDialog.Builder(this)
                .setTitle("New Ingredient")
                .setMessage(displayText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                })
                .show();
    }

}
