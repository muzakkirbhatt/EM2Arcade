package com.example.mbhatt1.em2arcade;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;


/**
 * Created by Muzakkir on 3/13/2018.
 */

public class ButtonLayout extends GridLayout {
    private int side;
    public Button[][] buttons;
    private TextView texty;
    private int pattern = 0;

    public ButtonLayout(Context context, int width, int newSide, OnClickListener listener) {
        super(context);
        side = newSide;
        setColumnCount(side);
        setRowCount(side + 1);
        while (pattern == 0) {
            int num = (int) (Math.random() * 100);
            Log.d("Model", "Random Number: " + num);
            if ((num % 2) == 0) {
                pattern = 2;
            } else if ((num % 3) == 0) {
                pattern = 3;
            } else {
                pattern = 1;
            }
        }
        Log.d("Model", "Pattern: " + pattern);

        buttons = new Button[side][side];
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {

                buttons[row][col] = new Button(context);
                buttons[row][col].setOnClickListener(listener);
                buttons[row][col].setTextSize(width * .2f);
                if (pattern == 1) {
                    if (row == col) {
                        buttons[row][col].setText("X");
                    }
                    if (row != col) {
                        buttons[row][col].setText("O");
                    }
                    buttons[row][col].setTextColor(View.INVISIBLE);
                }
                if (pattern == 2) {
                    if (row <= col && row == 0) {
                        buttons[row][col].setText("O");
                    } else {
                        buttons[row][col].setText("X");
                    }
                    buttons[row][col].setTextColor(View.INVISIBLE);
                }
                if (pattern == 3) {
                    if (row > col || row == 0) {
                        buttons[row][col].setText("X");
                    } else {
                        buttons[row][col].setText("0");
                    }
                    buttons[row][col].setTextColor(View.INVISIBLE);
                }
                addView(buttons[row][col], width, width);

            }
        }

        texty = new TextView(context);
        texty.setBackgroundColor(Color.GREEN);
        texty.setTextSize(width * .15f);
        texty.setText("LETS PLAY ");
        Spec rowSpec = GridLayout.spec(side, 1);
        Spec colSpec = GridLayout.spec(0, side);
        LayoutParams lp = new LayoutParams(rowSpec, colSpec);
        texty.setLayoutParams(lp);
        addView(texty);


    }

    public boolean isButton(Button b, int row, int col) {
        return (b == buttons[row][col]);
    }

    public Button[][] getButtons() {
        return buttons;
    }

    public void setText(String text) {
        texty.setText(text);
    }

    public void setColor(int color) {
        texty.setBackgroundColor(color);
    }
}
