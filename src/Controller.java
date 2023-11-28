import java.util.ArrayList;

public class Controller {
    private Model model;
    private int with;
    private int depth;
    private TreeDrawer treeDrawer;
    private int To_draw;
    public Controller(Model model, int with, int depth,TreeDrawer treeDrawer,int To_draw){
        this.model = model;
        this.depth = depth;
        this.with = with;
        this.treeDrawer = treeDrawer;
        this.To_draw=To_draw;
    }




    int make_good_move(byte[][] state) {
        int move;
        if (with == 1) {
            move = model.minmax_with_pruning_decision(state, depth);
            if(To_draw==1)
                draw();
            return move;
        } else {
            move = model.minmax_without_pruning_decision(state, depth);
            if(To_draw==1)
                draw();
            return move;
        }
    }
    void draw(){

        ArrayList<Node>arr=model.getTree();
        int root = model.getRoot();
        TreeDrawer.draw(arr,root);
    }

}
