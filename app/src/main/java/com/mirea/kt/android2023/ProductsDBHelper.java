package com.mirea.kt.android2023;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class ProductsDBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "productTitle";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_BUY = "isBuy";
    public static final String COLUMN_CONTEINERID = "conteinerID";
    public static final String PRODUCTS = "products";

    public ProductsDBHelper(@Nullable Context context) {

        super(context, "products.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PRODUCTS + " (" + COLUMN_ID
                + " integer primary key,"
                + COLUMN_TITLE + " text,"
                + COLUMN_QUANTITY + " text,"
                + COLUMN_BUY + " text,"
                + COLUMN_CONTEINERID + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + COLUMN_TITLE);
        onCreate(db);
    }

    public void addOne(Products products){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,products.getTitle());
        cv.put(COLUMN_QUANTITY,products.getQuantitu());
        cv.put(COLUMN_BUY,products.getIsBuy());
        cv.put(COLUMN_CONTEINERID,products.getConteinerID());
        db.insert(PRODUCTS ,null,cv);
        db.close();
    }

    public LinkedList<Products> getAll() {
        LinkedList<Products> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(PRODUCTS,null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                int id_id = cursor.getColumnIndex(COLUMN_ID);
                int id_title = cursor.getColumnIndex(COLUMN_TITLE);
                int id_quantity = cursor.getColumnIndex(COLUMN_QUANTITY);
                int id_isBuy = cursor.getColumnIndex(COLUMN_BUY);
                int id_conteinerID = cursor.getColumnIndex(COLUMN_CONTEINERID);

                Products products = new Products(cursor.getString(id_id),
                                                 cursor.getString(id_title),
                                                 cursor.getString(id_quantity),
                                                 cursor.getString(id_isBuy),
                                                 cursor.getString(id_conteinerID));
                list.add(products);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public LinkedList<Products> getListBuyID(String conteinerID) {
        LinkedList<Products> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(PRODUCTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id_id = cursor.getColumnIndex(COLUMN_ID);
                int id_title = cursor.getColumnIndex(COLUMN_TITLE);
                int id_quantity = cursor.getColumnIndex(COLUMN_QUANTITY);
                int id_isBuy = cursor.getColumnIndex(COLUMN_BUY);
                int id_conteinerID = cursor.getColumnIndex(COLUMN_CONTEINERID);
//                Log.i("ValueCheck ","Инициализация переменных в метоже отработала"
//                                +cursor.getString(id_title) + "\n"
//                                +cursor.getString(id_quantity) + "\n"
//                                +cursor.getString(id_isBuy) + "\n"
//                                +cursor.getString(id_conteinerID));
                Log.i("CreateConteinerCheck"," " + cursor.getString(id_conteinerID));
                    if (cursor.getString(id_conteinerID) != null && cursor.getString(id_conteinerID).equals((String) conteinerID)){
                        Products products = new Products(cursor.getString(id_id),
                                cursor.getString(id_title),
                                cursor.getString(id_quantity),
                                cursor.getString(id_isBuy),
                                cursor.getString(id_conteinerID));
                       // Log.i("ValueCheck ","Работа getListBuyID " + cursor.getString(id_title) + " " + cursor.getString(id_quantity));
                        list.add(products);
                    }
                    else {
                        //Log.i("ValueCheck ","Условие не прошло");
                        continue;
                    }
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public void updateOne(String id , Products products){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,products.getTitle());
        cv.put(COLUMN_QUANTITY,products.getQuantitu());
        cv.put(COLUMN_BUY,products.getIsBuy());
        cv.put(COLUMN_CONTEINERID,products.getConteinerID());

        db.update(PRODUCTS,cv,COLUMN_ID+" = ?",new String[] {id});
        db.close();
    }

    public void removeOne(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCTS,COLUMN_ID+" =?",new String[]{id});
        db.close();
    }



}
