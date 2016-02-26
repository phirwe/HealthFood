package com.example.poorwa.healthfood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class SelectIngredient extends AppCompatActivity implements View.OnClickListener {

    TableLayout ingredientTable;
    Button searchButton;
    HealthFoodDataInterface dataInterface;
    String[] ingredientName, calorieValue, type;
    EditText searchText;
    List<HealthFood> healthFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredient);

        ingredientTable = (TableLayout) findViewById(R.id.ingredientTable);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);


        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                searchText.setHint(searchText.getText().toString());
                dataInterface = new HealthFoodDataInterface(getBaseContext());
                ingredientTable.removeAllViews();
                try {
                    healthFoods = dataInterface.searchQuery(searchText.getText().toString());
                    int rows = healthFoods.size();
                    BuildTable(rows);
                }
                catch (Exception e) {
                    Toast.makeText(getBaseContext(),
                            "No such ingredient in database",
                            Toast.LENGTH_SHORT).show();
                }
                searchText.setText("");
            }
        });

    }

    private void BuildTable(int rows) {

        rows++;
        ingredientName = new String[rows];
        calorieValue = new String[rows];
        type = new String[rows];

        for (int k = 0; k < rows; k++) {

            if(k > 0) {

                ingredientName[k] = healthFoods.get(k - 1).getIngredient();
                calorieValue[k] = healthFoods.get(k - 1).getCalorieValue();
                type[k] = healthFoods.get(k - 1).getType();
            }

            else {
                ingredientName[k] = "Ingredient Name";
                calorieValue[k] = "Calorie Value";
                type[k] = "Type";
            }

            TableRow tr = new TableRow(SelectIngredient.this);
            tr.layout(0, 0, 0, 0);
            TextView tv1 = new TextView(SelectIngredient.this);
            tv1.setText(String.valueOf(ingredientName[k]));
            TextView tv2 = new TextView(SelectIngredient.this);
            tv2.setText(calorieValue[k]);
            TextView tv3 = new TextView(SelectIngredient.this);

            switch (type[k]) {
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

            tv3.setText(type[k]);
            tv1.setBackgroundResource((R.drawable.cell_shape));
            tv1.setTextSize(25f);
            tv1.setHeight(130);
            tv1.setMaxWidth(500);
            tv2.setBackgroundResource((R.drawable.cell_shape));
            tv2.setTextSize(25f);
            tv2.setHeight(130);
            tv2.setMaxWidth(500);
            tv3.setBackgroundResource((R.drawable.cell_shape));
            tv3.setTextSize(25f);
            tv3.setHeight(130);
            tv3.setMaxWidth(500);
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv1.setPadding(0, 13, 0, 0);
            tv2.setPadding(0, 13, 0, 0);
            tv3.setPadding(0, 13, 0, 0);

            if(k == 0) {
                tv1.setTextSize(40f);
                tv2.setTextSize(40f);
                tv3.setTextSize(40f);

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
            tr.setId(k);
            tr.setOnClickListener(SelectIngredient.this);
            ingredientTable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        }
    }

    @Override
    public void onClick(View v) {
        int clicked_id = v.getId();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = new Date();
        String newDateStr = curFormater.format(dateObj);
        String[] array = {newDateStr, ingredientName[clicked_id], calorieValue[clicked_id], type[clicked_id]};
        Intent intent = new Intent(this, AddWeight.class);
        intent.putExtra("KEY", array);
        startActivity(intent);
        finish();
    }
}
