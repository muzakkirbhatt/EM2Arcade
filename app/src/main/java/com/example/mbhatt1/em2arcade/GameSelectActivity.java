package com.example.mbhatt1.em2arcade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class GameSelectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);
        //Onclick listeners set for the different games
        ImageButton mmGame = (ImageButton) findViewById(R.id.memoryGame);
        mmGame.setOnClickListener(this);

        ImageButton c4Game = (ImageButton) findViewById(R.id.C4Game);
        c4Game.setOnClickListener(this);

        ImageButton bjGame = (ImageButton) findViewById(R.id.blackjackGame);
        bjGame.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.memoryGame: {
                // do something
                Toast.makeText(this, "Memory Game", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.C4Game: {
                Toast.makeText(this, "Connect4 Game", Toast.LENGTH_LONG).show();
                // do something
                break;
            }
            case R.id.blackjackGame: {
                Toast.makeText(this, "BlackJack Game", Toast.LENGTH_LONG).show();
                // do something
                break;
            }

        }//switch
    }//OnClick
}
