package com.example.lotus.testsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DBManager dbManager;//管理进行数据库操作
    private List<WordInfor> wordInforList = new ArrayList<WordInfor>();//存储所有单词对象

    private WebView webView;//用于加载单词页面控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 初始化DBManager
        dbManager = new DBManager(getApplicationContext());

        //查询所有单词，并进行存储
        wordInforList.clear();
        wordInforList = dbManager.searchInfroAllWord();

        //实例化WebView对象
        webView = (WebView) this.findViewById(R.id.home_webView);
        //设置WebView属性，能够执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //用于与页面交互
        webView.addJavascriptInterface( new MyJavaScriptInterface(), "myJavaScriptInterface" );
        try {
            //设置打开的页面地址
            webView.loadUrl("file:///android_asset/homepage.html");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //用于与页面交互的类
    private  final  class MyJavaScriptInterface {

        //显示点击了表格第几行
        @JavascriptInterface
        public void showWordIndex(String index){
            if( !TextUtils.isEmpty( index ) ){
                WordInfor wordInfor = wordInforList.get( Integer.parseInt( index));
                Toast.makeText( getApplicationContext(), wordInfor.toString() ,Toast.LENGTH_SHORT).show();
            }
        }

        //查询所有单词，以json实现webview与js之间的数据交互
        @JavascriptInterface
        public String showAllWord() {
            JSONObject map;
            JSONArray array = new JSONArray();

            //查询所有单词，并进行存储
            wordInforList.clear();
            wordInforList = dbManager.searchInfroAllWord();
            try {
                for (int i = 0; i < wordInforList.size(); i++) {
                    map = new JSONObject();
                    map.put("word", wordInforList.get(i).getWord());
                    map.put("mean", wordInforList.get(i).getMean());
                    map.put("sentence", wordInforList.get(i).getSentence());
                    array.put(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array.toString();
        }

        //插入指定单词、词意、例句
        @JavascriptInterface
        public void insertWord(String word, String mean, String sentence) {
            dbManager.insertWord(word, mean, sentence);
        }

        //删除指定单词
        @JavascriptInterface
        public void deleteWord(String word) {
            dbManager.deleteWord( word);
        }

        //更新指定单词的例句
        @JavascriptInterface
        public void updateWord(String word, String sentence) {
            dbManager.updateWordInfor(word, sentence);
        }

        //查询指定单词的详细信息
        @JavascriptInterface
        public String searchThisWord(String word) {
            JSONObject map;
            JSONArray array = new JSONArray();
            wordInforList.clear();
            wordInforList = dbManager.searchInfroThisWord( word );
            try {
                for (int i = 0; i < wordInforList.size(); i++) {
                    map = new JSONObject();
                    map.put("word", wordInforList.get(i).getWord());
                    map.put("mean", wordInforList.get(i).getMean());
                    map.put("sentence", wordInforList.get(i).getSentence());
                    array.put(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array.toString();
        }

    }

}



