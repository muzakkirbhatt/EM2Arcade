package com.example.mbhatt1.em2arcade;

import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoardActivity extends AppCompatActivity {

    private DatabaseManager db;
    private String game;
    TableLayout tl;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Configuration config = getResources().getConfiguration();
        Bundle bundle = getIntent().getExtras();
        game = bundle.getString("game");
        db = new DatabaseManager(this);
        tl = findViewById(R.id.tableLayout);
        this.populate();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void populate() {
        ArrayList<User> users = db.selectAll();
        if (users != null) {
            if (users.size() > 1) {
                if (game.equalsIgnoreCase("connectFour")) {
                    Collections.sort(users, (user1, user2) -> Integer.valueOf(user2.getConnectHS()) - Integer.valueOf(user1.getConnectHS()));
                } else if (game.equalsIgnoreCase("memoryGame")) {
                    Collections.sort(users, (user1, user2) -> Integer.valueOf(user2.getMemoryHS()) - Integer.valueOf(user1.getMemoryHS()));
                } else if (game.equalsIgnoreCase("blackJack")) {
                    Collections.sort(users, (user1, user2) -> Integer.valueOf(user2.getBlackjackHS()) - Integer.valueOf(user1.getBlackjackHS()));
                }
            }
            for (int i = 0; i < users.size(); i++) {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView rankView = new TextView(this);
                TextView userNameView = new TextView(this);
                TextView scoreView = new TextView(this);

                int rank = i + 1;
                rankView.setText("#" + rank);

                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
                layoutParams.setMargins(120, 60, 120, 60);

                rankView.setLayoutParams(layoutParams);
                userNameView.setLayoutParams(layoutParams);
                scoreView.setLayoutParams(layoutParams);

                userNameView.setText(users.get(i).getUsername());

                if (game.equalsIgnoreCase("connectFour")) {
                    scoreView.setText(users.get(i).getConnectHS());
                } else if (game.equalsIgnoreCase("memoryGame")) {
                    scoreView.setText(users.get(i).getMemoryHS());
                } else if (game.equalsIgnoreCase("blackJack")) {
                    scoreView.setText(users.get(i).getBlackjackHS());
                }

                rankView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                userNameView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                scoreView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                tr.addView(rankView);
                tr.addView(userNameView);
                tr.addView(scoreView);

                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_leaderboard_landscape);
            tl = findViewById(R.id.tableLayout);
            this.populate();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_leaderboard);
            tl = findViewById(R.id.tableLayout);
            this.populate();
        }
    }

    public void goBack(View view) {
        this.finish();
    }
}
