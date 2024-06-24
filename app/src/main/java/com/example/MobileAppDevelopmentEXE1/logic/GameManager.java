package com.example.MobileAppDevelopmentEXE1.logic;

import java.util.Random;

public class GameManager {
    private int[][] ghostMat;
    private int[] pacmanArr;
    private int life;
    private int collisions = 0;
    private int playerIndex;
    private int numRows;
    private int numCols;
    Random random = new Random();


    public GameManager() {
        this(4, 3, 3);
    }

    public GameManager(int rows, int cols, int life) {
        numRows = rows;
        numCols = cols;
        ghostMat = new int[rows][cols];
        pacmanArr = new int[cols];
        this.life = life;
        resetMatAndArr(rows, cols);
        playerIndex = cols / 2;
    }

    private void resetMatAndArr(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ghostMat[i][j] = 0;
                if (i == 0) {
                    if (j == cols / 2) {
                        pacmanArr[j] = 1;
                    } else {
                        pacmanArr[j] = 0;
                    }
                }
            }
        }
    }

    public int[][] getGhostMat() {
        return ghostMat;
    }

    public GameManager setGhostMat(int[][] ghostMat) {
        this.ghostMat = ghostMat;
        return this;
    }

    public int[] getPacmanArr() {
        return pacmanArr;
    }

    public GameManager setPacmanArr(int[] pacmanArr) {
        this.pacmanArr = pacmanArr;
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

    public GameManager setNumCols(int numCols) {
        this.numCols = numCols;
        return this;
    }

    public boolean checkIfCollision(int playerIndex) {
        if (ghostMat[ghostMat.length - 1][playerIndex] == 1) {
            collisions += 1;
            return true;
        }
        return false;
    }


    public boolean isGameOver() {
        return getLife() == getCollisions();
    }

    public void changePlayerPosition(int direction) {
        pacmanArr[playerIndex] = 0;
        if (direction == 0) {
            if (playerIndex == 0) {
                pacmanArr[numCols - 1] = 1;
                playerIndex = numCols - 1;
            } else {
                pacmanArr[playerIndex - 1] = 1;
                playerIndex -= 1;
            }
        } else {
            if (playerIndex == numCols - 1) {
                pacmanArr[0] = 1;
                playerIndex = 0;
            } else {
                pacmanArr[playerIndex + 1] = 1;
                playerIndex += 1;
            }
        }
    }

    public void changeMatrixPosition(int sec) {
        for (int i = numRows - 1; i >= 0; i--) {
            for (int j = numCols - 1; j >= 0; j--) {
                if (i == numRows - 1 && ghostMat[i][j] == 1) {
                    ghostMat[i][j] = 0;
                }
                if (ghostMat[i][j] == 1) {
                    ghostMat[i][j] = 0;
                    ghostMat[i + 1][j] = 1;
                }
            }
        }
        if (sec % 2 == 0) {
            startNewColumn();
        }
    }

    private void startNewColumn() {
        int randomColumn = random.nextInt(numCols);
        ghostMat[0][randomColumn] = 1;
    }


}
