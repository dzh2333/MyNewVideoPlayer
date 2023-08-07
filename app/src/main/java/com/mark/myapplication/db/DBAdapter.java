package com.mark.myapplication.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mark.myapplication.bean.MediaBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAdapter {
    //数据库基本信息
    private static final String DB_NAME="media.db";   //数据库名
    public static final String DB_TABLE="media";  //表名
    private static final int   DB_VERSION = 1;	  //数据库版本
    //字段名称
    public static  final  String KEY_ID="id";
    public static  final  String KEY_NAME="name";
    public static  final  String KEY_URL="url";

    private SQLiteDatabase db=null;  //数据库对象
    private Context context; //记住由构造函数传入的上下文对象

    //数据库辅助操作对象
    private MyDBOpenHelper helper;

    public DBAdapter(Context context){
        this.context=context;  //记住由构造函数传入的上下文对象
        //若此处的数据库版本不同于系统文件中实际的版本,则会触发升级动作
    }

    //创建/打开数据库
    public boolean createDB(){
        helper=new MyDBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try{
            db=helper.getWritableDatabase();
        }catch(Exception e){
            return false;
        }
        return true;
    }

    //添加
    public boolean insert(MediaBean mediaBean){
        ContentValues cv=new ContentValues();

        //设置插入的字段数值----对于自增的字段不需要设置
        cv.put(KEY_NAME, mediaBean.getName());
        cv.put(KEY_URL, mediaBean.getUrl());
        if (db.insert(DB_TABLE, null, cv)==-1)
            return false;

        return true;
    }

    //查询数据
    public List<MediaBean> query(){
        List<MediaBean> list = new ArrayList<>(); //结果列表

        //查询---可以使用占位符--SQL中的?对应后面字符串数组中的字符串,此处不用,第3个参数设置为null
        Cursor cursor=db.query(DB_TABLE,new String[]{KEY_ID,KEY_NAME,KEY_URL},
                null,null,null,null,null); //如果要查询的表不存在则会产生异常
        if (cursor.getCount()!=0){//查询结果不为空则依次查询并取值
            while (cursor.moveToNext()){ //循环取出游标中的查询结果
                //取出本行各列的值
                @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
                @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
                @SuppressLint("Range") String url=cursor.getString(cursor.getColumnIndex(KEY_URL));

                MediaBean mediaBean = new MediaBean(name);
                mediaBean.setUrl(url);
                mediaBean.setId(id);

                list.add(mediaBean);
            } //继续取下一行
        }

        cursor.close(); //关闭游标

        return list;
    }

    //修改
    public long updateData(long id,MediaBean mediaBean){
        ContentValues cv=new ContentValues();

        cv.put(KEY_NAME, mediaBean.getName());
        cv.put(KEY_URL, mediaBean.getUrl());

        return db.update(DB_TABLE,cv,KEY_ID+"="+id,null);
    }

    //按id删除
    public long delete(long id){
        return db.delete(DB_TABLE, KEY_ID+"="+id,null);
    }

    //删除全部数据
    public long delete(){
        return db.delete(DB_TABLE, null,null);
    }

//    public MediaBean[] getData(long age){
//        Cursor results = db.query(DB_TABLE,new String[]{KEY_ID,KEY_NAME,KEY_URL},
//                KEY_AGE+">="+age,null,null,null,null);
//        return ConvertToPeople(results);
//    }

    @SuppressLint("Range")
    private MediaBean[] ConvertToPeople(Cursor cursor){
        //获取数据的条数
        int resultCounts = cursor.getCount();
        //如果没有数据，则返回null
        if (resultCounts == 0 || !cursor.moveToFirst())
            return null;
        //定义元素类型为People的列表对象，共有resultCounts个元素
        MediaBean[] mediaBeans = new MediaBean[resultCounts];
        //将获得的数据逐条赋值给每个People对象peoples[i]
        for(int i = 0;i < resultCounts; i++){
            mediaBeans[i] = new MediaBean(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            mediaBeans[i].setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
            //将指针移到下一条数据
            cursor.moveToNext();
        }
        return mediaBeans;
    }

    //关闭数据库对象
    public void close(){
        if (db!=null){
            db.close();
            db=null;
        }
    }

    //完成事务
    public  void execTransaction(boolean rollback){
        db.beginTransaction();  //开始事务
        delete(); //全部删除数据
        if (!rollback) //模拟检测条件，如果不回滚则设置事务完成,在结束事务时会正确提交
            db.setTransactionSuccessful(); //如不执行此项,则在结束事务时会回滚事务
        db.endTransaction(); //结束事务
    }

}