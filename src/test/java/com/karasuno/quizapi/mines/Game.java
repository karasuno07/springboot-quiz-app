package com.karasuno.quizapi.mines;

import java.util.Scanner;

public class Game {

    private Grid grid;
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Game().start();
    }

    public void start() {
        try {
            int height = 0, width = 0; // declare height, width of games's board
            do {
                // ask user to set grid size
                System.out.println("Enter the height of grid: ");
                do {
                    height = scanner.nextInt();

                    if (height < 1) System.out.println("Input must be greater than 0");
                } while (height < 1);

                System.out.println("Enter the width of grid: ");
                do {
                    width = scanner.nextInt();

                    if (width < 1) System.out.println("Input must be greater than 0");
                } while (width < 1);

                // while total cells less than or equal number of mines (10), ask user input again
                if (!isValidGame(height, width, 10)) System.out.println("Invalid Game, please try again!");
            } while (!isValidGame(height, width, 10));

            grid = new Grid(height, width, 10);
            gameLoop();
        } catch (Exception e) {
            scanner.nextLine(); // clear buffer
            // if user input non-number characters, swallow exception and ask input again
            System.out.println("Please enter a valid number");
            System.out.println("-----------------------------");
            start();
        }
    }

    private boolean isValidGame(int row, int col, int mines) {
        int cells = row * col;
        return cells > mines;
    }

    private void gameLoop() {
        while (true) {
            grid.displayGrid();
            // ask user to input the cell to check
            System.out.println("Pick a spot to check for a mine.");
            System.out.println("First enter the row number then column number: ");

            int row;
            do {
                row = scanner.nextInt();
                if (row < 1 || row > grid.getWidth()) System.out.println("Please enter a valid row number");
            } while (row < 1 || row > grid.getHeight());

            int col;
            do {
                col = scanner.nextInt();
                if (col < 1 || col > grid.getWidth()) System.out.println("Please enter a valid column number");
            } while (col < 1 || col > grid.getWidth());

            // call checkLocation() method passing the input got from user
//            height = height - 1;
//            width = width - 1;
            if (checkLocation(--row, --col)) { // replace height = height - 1, width = width = -1
                System.out.println("GameOver!");
                grid.displayGridData();
                break;
            };

            if (grid.getNumOfMines() == grid.getRemainingCell()) {
                System.out.println("You Win!");
                break;
            }
        }
    }

    private boolean checkLocation(int row, int col) {
        if (grid.board[row][col]) {
            return true;
        } else {
            // if location does not have mine then call clearBanks() method
            clearBanks(row, col);
            return false;
        }
    }

    // reveal all of the cells adjacent to the location
    private void clearBanks(int row, int col) {
        // if adjacent cells has mine adjacent to it then number is put in to cell
        // the number is total adjacent mines
        int mines = numberOfMines(row, col);
        if (mines > 0) {
            grid.reveal[row][col] = true;
            grid.data[row][col] = mines;
        } else {
            // if adjacent cell doen't have mine cell turn blank and cells around it will be
            // revealed and checked for mines
            grid.reveal[row][col] = true;


            // loop to continue the process till one cell left
            if (row > 0) {
                if (col > 0) {
                    if (!grid.reveal[row - 1][col - 1]) {
                        clearBanks((row - 1), (col - 1));
                    };
                };
                if (!grid.reveal[row - 1][col]) {
                    clearBanks((row - 1), col);
                };
                if (col < (grid.getWidth() - 1)) {
                    if (!grid.reveal[row - 1][col + 1]) {
                        clearBanks((row - 1), (col + 1));
                    };
                };
            }

            if (col > 0) {
                if (!grid.reveal[row][col - 1]) {
                    clearBanks(row, (col - 1));
                };
            };

            if (col < (grid.getWidth() - 1)) {
                if (!grid.reveal[row][col + 1]) {
                    clearBanks(row, (col + 1));
                };
            };

            if (row < (grid.getHeight() - 1)) {
                if (col > 0) {
                    if (!grid.reveal[row + 1][col - 1]) {
                        clearBanks((row + 1), (col - 1));
                    };
                }
                if (!grid.reveal[row + 1][col]) {
                    clearBanks((row + 1), col);
                };
                if (col < (grid.getWidth() - 1)) {
                    if (!grid.reveal[row + 1][col + 1]) {
                        clearBanks((row + 1), (col + 1));
                    };
                };
            }

        }

    }

    // returns number of mines adjacent to the location
    private int numberOfMines(int row, int col) {
        int mines = 0;
        if (row > 0) {
            if (col > 0) {
                if (grid.board[row - 1][col - 1]) {
                    mines += 1;
                };
            };
            if (grid.board[row - 1][col]) {
                mines += 1;
            };
            if (col < (grid.getWidth() - 1)) {
                if (grid.board[row - 1][col + 1]) {
                    mines += 1;
                };
            };
        }


        if (col > 0) {
            if (grid.board[row][col - 1]) {
                mines += 1;
            };
        };
        if (col < (grid.getWidth() - 1)) {
            if (grid.board[row][col + 1]) {
                mines += 1;
            };
        };


        if (row < (grid.getHeight() - 1)) {
            if (col > 0) {
                if (grid.board[row + 1][col - 1]) {
                    mines += 1;
                };
            };
            if (grid.board[row + 1][col]) {
                mines += 1;
            };
            if (col < (grid.getWidth() - 1)) {
                if (grid.board[row + 1][col + 1]) {
                    mines += 1;
                };
            };
        }
        return mines;
    }
}
