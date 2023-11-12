
public class Main {
    public static void main(String[] args) {
        Gui gui = new Gui(new Controller(new Model(new Heuristic()), 1, 8));
    }
}