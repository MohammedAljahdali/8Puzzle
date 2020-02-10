package com.mohammed;

import java.io.PrintStream;
import java.util.Arrays;

public class PuzzleBoard {

    private char[][] board;
    public int row;
    public int col;

    PuzzleBoard(char[][] board, int row, int col) {
        char[][] x = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                x[i][j] = board[i][j];
        this.board = x;
        this.row = row;
        this.col = col;
    }

    PuzzleBoard(PuzzleBoard oldState, char movement) {
        char[][] x = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                x[i][j] = oldState.board[i][j];
        this.board = x;
        this.row = oldState.row;
        this.col = oldState.col;
        char temp;
        switch (movement) {
            case 'l':
                if (col != 0) {
                    col--;
                    temp = board[row][col];
                    board[row][col] = '-';
                    board[row][oldState.col] = temp;
                }
                break;
            case 'r':
                if (col != 2) {
                    col++;
                    temp = board[row][col];
                    board[row][col] = '-';
                    board[row][oldState.col] = temp;
                }
                break;
            case 'u':
                if (row != 0) {
                    row--;
                    temp = board[row][col];
                    board[row][col] = '-';
                    board[oldState.row][col] = temp;
                }
                break;
            case 'd':
                if (row != 2) {
                    row++;
                    temp = board[row][col];
                    board[row][col] = '-';
                    board[oldState.row][col] = temp;
                }
                break;
        }
    }

    public boolean equals(PuzzleBoard state) {
        if (state == null) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!Arrays.equals(board[i], state.board[i])) {
                return false;
            }
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print(board[i][j]+" ");
            System.out.println();
        }
    }

    public void printOnFile(PrintStream ps) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                ps.print(board[i][j]+" ");
            ps.println();
        }
    }

}
