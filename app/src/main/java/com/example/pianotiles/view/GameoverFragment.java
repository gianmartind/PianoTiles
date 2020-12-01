package com.example.pianotiles.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pianotiles.DBHandler;
import com.example.pianotiles.FragmentListener;
import com.example.pianotiles.R;
import com.example.pianotiles.presenter.GameoverFragmentPresenter;

public class GameoverFragment extends Fragment implements GameoverFragmentPresenter.IGameoverFragment, View.OnClickListener {
    FragmentListener fragmentListener;
    TextView score;
    Button playAgain, backMenu;
    EditText playerName;
    GameoverFragmentPresenter gameoverFragmentPresenter;
    DBHandler db;

    public GameoverFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gameover, container, false);

        this.score = view.findViewById(R.id.score);
        this.playAgain = view.findViewById(R.id.play_again);
        this.backMenu = view.findViewById(R.id.back_menu);
        this.playerName = view.findViewById(R.id.player_name);

        this.db = new DBHandler(this.getActivity());
        this.gameoverFragmentPresenter = new GameoverFragmentPresenter(this.getArguments().getInt("score", 0), this, this.db);

        this.playAgain.setOnClickListener(this);
        this.backMenu.setOnClickListener(this);

        this.gameoverFragmentPresenter.loadData();

        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fragmentListener = (FragmentListener) context;
        } else{
            throw new ClassCastException(context.toString() + " must implement FragmentListener");
        }
    }

    public static GameoverFragment newInstance(int score){
        GameoverFragment fragment = new GameoverFragment();

        Bundle args = new Bundle();
        args.putInt("score", score);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void setScore(int score) {
        this.score.setText("Your Score: " + Integer.toString(score));
    }

    @Override
    public void changePage(int page) {
        this.fragmentListener.changePage(page);
    }

    @Override
    public void onClick(View v) {
        String playerName = this.playerName.getText().toString();
        if(v == this.playAgain){
            this.gameoverFragmentPresenter.playAgain(playerName);
        } else if(v == this.backMenu){
            this.gameoverFragmentPresenter.backToMenu(playerName);
        }
    }
}
