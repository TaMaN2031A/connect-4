import java.util.ArrayList;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Model {
    private Heuristic heuristic;
    public Model(Heuristic heuristic){
        this.heuristic = heuristic;
    }
    int minmax_without_pruning_decision(byte[][] state, int depth){
        long option = Long.MIN_VALUE; int which_column = -1; // if -1 returned, stop nothing to do game ended
        // Warn el regala about the importance of the loop replacing the generate_next_states here, as
        // depending on the best 'i' we'll return the answer. Cannot handle it till state is determined
        for (int i = 0; i < 7; i++){
            int row = getEmptyRow(state, i);
            if(row == -1)
                continue;
            byte[][] new_state = new byte[6][7];
            for(int l = 0; l < 6; l++){
                for(int j = 0; j < 7; j++){
                    new_state[l][j] = state[l][j];
                }
            }
            new_state[row][i] = 1;
            long op = min_val_without_pruning(new_state, depth-1);
            if(op > option){
                option = op;
                which_column = i;
            }
        }
        return which_column;
    }
    private long min_val_without_pruning(byte[][] state, int depth){
        if(depth == 0)
            return heuristic.calculateHeuristic(state);
        long option = Long.MAX_VALUE;
        for (int i = 0; i < 7; i++){
            int row = getEmptyRow(state, i);
            if(row == -1)
                continue;
            byte[][] new_state = new byte[6][7];
            for(int l = 0; l < 6; l++){
                for(int j = 0; j < 7; j++){
                    new_state[l][j] = state[l][j];
                }
            }
            new_state[row][i] = 2;
            long op = max_val_without_pruning(new_state, depth-1);
            if(op < option){
                option = op;
            }
        }
        return option;
    }
    private long max_val_without_pruning(byte[][] state, int depth){
        if(depth == 0)
            return heuristic.calculateHeuristic(state);
        long option = Long.MIN_VALUE;
        for (int i = 0; i < 7; i++){
            int row = getEmptyRow(state, i);
            if(row == -1)
                continue;
            byte[][] new_state = new byte[6][7];
            for(int l = 0; l < 6; l++){
                for(int j = 0; j < 7; j++){
                    new_state[l][j] = state[l][j];
                }
            }
            new_state[row][i] = 1;
            long op = min_val_without_pruning(new_state, depth-1);
            if(op > option){
                option = op;
            }
        }
        return option;
    }

    //* alpha beta
    int minmax_with_pruning_decision(byte[][] state, int depth){
        long option = Long.MIN_VALUE; int which_column=-1; // 3 was used to test the ui only
        // Warn el regala about the importance of the loop replacing the generate_next_states here, as
        // depending on the best 'i' we'll return the answer. Cannot handle it till state is determined
        for (int i = 0; i < 7; i++){
            int row = getEmptyRow(state, i);
            if(row == -1)
                continue;
            byte[][] new_state = new byte[6][7];
            for(int l = 0; l < 6; l++){
                for(int j = 0; j < 7; j++){
                    new_state[l][j] = state[l][j];
                }
            }
            new_state[row][i] = 1;
            long op = min_val_with_pruning(new_state, depth-1, Long.MIN_VALUE, Long.MAX_VALUE);
            if(op > option){
                option = op;
                which_column = i;
            }
        }
        return which_column;
    }
    private long min_val_with_pruning(byte[][] state, int depth, long alpha, long beta){
        if(depth == 0)
            return heuristic.calculateHeuristic(state);
        long option = Long.MAX_VALUE;
        for (int i = 0; i < 7; i++){
            int row = getEmptyRow(state, i);
            if(row == -1)
                continue;
            byte[][] new_state = new byte[6][7];
            for(int l = 0; l < 6; l++){
                for(int j = 0; j < 7; j++){
                    new_state[l][j] = state[l][j];
                }
            }
            new_state[row][i] = 2;
            option = min(max_val_with_pruning(new_state, depth-1, alpha, beta), option);
            if(option <= alpha){
                return option; // I'm a min node, m7d4 hena hyob2a akbar mn el option
            }
            beta = min(beta, option);
        }
        return option;
    }
    private long max_val_with_pruning(byte[][] state, int depth, long alpha, long beta){
        if(depth == 0)
            return heuristic.calculateHeuristic(state);
        long option = Long.MIN_VALUE;
        for (int i = 0; i < 7; i++){
            int row = getEmptyRow(state, i);
            if(row == -1)
                continue;
            byte[][] new_state = new byte[6][7];
            for(int l = 0; l < 6; l++){
                for(int j = 0; j < 7; j++){
                    new_state[l][j] = state[l][j];
                }
            }

            new_state[row][i] = 1;
            option = max(option, min_val_with_pruning(new_state, depth-1, alpha, beta));
            if(option >= beta){
                return option;
            }
            alpha = max(option, alpha);
        }
        return option;
    }
    private int getEmptyRow(byte[][] state, int col) {
        for (int row = state.length-1; row > -1; row--) {
            if (state[row][col] == 0) {
                return row;
            }
        }
        return -1;
    }
}
