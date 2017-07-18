package com.example.lotus.testsqlite;

import android.content.ContentValues;
import android.text.TextUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**Android提供了一个名为SQLiteDatabase的类，
 * 该类封装了一些操作数据库的API，使用该类可以完成对数据进行添加(Create)、查询(Retrieve)、
 * 更新(Update)和删除(Delete)操作（这些操作简称为CRUD）。
 * execSQL()方法可以执行insert、delete、update和CREATE TABLE之类有更改行为的SQL语句； rawQuery()方法用于执行select语句
 * Created by Lotus' on 2017/4/26.
 */

public class DBManager {

    private MyDatebaseHelper myDatebaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String TABLE_Word = "WordTable";//单词表的表名

    public DBManager(Context context){
        myDatebaseHelper = new MyDatebaseHelper(context);
        // 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,mFactory);
        // 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        sqLiteDatabase = myDatebaseHelper.getWritableDatabase();
    }

    //查找所有单词数据
    public  List<WordInfor> searchInfroAllWord(){
        List<WordInfor> wordInforList = new ArrayList<WordInfor>();
        Cursor cursor = sqLiteDatabase.query( TABLE_Word, null, null, null, null, null, null);
        if( cursor.moveToFirst()){
            do{
                //存数据进入wordInforList
                String word = cursor.getString( cursor.getColumnIndex("word") );
                String mean = cursor.getString( cursor.getColumnIndex("mean") );
                String sentence = cursor.getString( cursor.getColumnIndex("sentence") );
                WordInfor wordInfor = new WordInfor( word, mean, sentence );
                wordInforList.add( wordInfor );
                //moveToNext()方法可以将游标从当前行移动到下一行，如果已经移过了结果集的最后一行，返回结果为false，否则为true
            }while ( cursor.moveToNext() );
        }
        cursor.close();
        return wordInforList;
    }

    //按输入单词查找
    public  List<WordInfor> searchInfroThisWord( String inputWord ){

        List<WordInfor> wordInforList = new ArrayList<WordInfor>();
        if( TextUtils.isEmpty( inputWord)){
            return wordInforList;
        }
        String sql = "select * from "+TABLE_Word+" where word like '%"+inputWord+"%'";
        Cursor cursor = sqLiteDatabase.rawQuery( sql,null);
        //Cursor cursor = sqLiteDatabase.query( TABLE_Word, null, "word like ?", new String[]{"%"+inputWord+"%"}, null, null, null);
        //cursor.getCount()可以获得cursor的行数int型
        if( cursor.moveToFirst()){
            do{
                //存数据进入wordInforList
                String word = cursor.getString( cursor.getColumnIndex("word") );
                String mean = cursor.getString( cursor.getColumnIndex("mean") );
                String sentence = cursor.getString( cursor.getColumnIndex("sentence") );
                WordInfor wordInfor = new WordInfor( word, mean, sentence );
                wordInforList.add( wordInfor );
                //moveToNext()方法可以将游标从当前行移动到下一行，如果已经移过了结果集的最后一行，返回结果为false，否则为true
            }while ( cursor.moveToNext() );
        }
        cursor.close();
        return wordInforList;
    }

    //增加一个单词
    public void insertWord( String word, String mean, String sentence){
        if( !TextUtils.isEmpty( word) && !TextUtils.isEmpty( mean) && !TextUtils.isEmpty( sentence) ){
            sqLiteDatabase.execSQL("insert into "+TABLE_Word+" values('"+word+"','"+mean+"','"+sentence+"')");
        }
    }

    //删除一个单词
    public void deleteWord( String word){
        if( !TextUtils.isEmpty( word) ){
            sqLiteDatabase.delete( TABLE_Word, "word"+"=?",new String[]{word} );
        }
    }

    //修改一个单词例句
    public void updateWordInfor( String word, String sentence  ){
        if( !TextUtils.isEmpty( word)  && !TextUtils.isEmpty( sentence) ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("sentence", sentence);
            sqLiteDatabase.update( TABLE_Word,contentValues,"word"+"=?",new String[]{word} );
        }
    }

    //释放数据库资源
    public void closeDB(){
        sqLiteDatabase.close();
    }

}

