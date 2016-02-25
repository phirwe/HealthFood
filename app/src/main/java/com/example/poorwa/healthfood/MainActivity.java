package com.example.poorwa.healthfood;

import android.content.Intent;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    TextView totalNumber;
    Button viewGraph, addIngredient, selectIngredient, viewMeals;
    MealDataInterface mealDataInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProfileDataInterface profileDataInterface = new ProfileDataInterface(this);
        try {
            profileDataInterface.getInfo();
        } catch (Exception e) {
            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        HealthFoodDataInterface dataInterface = new HealthFoodDataInterface(this);
        totalNumber = (TextView) findViewById(R.id.totalNumber);
        totalNumber.setVisibility(View.GONE);

        if(!(dataInterface.getCount() > 0)) {
            try {
                totalNumber.setVisibility(View.VISIBLE);
                totalNumber.setText("Inserting into Database");
                createDatabase();
                totalNumber.setVisibility(View.GONE);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        String print = String.valueOf(dataInterface.getCount() + " Ingredients to Select From");
        totalNumber.setText(print);
        mealDataInterface = new MealDataInterface(this);
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = new Date();
        String newDateStr = curFormater.format(dateObj);
        Log.println(Log.ASSERT, "Date", newDateStr);
        List<Meal> mealList = mealDataInterface.getAll(newDateStr);
        Log.println(Log.ASSERT, "List Size", String.valueOf(mealList.size()));
        if(mealList.size() <= 0) {
            mealDataInterface.delete();
            Toast.makeText(this, "Start Your Day", Toast.LENGTH_LONG).show();
        }


        viewGraph = (Button) findViewById(R.id.viewGraph);
        addIngredient = (Button) findViewById(R.id.addIngredient);
        selectIngredient = (Button) findViewById(R.id.selectIngredient);
        viewMeals = (Button) findViewById(R.id.viewMeals);

        viewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ttobj = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS) {
                            ttobj.setLanguage(Locale.ENGLISH);
                            toSpeak = viewGraph.getText().toString();
                            ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);*/
                            Intent intent = new Intent(getBaseContext(), GraphView.class);
                            startActivity(intent);

               /*         }
                    }
                }
                );*/

            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ttobj = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            ttobj.setLanguage(Locale.ENGLISH);
                            toSpeak = addIngredient.getText().toString();*/
                            //ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                            Intent intent = new Intent(getBaseContext(), AddIngredient.class);
                            startActivity(intent);
                      /*  }
                    }
                }
                );*/

            }
        });

        selectIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ttobj = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            ttobj.setLanguage(Locale.ENGLISH);
                            toSpeak = selectIngredient.getText().toString();
                            ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);*/
                            Intent intent = new Intent(getBaseContext(), SelectIngredient.class);
                            startActivity(intent);
                       /* }
                    }
                }
                );*/
            }
        });

        viewMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewMeals.class);
                startActivity(intent);
            }
        });

    }

    public void createDatabase() throws IOException {
        HealthFood healthFood;
        HealthFoodDataInterface dataInterface = new HealthFoodDataInterface(this);
        String[] arrayRow = Input.INPUT1.split("#");
        for (String i : arrayRow) {
            String[] arrayEach = i.split("--");
            healthFood = new HealthFood();
            healthFood.setIngredient(arrayEach[0]);
            healthFood.setCalorieValue(arrayEach[1]);
            healthFood.setType(arrayEach[2]);
            dataInterface.insert(healthFood);
        }
        arrayRow = Input.INPUT2.split("#");
        for (String i : arrayRow) {
            String[] arrayEach = i.split("--");
            healthFood = new HealthFood();
            healthFood.setIngredient(arrayEach[0]);
            healthFood.setCalorieValue(arrayEach[1]);
            healthFood.setType(arrayEach[2]);
            dataInterface.insert(healthFood);
        }
    }

}
