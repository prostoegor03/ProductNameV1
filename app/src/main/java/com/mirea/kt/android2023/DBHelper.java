package com.mirea.kt.android2023;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_CONTENT = "content";

  public static final String COLUMN_TYPE = "type";
  public static final String NOTES = "notes";

    public DBHelper(@Nullable Context context) {
        super(context, "main.db", null , 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NOTES + " (" + COLUMN_ID
            + " integer primary key," + COLUMN_TITLE + " text," + COLUMN_CONTENT + " text," + COLUMN_TYPE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + COLUMN_TITLE);
        onCreate(db);
    }

    //Метод для добавления одной записи
    public void addOne(Notes notes){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,notes.getTitle());
        cv.put(COLUMN_CONTENT,notes.getContent());
        cv.put(COLUMN_TYPE,notes.getType());
        db.insert(NOTES ,null,cv);
        db.close();
    }
    public String createConteiner(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String title = "Список покупок";
        cv.put(COLUMN_TITLE,title);
        long id = db.insert(NOTES,null,cv);
        return Long.toString(id);
    }

    //метод для того чтобы вытащить базу
    public LinkedList<Notes> getAll() {

        LinkedList<Notes> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(NOTES,null,null,null,null,null,null);

        if(cursor.moveToFirst()) {
            do {
                int id_id = cursor.getColumnIndex(COLUMN_ID);
                int id_title = cursor.getColumnIndex(COLUMN_TITLE);
                int id_content = cursor.getColumnIndex(COLUMN_CONTENT);
                int id_type = cursor.getColumnIndex(COLUMN_TYPE);

                Notes notes = new Notes(cursor.getString(id_id),cursor.getString(id_title),cursor.getString(id_content),cursor.getString(id_type));
                list.add(notes);

            } while (cursor.moveToNext());

        }
        db.close();
        return list;
    }

    //Метод для изменения одной записи
    public void updateOne(String id , Notes notes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,notes.getTitle());
        cv.put(COLUMN_CONTENT,notes.getContent());
        cv.put(COLUMN_TYPE,notes.getType());
        db.update(NOTES,cv,COLUMN_ID+" = ?",new String[] {id});
        db.close();
    }


    //Метод для удаления одной записи
    public void removeOne(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTES,COLUMN_ID+" =?",new String[]{id});
        db.close();
    }





}
