package com.example.poorwa.healthfood;

import android.provider.BaseColumns;

/**
 * Created by poorwa on 24/2/16.
 */
public class MealDbHelper {

    public MealDbHelper() {

    }

    public static abstract class TableInfo implements BaseColumns {
        public static final String DATE = "date";
        public static final String INGREDIENT = "ingredient";
        public static final String CALORIES = "calories";
        public static final String TYPE = "type";
        public static final String TABLE_NAME = "MealIngredients";
        public static final String DATABASE_NAME = "HealthFoodCOEP2";
    }

}
