

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

//If the player opens a cell that is not adjacent to a mine-cell, then all its neighbor cells are opened automatically

    // E = %15
    // M = %25
    // H = %40 Mine

    public static String[][] placeMines(char level, int m, int n) {
        String[][] board = new String[m][n];
        String[][] mineboard = new String[m][n];
        int mineNum = 0;
        Random random = new Random();
        int placeNum = m * n;

        if (level == 'E') {
            mineNum = placeNum * 15 / 100;
        } else if (level == 'M') {
            mineNum = placeNum * 25 / 100;
        } else if (level == 'H') {
            mineNum = placeNum * 40 / 100;
        }

        int i = 0;
        while (i < mineNum) {
            int index1 = random.nextInt(m - 1);
            int index2 = random.nextInt(n - 1);
            if (mineboard[index1][index2] == "mine") {
                continue;

            } else {
                mineboard[index1][index2] = "mine";

                i++;

            }

        }
        return mineboard;

    }

    public static boolean isMine(int index1, int index2, String[][] mineboard) {
        boolean mine = false;
        if (mineboard[index1][index2] == "mine") {
            mine = true;
        }

        return mine;

    }

    public static void printArray(String[][] arr) {
        int i = 0;
        int j = 0;
        while (i < arr.length) {
            j = 0;
            while (j < arr[0].length) {
                System.out.print(arr[i][j] + " ");
                j++;

            }
            System.out.println();
            i++;
        }

    }

    public static boolean endOfTheGame(String[][] shapeboard, String[][] mineboard) {
        boolean end = true;
        for (int i = 0; i < shapeboard.length; i++) {
            for (int j = 0; j < shapeboard.length; j++) {
                if (shapeboard[i][j] != "o ") {
                    continue;
                } else if (shapeboard[i][j] == "o ") {
                    if (mineboard[i][j] != "mine") {
                        end = false;

                    }

                }

            }
        }
        return end;
    }

    public static String[][] fillBoard(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = "o ";

            }

        }
        return arr;

    }

    public static void chooseAction(String[][] mineboard, int i, int j, String action, String shapeboard[][]) {
        if (action.equals("F")) {// FLAG
            flag(mineboard, i, j, shapeboard);

        } else if (action.equals("U")) {// UNFLAG
            unflag(mineboard, i, j, shapeboard);

        } else if (action.equals("O")) { // OPEN
            if (mineboard[mineboard.length - i][j - 1] == "mine") {
                lose(mineboard, shapeboard);
            }

            else {
                opencell(shapeboard.length - i, j - 1, mineboard, shapeboard);

            }

        }
    }

    public static void opencell(int row, int col, String[][] mineboard, String[][] shapeboard) {

        if (shapeboard[row][col] == "F ") {
            return;
        } else if (shapeboard[row][col] != "o ") {
            return;
        } else if (mineboard[row][col] == "mine ") {
            return;
        }

        else if (isValid(row, col, mineboard) != true) {
            return;

        }

        else {
            if (findAdjacentCells(mineboard, row, col, shapeboard) != 0) {

                shapeboard[row][col] = findAdjacentCells(mineboard, row, col, shapeboard) + " ";
            } else {

                shapeboard[row][col] = "- ";

                if (isValid(row - 1, col - 1, mineboard)) {
                    if (mineboard[row - 1][col - 1] != "mine " && shapeboard[row - 1][col - 1] == "o ") {
                        opencell(row - 1, col - 1, mineboard, shapeboard);
                    }
                }

                if (isValid(row - 1, col, mineboard)) {

                    if (mineboard[row - 1][col] != "mine " && shapeboard[row - 1][col] == "o ") {
                        opencell(row - 1, col, mineboard, shapeboard);
                    }
                }

                if (isValid(row, col - 1, mineboard)) {
                    if (mineboard[row][col - 1] != "mine " && shapeboard[row][col - 1] == "o ") {
                        opencell(row, col - 1, mineboard, shapeboard);
                    }

                }
                if (isValid(row + 1, col, mineboard)) {
                    if (mineboard[row + 1][col] != "mine " && shapeboard[row + 1][col] == "o ") {
                        opencell(row + 1, col, mineboard, shapeboard);
                    }

                }
                if (isValid(row, col + 1, mineboard)) {
                    if (mineboard[row][col + 1] != "mine " && shapeboard[row][col + 1] == "o ") {
                        opencell(row, col + 1, mineboard, shapeboard);
                    }

                }
                if (isValid(row + 1, col + 1, mineboard)) {
                    if (mineboard[row + 1][col + 1] != "mine " && shapeboard[row + 1][col + 1] == "o ") {
                        opencell(row + 1, col + 1, mineboard, shapeboard);
                    }

                }
                if (isValid(row - 1, col + 1, mineboard)) {
                    if (mineboard[row - 1][col + 1] != "mine " && shapeboard[row - 1][col + 1] == "o ") {
                        opencell(row - 1, col + 1, mineboard, shapeboard);
                    }

                }
                if (isValid(row + 1, col - 1, mineboard)) {
                    if (mineboard[row + 1][col - 1] != "mine " && shapeboard[row + 1][col - 1] == "o ") {
                        opencell(row + 1, col - 1, mineboard, shapeboard);
                    }

                }

            }
        }
    }

    public static boolean isValid(int row, int column, String[][] mineboard) {

        return (row >= 0) && (row < mineboard.length) && (column >= 0) && (column < mineboard[0].length);
    }

    public static void lose(String[][] mineboard, String[][] shapeboard) {
        System.out.println("You lost, better luck next time.");

        for (int i = 0; i < mineboard.length; i++) {
            for (int j = 0; j < mineboard.length; j++) {
                if (mineboard[i][j] == "mine") {
                    shapeboard[i][j] = "X ";

                }

            }

        }
        printArray(shapeboard);
        System.exit(0);

    }

    public static String[][] unflag(String mineboard[][], int i, int j, String shapeboard[][]) {

        if (shapeboard[shapeboard.length - i][j - 1] != "F ") {
            System.out.println("Only flagged cells can be unflagged .");

        } else {
            shapeboard[shapeboard.length - i][j - 1] = "o ";

        }
        return shapeboard;

    }

    public static String[][] flag(String mineboard[][], int i, int j, String shapeboard[][]) {

        if (shapeboard[shapeboard.length - i][j - 1] != "o ") {
            System.out.println("Open cells cannot be flagged");

        } else {
            shapeboard[mineboard.length - i][j - 1] = "F ";

        }

        return shapeboard;
    }

    public static int findAdjacentCells(String[][] mineboard, int row, int col, String[][] shapeboard) {

        int count = 0;

        if (isValid(row - 1, col, mineboard) == true) {
            if (isMine(row - 1, col, mineboard) == true) {
                count++;
            }
        }

        if (isValid(row + 1, col, mineboard) == true) {
            if (isMine(row + 1, col, mineboard) == true)
                count++;
        }

        if (isValid(row, col + 1, mineboard) == true) {
            if (isMine(row, col + 1, mineboard) == true) {
                count++;
            }
        }

        if (isValid(row, col - 1, mineboard) == true) {
            if (isMine(row, col - 1, mineboard) == true) {
                count++;
            }
        }

        if (isValid(row - 1, col + 1, mineboard) == true) {
            if (isMine(row - 1, col + 1, mineboard) == true) {
                count++;
            }
        }

        if (isValid(row - 1, col - 1, mineboard) == true) {
            if (isMine(row - 1, col - 1, mineboard) == true) {
                count++;
            }
        }

        if (isValid(row + 1, col + 1, mineboard) == true) {
            if (isMine(row + 1, col + 1, mineboard) == true) {
                count++;
            }
        }

        if (isValid(row + 1, col - 1, mineboard) == true) {
            if (isMine(row + 1, col - 1, mineboard) == true) {
                count++;
            }
        }

        return (count);

    }

    public static void main(String[] args) {
        String action = "";
        int move11 = 0;
        int move12 = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("::WELCOME::");
        System.out.print("Please enter the sizes of the board (m x n ) :");
        String sizes = keyboard.nextLine();

        int indexX = sizes.indexOf('x');
        int m = Integer.parseInt(sizes.substring(0, indexX));
        int n = Integer.parseInt(sizes.substring(indexX + 1));
        String[][] mineBoard = new String[m][n];
        String[][] shapeBoard = new String[m][n];
        shapeBoard = fillBoard(shapeBoard);
        System.out.print("Please select the difficulty (E, M, H) :");
        char dif = keyboard.nextLine().charAt(0);
        mineBoard = placeMines(dif, m, n);
        printArray(shapeBoard);
        //printArray(mineBoard);
        boolean win = true;

        while (win) {

            System.out.println("Please make a move:");
            String move1 = keyboard.nextLine();

            int indexM = move1.indexOf(",");

            if (move1.charAt(move1.length() - 1) != 'F' && move1.charAt(move1.length() - 1) != 'U') {
                action = "O";
                move11 = Integer.parseInt(move1.substring(0, indexM));
                move12 = Integer.parseInt(move1.substring(indexM + 1));
            } else {
                int indexSpace = move1.indexOf(" ");

                action = move1.substring(indexSpace + 1);
                move11 = Integer.parseInt(move1.substring(0, indexM));
                move12 = Integer.parseInt(move1.substring(indexM + 1, move1.length() - 2));
            }
            chooseAction(mineBoard, move11, move12, action, shapeBoard);
            printArray(shapeBoard);
           // printArray(mineBoard);
            if (endOfTheGame(shapeBoard, mineBoard)) {
                System.out.println("Congratulations, you won!");
                System.exit(0);
            }
        }

    }
}
