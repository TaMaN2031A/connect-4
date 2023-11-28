import edu.uci.ics.jung.graph.DelegateTree;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter depth: ");
        int depth=sc.nextInt();
        System.out.println("for withPruning enter 1 for withoutPruning enter 2:");
        int with =sc.nextInt();
        System.out.println("if you want it to draw the tree enter 1 otherwise enter 2:");
        int draw=sc.nextInt();
        Gui gui = new Gui(new Controller(new Model(new Heuristic()), with,depth,new TreeDrawer(),draw));
    }
}