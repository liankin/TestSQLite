package com.example.lotus.testsqlite;

public class WordInfor {

    private String word;//单词
    private String mean;//词意
    private String sentence;//例句

    public WordInfor ( String word, String mean, String sentence ){
        this.word = word;
        this.mean = mean;
        this.sentence = sentence;
    }

    public String getWord(){
        return this.word;
    }

    public String getMean(){
        return  this.mean;
    }

    public String getSentence(){
        return  this.sentence;
    }

    public String toString(){
        return this.word+" "+this.mean+" "+this.sentence;
    }
}

