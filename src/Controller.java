public class Controller {
    private Model model;
    private int with;
    private int depth;
    public Controller(Model model, int with, int depth){
        this.model = model;
        this.depth = depth;
        this.with = with;
    }
    int make_good_move(byte[][] state){
        if (with == 1) return model.minmax_with_pruning_decision(state, depth);
        return model.minmax_without_pruning_decision(state, depth);
    }
}
