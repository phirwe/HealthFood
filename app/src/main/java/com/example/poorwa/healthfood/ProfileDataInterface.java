package com.example.poorwa.healthfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by poorwa on 24/2/16.
 */
public class ProfileDataInterface extends SQLiteOpenHelper {

    public long z;
    public static final int database_version = 1;

    public ProfileDataInterface(Context context) {
        super(context, ProfileDbHelper.TableInfo.DATABASE_NAME, null, database_version);
    }

    public String CREATE_QUERY = "CREATE TABLE " + ProfileDbHelper.TableInfo.TABLE_NAME + " ( " +
            ProfileDbHelper.TableInfo.NAME + " TEXT, " +
            ProfileDbHelper.TableInfo.WEIGHT + " TEXT, " +
            ProfileDbHelper.TableInfo.HEIGHT + " TEXT, " +
            ProfileDbHelper.TableInfo.GENDER + " TEXT )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProfileDbHelper.TableInfo.TABLE_NAME);
        onCreate(db);
    }

    public void insert(Profile profile) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ProfileDbHelper.TableInfo.NAME, profile.getName());
        cv.put(ProfileDbHelper.TableInfo.WEIGHT, profile.getWeight());
        cv.put(ProfileDbHelper.TableInfo.HEIGHT, profile.getHeight());
        cv.put(ProfileDbHelper.TableInfo.GENDER, profile.getGender());

        z = SQ.insert(ProfileDbHelper.TableInfo.TABLE_NAME, null, cv);
        Log.println(Log.ASSERT, "Profile Insert", String.valueOf(z));
        SQ.close();
    }

    public Profile getInfo() {
        SQLiteDatabase SQ = this.getReadableDatabase();
        Cursor c = SQ.rawQuery("SELECT * FROM " + ProfileDbHelper.TableInfo.TABLE_NAME, null);
        c.moveToFirst();
        Profile profile = new Profile();
        int i = 0;
        profile.setName(c.getString(i++));
        profile.setWeight(c.getString(i++));
        profile.setHeight(c.getString(i++));
        profile.setGender(c.getString(i++));
        return profile;
    }

    public void update(Profile profile) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ProfileDbHelper.TableInfo.NAME, profile.getName());
        cv.put(ProfileDbHelper.TableInfo.WEIGHT, profile.getWeight());
        cv.put(ProfileDbHelper.TableInfo.HEIGHT, profile.getHeight());
        cv.put(ProfileDbHelper.TableInfo.GENDER, profile.getGender());

        z = SQ.insert(ProfileDbHelper.TableInfo.TABLE_NAME, null, cv);
        Log.println(Log.ASSERT, "Profile Insert", String.valueOf(z));
        SQ.close();
    }

}
