package com.example.mbhatt1.em2arcade;

/**
 * Created by Muzakkir on 2/24/2018.
 */

public class ConnectFour {
    public static final int x = 6;
    public static final int y = 7;
    private int turn;
    private int[][] game;

    private int Player1Wins = 0;
    private int Player2Wins = 0;
    private int TieGame = 0;

    public ConnectFour() {
        game = new int[x][y];
        resetGame();
    }

    public int play(int row, int col) {
        int currentTurn = turn;
        if (row >= 0 && col >= 0 && row < (x) && col < y
                && game[0][col] == 0) {

            int r;

            for (r = ConnectFour.x - 1; r > -1; r--) {
                if (game[r][col] == 0) {
                    game[r][col] = turn;

                    break;
                }
            }

            if (turn == 1) {
                turn = 2;
            } else if (turn == 2) {
                turn = 1;
            }
            return currentTurn;
        } else
            return 0;
    }

    public int whoWon() {
        int rows = checkRows();
        if (rows > 0)
            return rows;
        int columns = checkColumns();
        if (columns > 0)
            return columns;
        int diagonals = checkDiagonals();
        if (diagonals > 0)
            return diagonals;
        return 0;
    }

    protected int checkRows() {
        for (int r = 0; r < x; r++) {
            int count = 0;
            for (int c = 1; c < y; c++) {
                if ((game[r][c] != 0 && c != x - 1) &&
                        game[r][c] == game[r][c - 1]) {
                    count++;
                } else {
                    count = 1;
                }
                if (count >= 4) {
                    return game[r][c];
                }

            }
        }

        return 0;
    }

    protected int checkColumns() {
        for (int c = 0; c < y; c++) {
            int count = 0;

            for (int r = 1; r < x; r++) {
                if ((game[r][c] != 0 /*&& r != 0*/) &&
                        game[r][c] == game[r - 1][c]) {
                    count++;

                } else {
                    count = 1;
                }
                if (count >= 4) {
                    return game[r][c];
                }

            }
        }
        return 0;
    }

    protected int checkDiagonals() {
        final int maxX = x;
        final int maxY = y;

        int[][] directions = {{1, 0}, {1, -1}, {1, 1}, {0, 1}};
        for (int[] d : directions) {
            int dx = d[0];
            int dy = d[1];
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    int lastX = x + 3 * dx;
                    int lastY = y + 3 * dy;
                    if (0 <= lastX && lastX < maxX && 0 <= lastY && lastY < maxY) {
                        int w = game[x][y];
                        if (w != 0 && w == game[x + dx][y + dy]
                                && w == game[x + 2 * dx][y + 2 * dy]
                                && w == game[lastX][lastY]) {
                            return w;
                        }
                    }
                }
            }
        }
        return 0; // no winner
    }


    public boolean canNotPlay() {
        boolean result = true;
        for (int col = 0; col < y; col++)
            if (game[0][col] == 0)
                result = false;
        return result;
    }

    public boolean isGameOver() {
        return canNotPlay() || (whoWon() > 0);
    }

    public void resetGame() {
        for (int row = 0; row < x; row++)
            for (int col = 0; col < y; col++)
                game[row][col] = 0;
        turn = 1;
    }

    public String result() {
        if (whoWon() > 0) {
            if (whoWon() == 1) {
                Player1Wins++;
            } else if (whoWon() == 2) {
                Player2Wins++;
            }

            return "\n\nPlayer " + whoWon() + " won";
        } else if (canNotPlay())
            return "Tie Game\n\n\nPlayer 1 Wins: " + Player1Wins + "\nPlayer 2 Wins: " + Player2Wins + "\nTie: " + TieGame++;
        else
            return "Welcome to Connect 4.\n\n Touch the row to drop your piece in.\n\nPlayer 1 Score: " + Player1Wins + "\n\nPlayer 2 Score: " + Player2Wins + "\n\nTie Games: " + TieGame;
    }
}