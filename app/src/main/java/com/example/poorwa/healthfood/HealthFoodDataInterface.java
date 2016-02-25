package com.example.poorwa.healthfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.poorwa.healthfood.HealthFoodDbHelper.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poorwa on 18/2/16.
 */
public class HealthFoodDataInterface extends SQLiteOpenHelper {

    public long z;

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ TableInfo.TABLE_NAME+
            "("+ TableInfo.INGREDIENT +" TEXT PRIMARY KEY, " +
            TableInfo.CALORIE_VALUE + " TEXT, " +
            TableInfo.TYPE + " TEXT )";

    public HealthFoodDataInterface(Context context) {
        super(context, TableInfo.DATABASE_NAME, null, database_version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TableInfo.TABLE_NAME + " IF EXISTS");
        db.execSQL(CREATE_QUERY);
    }

    public void insert(HealthFood healthFood) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableInfo.INGREDIENT, healthFood.getIngredient());
        cv.put(TableInfo.CALORIE_VALUE, healthFood.getCalorieValue());
        cv.put(TableInfo.TYPE, healthFood.getType());

        z = SQ.insert(TableInfo.TABLE_NAME, null, cv);
        Log.println(Log.ASSERT, "Inserted row", String.valueOf(z));
        SQ.close();

    }

    public HealthFood getInfo(String ingredient) {
        HealthFood healthFood = new HealthFood();
        SQLiteDatabase SQ = this.getReadableDatabase();
        Cursor c = SQ.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE " + TableInfo.INGREDIENT + " LIKE '" + ingredient + "'", null);
        c.moveToFirst();
        int i = 0;
        healthFood.setIngredient(c.getString(i++));
        healthFood.setCalorieValue(c.getString(i++));
        healthFood.setType(c.getString(i++));

        SQ.close();

        return healthFood;
    }

    public void update(HealthFood healthFood) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String selection = "ingredient LIKE ?";
        String[] args = {healthFood.getIngredient()};

        cv.put(TableInfo.INGREDIENT, healthFood.getIngredient());
        cv.put(TableInfo.CALORIE_VALUE, healthFood.getCalorieValue());
        cv.put(TableInfo.TYPE, healthFood.getType());

        long z = SQ.update(TableInfo.TABLE_NAME, cv, selection, args);
        Log.println(Log.ASSERT, "Updated row", String.valueOf(z));
        SQ.close();
    }

    public List<HealthFood> getAll() {
        int count, i = 0, j = 1;
        List<HealthFood> healthFoods  = new ArrayList<HealthFood>();
        SQLiteDatabase SQ = this.getReadableDatabase();
        Cursor c = SQ.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME, null);
        count = c.getCount();
        c.moveToFirst();
        HealthFood healthFood;
        healthFood = new HealthFood();
        healthFood.setIngredient(c.getString(j++));
        healthFood.setType(c.getString(j++));
        healthFood.setCalorieValue(c.getString(j++));
        healthFoods.add(healthFood);
        while(c.moveToNext() == true) {
            j = 1;
            i++;
            healthFood = new HealthFood();
            healthFood.setIngredient(c.getString(j++));
            healthFood.setType(c.getString(j++));
            healthFood.setCalorieValue(c.getString(j++));
            healthFoods.add(healthFood);
        }
        SQ.close();
        return healthFoods;
    }

    public List<HealthFood> searchQuery(String ingredient) {
        List<HealthFood> healthFoods = new ArrayList<>();
        SQLiteDatabase SQ = this.getReadableDatabase();
        HealthFood healthFood;
        Cursor c = SQ.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE " + TableInfo.INGREDIENT + " LIKE '%" + ingredient + "%'", null);
        c.moveToFirst();
        int i;
        do {
            i = 0;
            healthFood = new HealthFood();
            healthFood.setIngredient(c.getString(i++));
            healthFood.setCalorieValue(c.getString(i++));
            healthFood.setType(c.getString(i++));
            healthFoods.add(healthFood);
        } while(c.moveToNext());

        SQ.close();
        return healthFoods;

    }

    public HealthFood getRecent() {
        HealthFood healthFood = new HealthFood();
        SQLiteDatabase SQ = this.getReadableDatabase();
        Cursor c = SQ.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME, null);
        c.moveToLast();
        int i = 1;
        healthFood.setIngredient(c.getString(i++));
        healthFood.setCalorieValue(c.getString(i++));
        healthFood.setType(c.getString(i++));
        SQ.close();
        return healthFood;
    }

    public int getCount() {
        SQLiteDatabase SQ = this.getReadableDatabase();
        Cursor c = SQ.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME, null);
        return c.getCount();
    }



}
