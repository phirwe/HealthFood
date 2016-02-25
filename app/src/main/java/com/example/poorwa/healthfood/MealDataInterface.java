package com.example.poorwa.healthfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.poorwa.healthfood.MealDbHelper.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poorwa on 24/2/16.
 */
public class MealDataInterface extends SQLiteOpenHelper {
    public long z;

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ MealDbHelper.TableInfo.TABLE_NAME+
            "("+ TableInfo.DATE + " TEXT, " +
            MealDbHelper.TableInfo.INGREDIENT +" TEXT PRIMARY KEY, " +
            MealDbHelper.TableInfo.CALORIES + " TEXT, " +
            MealDbHelper.TableInfo.TYPE + " TEXT )";

    public MealDataInterface(Context context) {
        super(context, MealDbHelper.TableInfo.DATABASE_NAME, null, database_version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + MealDbHelper.TableInfo.TABLE_NAME + " IF EXISTS");
        db.execSQL(CREATE_QUERY);
    }

    public void insert(Meal meal) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableInfo.DATE, meal.getDate());
        cv.put(MealDbHelper.TableInfo.INGREDIENT, meal.getIngredient());
        cv.put(MealDbHelper.TableInfo.CALORIES, meal.getCalories());
        cv.put(MealDbHelper.TableInfo.TYPE, meal.getType());

        z = SQ.insert(MealDbHelper.TableInfo.TABLE_NAME, null, cv);
        Log.println(Log.ASSERT, "Meal Inserted", String.valueOf(z));
        SQ.close();

    }

    public void delete() {
        SQLiteDatabase SQ = this.getWritableDatabase();
        SQ.delete(TableInfo.TABLE_NAME, null, null);
        SQ.close();
    }

    public Meal getInfo(String date) {
        SQLiteDatabase SQ = this.getReadableDatabase();
        Cursor c = SQ.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE " + TableInfo.DATE + " LIKE '" + date + "'", null);
        c.moveToFirst();
        Meal meal = new Meal();
        int i = 0;
        meal.setDate(c.getString(i++));
        meal.setIngredient(c.getString(i++));
        meal.setCalories(c.getString(i++));
        meal.setType(c.getString(i++));
        return meal;
    }

    public List<Meal> getAll(String date) {
        Meal meal;
        List<Meal> mealList = new ArrayList<>();
        SQLiteDatabase SQ = this.getReadableDatabase();
        Cursor c = SQ.rawQuery("SELECT * FROM " + MealDbHelper.TableInfo.TABLE_NAME + " WHERE " + MealDbHelper.TableInfo.DATE + " LIKE '" + date + "'", null);
        c.moveToFirst();
        Log.println(Log.ASSERT, "Cursor Count", String.valueOf(c.getCount()));
        if(c.getCount() > 0) {
            do {
                int i = 0;
                meal = new Meal();
                meal.setDate(c.getString(i++));
                meal.setIngredient(c.getString(i++));
                meal.setCalories(c.getString(i++));
                meal.setType(c.getString(i++));
                mealList.add(meal);
            } while (c.moveToNext());
        }

        SQ.close();

        return mealList;
    }

    public void update(Meal meal) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String selection = "ingredient LIKE ?";
        String[] args = {meal.getIngredient()};

        cv.put(MealDbHelper.TableInfo.INGREDIENT, meal.getIngredient());
        cv.put(MealDbHelper.TableInfo.CALORIES, meal.getCalories());
        cv.put(MealDbHelper.TableInfo.TYPE, meal.getType());

        long z = SQ.update(MealDbHelper.TableInfo.TABLE_NAME, cv, selection, args);
        Log.println(Log.ASSERT, "Updated row", String.valueOf(z));
        SQ.close();
    }
}
