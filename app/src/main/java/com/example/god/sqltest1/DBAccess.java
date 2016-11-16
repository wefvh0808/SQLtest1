package com.example.god.sqltest1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GOD on 2016/11/16.
 */

public class DBAccess extends SQLiteOpenHelper {
    final static String TABLE_NAME="todolist";//定義資料表名稱
    final static String ID_FIELD="_id";//定義_id欄位
    final static String DATE_FIELD="date";//定義date欄位
    final static String TIME_FIELD="time";//定義time欄位
    final static String TITLE_FIELD="title";//定義title欄位

    public DBAccess(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建立資料表
        String sql="create table "+TABLE_NAME+"("
                +ID_FIELD+" integer primary key autoincrement,"
                +DATE_FIELD+" text, "
                +TIME_FIELD+" text, "
                +TITLE_FIELD+" text)";//sql語法
        db.execSQL(sql);//執行sql語法

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+TABLE_NAME);//將資料表刪除
        onCreate(db);//資料重建

    }
    //自訂新增資料方法,傳入參數為:日期、時間、標題
    long add(String date, String time, String title) {
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        ContentValues values =new ContentValues();
        values.put(DATE_FIELD, date);
        values.put(TIME_FIELD, time);
        values.put(TITLE_FIELD, title);
        long result=db.insert(TABLE_NAME, null, values);//執行新增資料
        db.close();
        return result;//回傳新資料的row ID,若為-1表示新增失敗
    }
    /*自訂修改資料方法,傳入參數為:日期、時間、標題、更新條件，無須修改則傳入null*/
    long update(String date, String time, String title,String whereClause) {
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        ContentValues values =new ContentValues();
        if(date!=null)  values.put(DATE_FIELD, date);
        if(time!=null)  values.put(TIME_FIELD, time);
        if(title!=null)  values.put(TITLE_FIELD, title);
        //執行更新資料
        long result=db.update(TABLE_NAME, values, whereClause, null);
        db.close();
        return result;//回傳更新資料筆數
    }
    //自訂刪除資料方法,傳入參數為:欲刪除資料的_id欄位值
    int delete(String _id){
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        int result=db.delete(TABLE_NAME, ID_FIELD+" ="+_id, null); //進行刪除
        db.close();
        return result;//回傳刪除筆數
    }
    //自訂查詢資料方法,傳入參數為:查詢條件、排序欄位
    Cursor getData(String whereClause, String orderBy){
        SQLiteDatabase db=this.getReadableDatabase();//取得讀寫資料表物件
        Cursor c=db.query(TABLE_NAME,
                new String[]{ID_FIELD,DATE_FIELD,TIME_FIELD,TITLE_FIELD},
                whereClause, null, null, null, orderBy); //進行查詢
        return c;
    }

}
