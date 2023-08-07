package com.mark.myapplication.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("MyDBOpenHelper", "MyDBOpenHelper onCreate: ");
        String createSQL="create table "+DBAdapter.DB_TABLE+" ("+DBAdapter.KEY_ID+
                " integer primary key autoincrement,"
                +DBAdapter.KEY_NAME + " text not null,"
                +DBAdapter.KEY_URL + " text not null)";
        db.execSQL(createSQL); //执行创建语句
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DBAdapter.DB_TABLE); //一般情况下不能直接删除数据,先保存...
        onCreate(db);
        System.out.println(oldVersion+"--"+newVersion);
    }
}
