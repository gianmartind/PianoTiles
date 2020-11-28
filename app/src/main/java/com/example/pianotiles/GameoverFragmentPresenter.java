package com.example.pianotiles;

public class GameoverFragmentPresenter {
    int score;
    IGameoverFragment ui;

    public GameoverFragmentPresenter(int score, IGameoverFragment ui){
        this.score = score;
        this.ui = ui;
    }

    public void loadData(){
        this.ui.setScore(this.score);
    }

    public void backToMenu(){
        this.ui.changePage(0);
    }

    public void playAgain(){
        this.ui.changePage(1);
    }

    public interface IGameoverFragment{
        void setScore(int score);
        void changePage(int page);
    }
}
