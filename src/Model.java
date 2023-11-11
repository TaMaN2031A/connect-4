import java.util.ArrayList;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Model {
    private Heuristic heuristic;
    public Model(Heuristic heuristic){
        this.heuristic = heuristic;
    }
    int minmax_without_pruning_decision(State state, int depth){
        long option = Long.MIN_VALUE; int which_column=3; // 3 was used to test the ui only
        // Warn el regala about the importance of the loop replacing the generate_next_states here, as
        // depending on the best 'i' we'll return the answer. Cannot handle it till state is determined
        ArrayList<State> next = generate_next_states(state);
        for (int i = 0; i < next.size(); i++){
            long op = min_val_without_pruning(next.get(i), depth-1);
            if(op > option){
                option = op;
                which_column = i;
            }
        }
        return which_column;
    }
    private long min_val_without_pruning(State state, int depth){
        ArrayList<State> next = generate_next_states(state);
        if(depth == 0 || next.isEmpty())
            return heuristic.calculateHeuristic(state);
        long option = Long.MAX_VALUE;
        for(int i = 0; i < next.size(); i++){
            option = min(option, max_val_without_pruning(state, depth-1));
        }
        return option;
    }
    private long max_val_without_pruning(State state, int depth){
        ArrayList<State> next = generate_next_states(state);
        if(depth == 0 || next.isEmpty())
            return heuristic.calculateHeuristic(state);
        long option = Long.MIN_VALUE;
        for(int i = 0; i < next.size(); i++){
            option = max(option, max_val_without_pruning(state, depth-1));
        }
        return option;
    }

    //* alpha beta
    int minmax_with_pruning_decision(State state, int depth){
        long option = Long.MIN_VALUE; int which_column=3; // 3 was used to test the ui only
        // Warn el regala about the importance of the loop replacing the generate_next_states here, as
        // depending on the best 'i' we'll return the answer. Cannot handle it till state is determined
        ArrayList<State> next = generate_next_states(state);
        for (int i = 0; i < next.size(); i++){
            long op = min_val_with_pruning(next.get(i), depth-1, Long.MIN_VALUE, Long.MAX_VALUE);
            if(op > option){
                option = op;
                which_column = i;
            }
        }
        return which_column;
    }
    private long min_val_with_pruning(State state, int depth, long alpha, long beta){
        ArrayList<State> next = generate_next_states(state);
        if(depth == 0 || next.isEmpty())
            return heuristic.calculateHeuristic(state);
        long option = Long.MAX_VALUE;
        for(int i = 0; i < next.size(); i++){
            option = min(option, max_val_with_pruning(state, depth-1, alpha, beta));
            if(option <= alpha)
                return option; // I'm a min node, m7d4 hena hyob2a akbar mn el option
            beta = min(beta, option);
        }
        return option;
    }
    private long max_val_with_pruning(State state, int depth, long alpha, long beta){
        ArrayList<State> next = generate_next_states(state);
        if(depth == 0 || next.isEmpty())
            return heuristic.calculateHeuristic(state);
        long option = Long.MIN_VALUE;
        for(int i = 0; i < next.size(); i++){
            option = max(option, max_val_with_pruning(state, depth-1, alpha, beta));
            if(option >= beta)
                return option;
            alpha = max(option, alpha);
        }
        return option;
    }
    private ArrayList<State> generate_next_states(State state){
        return new ArrayList<>();
    }
}
