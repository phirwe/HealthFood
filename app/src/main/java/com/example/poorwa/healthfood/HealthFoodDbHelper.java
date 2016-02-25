package com.example.poorwa.healthfood;

import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by poorwa on 18/2/16.
 */
public class HealthFoodDbHelper {
    public HealthFoodDbHelper() {
    }

    public static abstract class TableInfo implements BaseColumns {
        public static final String INGREDIENT = "ingredient";
        public static final String CALORIE_VALUE = "calorie_value";
        public static final String TYPE = "type";
        public static final String TABLE_NAME = "NutritionFacts";
        public static final String DATABASE_NAME = "HealthFoodCOEP";
    }



}
