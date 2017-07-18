package com.example.lotus.testsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 一个抽象类，来管理数据库的创建和版本的管理。
 * 要使用它必须实现它的nCreate(SQLiteDatabase)，onUpgrade(SQLiteDatabase, int, int)方法
 * Created by Lotus' on 2017/4/26.
 */

public class MyDatebaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;//数据库版本号
    private static final String DATABASE_NAME = "DBsas.db";//数据库名字

    //数据库表格表明
    public static final String TABLE_Word = "WordTable";//单词表的表名

    public MyDatebaseHelper(Context context){
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super( context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    //当数据库第一次被建立的时候被执行，例如创建表,初始化数据等
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建单词表，并插入数据
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_Word + " (word vachar(50),mean vachar(100),sentence vachar(300 ))");
        for( int i = 0; i< 10 ;i++){
            db.execSQL("insert into "+ TABLE_Word + " values ( '"+ i + "english', '英语', 'english book') ");
        }
    }

    //当数据库需要被更新的时候执行，例如删除久表，创建新表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}


