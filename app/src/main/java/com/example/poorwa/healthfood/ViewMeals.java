package com.example.poorwa.healthfood;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ViewMeals extends AppCompatActivity {


    List<Meal> mealList;
    MealDataInterface mealDataInterface;
    TableLayout mealTable;
    String[] ingredientName, calories, type;
    TextView dateView, calorieView;
    float totalCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meals);

        dateView = (TextView) findViewById(R.id.dateView);
        mealTable = (TableLayout) findViewById(R.id.mealTable);
        calorieView = (TextView) findViewById(R.id.calorieView);

        mealDataInterface = new MealDataInterface(this);
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = new Date();
        String newDateStr = curFormater.format(dateObj);
        mealList = mealDataInterface.getAll(newDateStr);
        dateView.setText(newDateStr + ":  View Your Calorie Intake for Today");
        Collections.sort(mealList, new TypeComparator());
        BuildTable(mealList.size());

    }

    private void BuildTable(int rows) {

        rows++;
        ingredientName = new String[rows];
        calories = new String[rows];
        type = new String[rows];

        for (int i = 0; i < rows; i++) {

            if (i > 0) {
                ingredientName[i] = mealList.get(i - 1).getIngredient();
                calories[i] = mealList.get(i - 1).getCalories();
                totalCalories += Float.valueOf(calories[i]);
                type[i] = mealList.get(i - 1).getType();
            } else {
                ingredientName[i] = "Ingredient Name";
                calories[i] = "Calories";
                type[i] = "Type";
            }

            TableRow tr = new TableRow(ViewMeals.this);
            tr.layout(0, 0, 0, 0);
            TextView tv1 = new TextView(ViewMeals.this);
            tv1.setText(String.valueOf(ingredientName[i]));
            TextView tv2 = new TextView(ViewMeals.this);
            tv2.setText(calories[i]);
            TextView tv3 = new TextView(ViewMeals.this);

            switch (type[i]) {
                case "Red":
                    tv3.setTextColor(Color.RED);
                    break;
                case "Green":
                    tv3.setTextColor(Color.GREEN);
                    break;
                case "Yellow":
                    tv3.setTextColor(Color.YELLOW);
                    break;
            }

            tv3.setText(type[i]);
            tv1.setBackgroundResource(R.drawable.cell_shape);
            tv1.setTextSize(25f);
            tv1.setHeight(130);
            tv1.setMaxWidth(500);
            tv2.setBackgroundResource(R.drawable.cell_shape);
            tv2.setTextSize(25f);
            tv2.setHeight(130);
            tv2.setMaxWidth(500);
            tv3.setBackgroundResource(R.drawable.cell_shape);
            tv3.setTextSize(25f);
            tv3.setHeight(130);
            tv3.setMaxWidth(500);
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv1.setPadding(0, 12, 0, 0);
            tv2.setPadding(0, 12, 0, 0);
            tv3.setPadding(0, 12, 0, 0);

            if (i == 0) {
                tv1.setTextSize(35f);
                tv2.setTextSize(35f);
                tv3.setTextSize(35f);

                tv1.setHeight(150);
                tv2.setHeight(150);
                tv3.setHeight(150);

                tv1.setPadding(0, 18, 0, 0);
                tv2.setPadding(0, 18, 0, 0);
                tv3.setPadding(0, 18, 0, 0);

                tv1.setTextColor(Color.BLACK);
                tv2.setTextColor(Color.BLACK);
                tv3.setTextColor(Color.BLACK);
            }

            tr.addView(tv1);
            tr.addView(tv2);
            tr.addView(tv3);
            tr.setId(i);
            mealTable.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
        DecimalFormat df = new DecimalFormat("0.00");
        totalCalories = Float.parseFloat(df.format(totalCalories));
        calorieView.setText(String.valueOf(totalCalories) + " cal");
    }
}
