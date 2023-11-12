public class Helper {
    public long calculateHeuristic(byte[][] state, int who) {
        int rows = state.length;
        int cols = state[0].length;
        long score = 0;

        // Check horizontally
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                int counts = 0;

                for (int i = 0; i < 4; i++) {
                    if(state[row][col + i] == who)
                        counts++;

                }

                if(counts == 4)
                    score++;
            }
        }

        // Check vertically
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                int counts = 0;

                for (int i = 0; i < 4; i++) {
                    if(state[row + i][col] == who)
                        counts++;
                }

                if(counts == 4)
                    score++;
            }
        }

        // Check diagonally (from top-left to bottom-right)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                int counts = 0;

                for (int i = 0; i < 4; i++) {
                    if (state[row + i][col + i] == who)
                        counts++;
                }

                if (counts == 4)
                    score++;
            }
        }

        // Check diagonally (from top-right to bottom-left)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = cols - 1; col >= 3; col--) {

                int counts = 0;

                for (int i = 0; i < 4; i++) {
                    if (state[row + i][col - i] == who)
                        counts++;
                }

                if (counts == 4)
                    score++;
            }
        }

        return score;
    }

}
