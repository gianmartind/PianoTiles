package com.example.pianotiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.pianotiles.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements FragmentListener{
    ActivityMainBinding bind;
    FragmentManager fragmentManager;
    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bind = ActivityMainBinding.inflate(getLayoutInflater());
        View view = this.bind.getRoot();
        setContentView(view);

        this.fragmentManager = this.getSupportFragmentManager();
        this.mainFragment = new MainFragment();

        changePage(1);

    }

    @Override
    public void changePage(int i) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(i == 1) {
            ft.replace(R.id.fragment_container, this.mainFragment).addToBackStack(null);
        }
        ft.commit();
    }

    @Override
    public void closeApplication() {

    }
}