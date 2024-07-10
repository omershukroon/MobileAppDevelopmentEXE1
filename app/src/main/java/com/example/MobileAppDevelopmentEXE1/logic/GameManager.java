package com.example.MobileAppDevelopmentEXE1.logic;



import java.util.Random;

public class GameManager {
    private int[][] mushroomsMat;
    private int[][] coinsMat;
    private int[] mariosArr;
    private int life;
    private int collisions = 0;
    private int playerIndex;
    private int numRows;
    private int numCols;
    Random random = new Random();
    private int score;
    private static final int POINTS = 5;


    public GameManager() {
        this(4, 3, 3);
    }

    public GameManager(int rows, int cols, int life) {
        numRows = rows;
        numCols = cols;
        mushroomsMat = new int[rows][cols];
        coinsMat = new int[rows][cols];
        mariosArr = new int[cols];
        this.life = life;
        resetMatAndArr(rows, cols);
        playerIndex = cols / 2;
        mariosArr[playerIndex] = 1;
        score = 0;

    }


    private void resetMatAndArr(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mushroomsMat[i][j] = 0;
                coinsMat[i][j] = 0;
                if (i == 0) {
                    mariosArr[j] = 0;
                }
            }
        }
    }

    public int[][] getCoinsMat() {
        return coinsMat;
    }

    public int[][] getMushroomsMat() {
        return mushroomsMat;
    }

    public GameManager setMushroomsMat(int[][] mushroomsMat) {
        this.mushroomsMat = mushroomsMat;
        return this;
    }

    public int[] getMariosArr() {
        return mariosArr;
    }

    public GameManager setMariosArr(int[] mariosArr) {
        this.mariosArr = mariosArr;
        return this;
    }

    public int getLife() {
        return life;
    }

    public GameManager setLife(int life) {
        this.life = life;
        return this;
    }


    public GameManager setCollisions(int collisions) {
        this.collisions = collisions;
        return this;
    }

    public int getCollisions() {
        return collisions;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public GameManager setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
        return this;
    }

    public int getNumRows() {
        return numRows;
    }

    public GameManager setNumRows(int numRows) {
        this.numRows = numRows;
        return this;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getScore() {
        return score;
    }

    public GameManager setNumCols(int numCols) {
        this.numCols = numCols;
        return this;
    }

    public GameManager setCoinsMat(int[][] coinsMat) {
        this.coinsMat = coinsMat;
        return this;
    }


    public GameManager setScore(int score) {
        this.score = score;
        return this;
    }

    public boolean checkIfCollision(int playerIndex) {
        if (mushroomsMat[numRows - 1][playerIndex] == 1) {
            collisions += 1;
            mushroomsMat[numRows - 1][playerIndex] = 0;
            return true;
        }

        return false;
    }

    public boolean checkIfGetCoin(int playerIndex) {

        if (coinsMat[numRows - 1][playerIndex] == 1) {
            coinsMat[numRows - 1][playerIndex] = 0;
            score += 3 * POINTS;
            return true;
        }

        return false;
    }

    public boolean isGameOver() {
        return getLife() == getCollisions();
    }

    public void changePlayerPosition(int direction) {
        changePlayerPositionHelper(direction, mariosArr);

    }

    private void changePlayerPositionHelper(int direction, int[] playerArr) {
        playerArr[playerIndex] = 0;
        if (direction == 0) {
            if (playerIndex == 0) {
                playerArr[numCols - 1] = 1;
                playerIndex = numCols - 1;
            } else {
                playerArr[playerIndex - 1] = 1;
                playerIndex -= 1;
            }
        } else {
            if (playerIndex == numCols - 1) {
                playerArr[0] = 1;
                playerIndex = 0;
            } else {
                playerArr[playerIndex + 1] = 1;
                playerIndex += 1;
            }
        }

    }

    public void changeMatrixPosition(int sec) {
        int i = 6;
        changeMatrixPositionHellper(mushroomsMat);
        changeMatrixPositionHellper(coinsMat);
        if (sec % 2 == 0) {
            startNewColumn(mushroomsMat);
        }
        if (sec % i == 0 && sec > 1) {
            i += 2;
            startNewColumn(coinsMat);
        }
        score += POINTS;
    }

    private void changeMatrixPositionHellper(int[][] mat) {
        for (int i = numRows - 1; i >= 0; i--) {
            for (int j = numCols - 1; j >= 0; j--) {
                if (i == numRows - 1 && mat[i][j] == 1) {
                    mat[i][j] = 0;
                }
                if (mat[i][j] == 1) {
                    mat[i][j] = 0;
                    mat[i + 1][j] = 1;
                }
            }
        }
    }

    public void startNewColumn(int[][] met) {
        int randomColumn = random.nextInt(numCols);
        met[0][randomColumn] = 1;
    }

}
