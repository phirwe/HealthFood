package com.example.poorwa.healthfood;

import android.provider.BaseColumns;

/**
 * Created by poorwa on 24/2/16.
 */
public class ProfileDbHelper {
    public  ProfileDbHelper() {

    }

    public static abstract class TableInfo implements BaseColumns {
        public static final String NAME = "name";
        public static final String WEIGHT = "weight";
        public static final String HEIGHT = "height";
        public static final String GENDER = "gender";
        public static final String TABLE_NAME = "Profile";
        public static final String DATABASE_NAME = "HealthFoodCOEP1";
    }

}
