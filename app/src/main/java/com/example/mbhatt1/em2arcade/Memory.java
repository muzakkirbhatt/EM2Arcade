package com.example.mbhatt1.em2arcade;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;


/**
 * Created by Muzakkir on 3/13/2018.
 */

public class Memory {
    public static final int SIDE = 2;
    private Button[][] buttons;
    public int buttonsFlipped = 0;

    public Memory(Button[][] buttons) {
        this.buttons = buttons;
        reset();
    }

    public void flipButton(int row, int col) {
        if (buttons[row][col].getCurrentTextColor() == Color.BLACK) {
            buttons[row][col].setTextColor(View.INVISIBLE);
            buttonsFlipped -= 1;
            if (buttonsFlipped < 0) {
                buttonsFlipped = 0;
            }
        } else {
            buttons[row][col].setTextColor(Color.BLACK);
            buttonsFlipped += 1;
        }
    }


    public void reset() {
        for (int row = 0; row < SIDE; row++) {
            for (int col = 0; col < SIDE; col++) {
                buttons[row][col].setTextColor(View.INVISIBLE);
                buttonsFlipped = 0;
            }
        }
    }


    public int checkWin() {
        String[] arr = new String[2];
        int win = 0;
        for (int row = 0; row < SIDE; row++) {
            for (int col = 0; col < SIDE; col++) {
                if (buttons[row][col].getCurrentTextColor() == Color.BLACK) {
                    String text = this.getText(row, col);
                    if (arr[0] == null) {
                        arr[0] = text;
                    } else if (arr[0] != null && arr[1] == null) {
                        arr[1] = text;
                    }
                }
            }
        }

        if (arr[0] != null && arr[1] != null) {
            if (arr[0].equalsIgnoreCase(arr[1])) {
                win = 1;
            }
        }
        return win;
    }

    public String getText(int row, int col) {
        String text = buttons[row][col].getText().toString();
        return text;
    }
}