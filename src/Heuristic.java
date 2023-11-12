import java.util.Random;

public class Heuristic {

    public long calculateHeuristic(byte[][] state) {
        int rows = state.length;
        int cols = state[0].length;
        long score = 0;

        // Check horizontally
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                int[] counts = new int[3];

                for (int i = 0; i < 4; i++) {
                    counts[state[row][col + i]]++;
                }

                score += evaluateWindow(counts);
            }
        }

        // Check vertically
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                int[] counts = new int[3];

                for (int i = 0; i < 4; i++) {
                    counts[state[row + i][col]]++;
                }

                score += evaluateWindow(counts);
            }
        }

        // Check diagonally (from top-left to bottom-right)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                int[] counts = new int[3];

                for (int i = 0; i < 4; i++) {
                    counts[state[row + i][col + i]]++;
                }

                score += evaluateWindow(counts);
            }
        }

        // Check diagonally (from top-right to bottom-left)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = cols - 1; col >= 3; col--) {
                int[] counts = new int[3];

                for (int i = 0; i < 4; i++) {
                    counts[state[row + i][col - i]]++;
                }

                score += evaluateWindow(counts);
            }
        }

        return score;
    }

    private long evaluateWindow(int[] counts) {
        int computerPieces = counts[1];
        int userPieces = counts[2];
        int emptySquares = counts[0];

        if (computerPieces == 4) {
            return 600;
        } else if (computerPieces == 3 && emptySquares == 1) {
            return 200;
        } else if (computerPieces == 3 && userPieces == 1) {
            return 50;
        } else if (computerPieces == 2 && userPieces == 2) {
            return 0;
        } else if (computerPieces == 2 && emptySquares == 2) {
            return 30;
        } else if (computerPieces == 2 && userPieces == 1) {
            return 20;
        } else if (computerPieces == 1 && userPieces == 3) {
            return 0;
        } else if (computerPieces == 1 && userPieces == 0) {
            return 0;
        } else if (computerPieces == 1 && userPieces == 1) {
            return 0;
        } else if (computerPieces == 1 && userPieces == 2) {
            return 0;
        } else if (userPieces == 4) {
            return -300;
        } else if (userPieces == 3 && emptySquares == 1) {
            return -200;
        } else if (userPieces == 1 && emptySquares == 3) {
            return 0;
        } else if (userPieces == 2 && emptySquares == 2) {
            return -10;
        }
        return 0;
    }

}