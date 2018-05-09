package com.example.mbhatt1.em2arcade;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConnectActivity extends AppCompatActivity {
    private ConnectFour game;
    private GridLayoutClass cfView;
    private ConnectActivity self;
    private User player;
    private DatabaseManager db;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        db = new DatabaseManager(this);
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String username = sp.getString("userName", null);
        player = db.selectByUsername(username);
        game = new ConnectFour();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / ConnectFour.y;
        ButtonHandler bh = new ButtonHandler();
        cfView = new GridLayoutClass(this, w, ConnectFour.x, ConnectFour.y, bh);
        cfView.setStatusText(game.result());
        setContentView(cfView);
    }

    public void showNewGameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("WOOOO! That was fun!");
        alert.setMessage("Wanna play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.setCancelable(false);
        setFinishOnTouchOutside(false);
        alert.show();
    }

    private class ButtonHandler implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void onClick(View v) {
            for (int row = 0; row < ConnectFour.x; row++) {
                for (int column = 0; column < ConnectFour.y; column++) {
                    if (cfView.isButton((Button) v, row, column)) {
                        int play = game.play(row, column);
                        if (play == 1) {
                            cfView.setButtonText(row, column, "X");
                        } else if (play == 2) {
                            cfView.setButtonText(row, column, "O");
                        }
                        if (game.isGameOver()) {
                            cfView.setStatusBackgroundColor(Color.GREEN);
                            cfView.setTextColor(Color.BLACK);
                            cfView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            cfView.enableButtons(false);
                            cfView.setStatusText(game.result());
                            showNewGameDialog();    // offer to play again
                        }
                    }
                }
            }
        }
    }

    private class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            if(game.whoWon() == 1){
                int wins = Integer.valueOf(player.getConnectHS()) + 1;
                player.setConnectHS(String.valueOf(wins));
                db.updateHS(player, "connectFour");
            }
            if (id == -1) /* YES button */ {
                game.resetGame();
                cfView.enableButtons(true);
                cfView.resetButtons();
                cfView.setStatusBackgroundColor(Color.BLUE);
                cfView.setTextColor(Color.WHITE);
                cfView.setStatusText(game.result());
            } else if (id == -2) // NO button
                self.finish();
        }
    }
}