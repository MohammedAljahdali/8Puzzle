package com.mohammed;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleBoard {

    public char[][] board;
    public int row;
    public int col;
    public int cost;
    public int level;

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

    public PuzzleBoard[] move() {
        int possibleMoves = getPossibleMoves();
        PuzzleBoard[] boards = new PuzzleBoard[possibleMoves];
        possibleMoves--;
        char temp;
        if (col != 0) {
            int newRow = row;
            int newCol = col;
            char[][] newBoard = new char[3][3];
            for (int i = 0; i < 3; i++)
                newBoard[i] = Arrays.copyOf(board[i],3);
            newCol--;
            temp = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = '-';
            newBoard[newRow][col] = temp;
            boards[possibleMoves--] = new PuzzleBoard(newBoard,newRow, newCol);
        }
        if (col != 2) {
            int newRow = row;
            int newCol = col;
            char[][] newBoard = new char[3][3];
            for (int i = 0; i < 3; i++)
                newBoard[i] = Arrays.copyOf(board[i],3);
            newCol++;
            temp = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = '-';
            newBoard[newRow][col] = temp;
            boards[possibleMoves--] = new PuzzleBoard(newBoard,newRow, newCol);
        }
        if (row != 0) {
            int newRow = row;
            int newCol = col;
            char[][] newBoard = new char[3][3];
            for (int i = 0; i < 3; i++)
                newBoard[i] = Arrays.copyOf(board[i],3);
            newRow--;
            temp = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = '-';
            newBoard[row][newCol] = temp;
            boards[possibleMoves--] = new PuzzleBoard(newBoard,newRow, newCol);
        }
        if (row != 2) {
            int newRow = row;
            int newCol = col;
            char[][] newBoard = new char[3][3];
            for (int i = 0; i < 3; i++)
                newBoard[i] = Arrays.copyOf(board[i],3);
            newRow++;
            temp = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = '-';
            newBoard[row][newCol] = temp;
            boards[possibleMoves--] = new PuzzleBoard(newBoard,newRow, newCol);
        }
        return boards;
    }

    public int getPossibleMoves() {
        int possibleMoves = 0;
        if (col != 0)
            possibleMoves++;
        if (col != 2)
            possibleMoves++;
        if (row != 0)
            possibleMoves++;
        if (row != 2)
            possibleMoves++;
        return possibleMoves;
    }

}
