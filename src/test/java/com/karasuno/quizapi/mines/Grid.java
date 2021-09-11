package com.karasuno.quizapi.mines;

import java.util.Random;

public class Grid {
    private int row;
    private int col;
    private int numOfMines;
    public boolean[][] board;
    public boolean[][] reveal;
    public int[][] data;

    public Grid(int row, int col, int mines) {
        this.row = row;
        this.col = col;
        this.numOfMines = mines;

        this.board = new boolean[this.row][this.col];
        this.reveal = new boolean[this.row][this.col];
        this.data = new int[this.row][this.col];

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                board[i][j] = false;
                reveal[i][j] = false;
                data[i][j] = 0;
            }
        }
        randomFillGrid();
    }

    private void randomFillGrid(){
        Random r = new Random();
        for(int i=0;i<numOfMines;i++){
            int h = r.nextInt(row);
            int w = r.nextInt(col);
            // place mine in random empty cell
            while(!board[h][w]){
                board[h][w] = true;
            }
        }
    }

    // print grid with hidden or reveal cell
    public void displayGrid(){
        System.out.print("There are total of ");
        System.out.print(numOfMines);
        System.out.println(" mines in the mine field");
        System.out.print(" ");
        for(int i=0;i<col;i++){
            System.out.print(i+1);
            System.out.print(" ");
        }
        System.out.println();
        for(int j=0;j<row;j++){
            System.out.print((j+1));
            for(int k=0;k<col;k++){
                System.out.print(" ");
                if(reveal[j][k]){
                    if(data[j][k]!=0){
                        System.out.print(data[j][k]);
                    }
                    else{
                        System.out.print(" ");
                    }
                }
                else{
                    System.out.print("*");
                }
            }
            System.out.println();
        }
    }
    // print grid with location of mine revealed
    public void displayGridData(){
        System.out.print("There are total of ");
        System.out.print(numOfMines);
        System.out.println(" mines in the mine field");
        System.out.print(" ");
        for(int i=0;i<col;i++){
            System.out.print(i+1);
            System.out.print(" ");
        }
        System.out.println();
        for(int j=0;j<row;j++){
            System.out.print((j+1));
            for(int k=0;k<col;k++){
                System.out.print(" ");
                if(board[j][k]){
                    System.out.print("*");
                }
                else{
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
    // getters
    public int getHeight(){
        return row;
    }
    public int getWidth(){
        return col;
    }
    public int getNumOfMines() {
        return numOfMines;
    }
    // returns number of not revealed cell
    public int getRemainingCell(){
        int cells = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(!reveal[i][j]){
                    cells++;
                }
            }
        }
        return cells;
    }
}
