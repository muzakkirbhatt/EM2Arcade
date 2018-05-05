package com.example.mbhatt1.em2arcade;

/**
 * Created by Muzakkir on 2/24/2018.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class GridLayoutClass extends GridLayout {
    private int rowNum;
    private int colNum;
    private Button[][] buttons;
    private TextView status;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public GridLayoutClass(Context context, int width, int rowNum, int colNum,
                           OnClickListener listener) {
        super(context);
        this.rowNum = rowNum;
        this.colNum = colNum;
        // Set # of rows and columns of this GridLayout
        setColumnCount(colNum);
        setRowCount(colNum);
        setBackgroundColor(Color.BLUE);

        // Create the buttons and add them to this GridLayout
        buttons = new Button[rowNum][colNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                buttons[row][col] = new Button(context);
                buttons[row][col].setTextSize((int) (width * .2));
                buttons[row][col].setOnClickListener(listener);
                addView(buttons[row][col], width, width);

            }
        }

        // set up layout parameters of last row of gridLayout
        status = new TextView(context);
        Spec rowSpec = GridLayout.spec(rowNum, 1);
        Spec columnSpec = GridLayout.spec(0, colNum);
        LayoutParams lpStatus
                = new LayoutParams(rowSpec, columnSpec);
        status.setLayoutParams(lpStatus);

        // set up status' characteristics
        status.setWidth(colNum * width);
        status.setHeight(rowNum * width);
        status.setGravity(Gravity.CENTER_HORIZONTAL);
        status.setBackgroundColor(Color.BLUE);
        status.setTextColor(Color.WHITE);
        status.setTextSize((int) (width / 10));

        addView(status);

    }

    public void setStatusText(String text) {
        status.setText(text);
    }

    public void setStatusBackgroundColor(int color) {
        status.setBackgroundColor(color);
    }

    public void setTextColor(int color) {
        status.setTextColor(color);
    }

    public void setButtonText(int row, int column, String text) {
        for (row = ConnectFour.x - 1; row > -1; row--) {
            if (!buttons[row][column].getText().toString().contains("X") &&
                    !buttons[row][column].getText().toString().contains("O")) {

                break;
            }
        }

        buttons[row][column].setText(text);

        if (text.contains("X")) {
            buttons[row][column].setTextColor(Color.RED);
            buttons[row][column].getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        } else if (text.contains("O")) {
            buttons[row][column].setTextColor(Color.YELLOW);
            buttons[row][column].getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public boolean isButton(Button b, int row, int column) {
        return (b == buttons[row][column]);
    }

    public void resetButtons() {
        for (int row = 0; row < rowNum; row++)
            for (int col = 0; col < colNum; col++) {
                buttons[row][col].setText("");
                buttons[row][col].getBackground().clearColorFilter();
            }
    }

    public void enableButtons(boolean enabled) {
        for (int row = 0; row < rowNum; row++)
            for (int col = 0; col < colNum; col++)
                buttons[row][col].setEnabled(enabled);
    }
}
