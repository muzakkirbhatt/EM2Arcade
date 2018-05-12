package com.example.mbhatt1.em2arcade;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemoryActivity extends AppCompatActivity {
    private ButtonLayout buttonLayout;
    private Memory memory;
    private MemoryActivity self;
    private User player;
    private DatabaseManager db;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.create();
        self = this;
        db = new DatabaseManager(this);
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String username = sp.getString("userName", null);
        player = db.selectByUsername(username);
        setContentView(buttonLayout);
    }

    public void create() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / (memory.SIDE);
        buttonHandler bh = new buttonHandler();
        buttonLayout = new ButtonLayout(this, w, memory.SIDE, bh);
        memory = new Memory(buttonLayout.getButtons());
    }

    public class buttonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int win = 0;
            for (int row = 0; row < memory.SIDE; row++) {
                for (int col = 0; col < memory.SIDE; col++) {
                    if (buttonLayout.isButton((Button) view, row, col)) {
                        if (memory.buttonsFlipped < 2) {
                            memory.flipButton(row, col);
                        }
                        if (memory.buttonsFlipped == 2 && win == 0) {
                            win = memory.checkWin();
                            if (win == 1) {
                                int wins = Integer.valueOf(player.getMemoryHS()) + 1;
                                player.setMemoryHS(String.valueOf(wins));
                                db.updateHS(player, "memoryGame");
                                mp = MediaPlayer.create(getApplicationContext(), R.raw.win);
                                mp.start();
                                buttonLayout.setText("  YOU WIN   ");
                                new AlertDialog.Builder(self)
                                        .setTitle("YOU WON")
                                        .setMessage("Wanna play again?")
                                        .setCancelable(true)
                                        .setPositiveButton("HELL YEAH!", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                MemoryActivity main = MemoryActivity.this;
                                                main.create();
                                                main.setContentView(buttonLayout);
                                            }
                                        })
                                        .setNegativeButton("Nahh", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                self.finish();
                                            }
                                        }).show();
                            } else {
                                new CountDownTimer(500, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        memory.reset();
                                    }
                                }.start();
                            }
                        }
                    }
                }
            }
        }
    }

}