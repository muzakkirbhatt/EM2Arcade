package com.example.mbhatt1.em2arcade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class GameSelectActivity extends AppCompatActivity {

    public User user;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);
        db = new DatabaseManager(this);
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String username = sp.getString("userName", null);
        user = db.selectByUsername(username);

    }

    public void selectGame(View v) {
        switch (v.getId()) {
            case R.id.memoryGame: {
                Intent mg = new Intent(this, MemoryActivity.class);
                startActivity(mg);
                break;
            }
            case R.id.C4Game: {
                Intent c4 = new Intent(this, ConnectActivity.class);
                startActivity(c4);
                break;
            }
            case R.id.blackjackGame: {
                Intent blkj = new Intent(this, BlackJackActivity.class);
                startActivity(blkj);
                break;
            }

        }
    }

    public void leaderBoards(View v) {
        String game = "";
        RadioButton rb;
        rb = findViewById(v.getId());
        switch (v.getId()) {
            case R.id.MMRadio: {
                game = "memoryGame";
                break;
            }
            case R.id.C4Radio: {
                game = "connectFour";
                break;
            }
            case R.id.BJRadio: {
                game = "blackJack";
                break;
            }
        }
        Intent leaderBoard = new Intent(this, LeaderBoardActivity.class);
        leaderBoard.putExtra("game", game);
        rb.setChecked(false);
        startActivity(leaderBoard);
    }


}
