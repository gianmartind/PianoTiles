package com.example.pianotiles.presenter;

import com.example.pianotiles.DBHandler;
import com.example.pianotiles.model.Score;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GameoverFragmentPresenter {
    DBHandler db;
    int score;
    IGameoverFragment ui;

    public GameoverFragmentPresenter(int score, IGameoverFragment ui, DBHandler db){
        this.score = score;
        this.ui = ui;
        this.db = db;
    }

    public void loadData(){
        this.ui.setScore(this.score);
    }

    public void backToMenu(String playerName){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        Score score = new Score(0, this.score, playerName, strDate);
        this.db.addRecord(score);
        this.ui.changePage(0);
    }

    public void playAgain(String playerName){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        Score score = new Score(0, this.score, playerName, strDate);
        this.db.addRecord(score);
        this.ui.changePage(1);
    }

    public interface IGameoverFragment{
        void setScore(int score);
        void changePage(int page);
    }
}
