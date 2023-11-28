import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TreeDrawer {

    private Map<Integer,Integer>id_to_value ;
    public static void draw(ArrayList<Node>arr, int root) {
        SwingUtilities.invokeLater(() -> createAndShowGUI(arr, root));
    }
    private static void createAndShowGUI(ArrayList<Node> arr, int root) {
        JFrame frame = new JFrame("Tree Visualization Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DelegateTree<Integer, String> delegateTree = createSampleTree(arr);
        TreeLayout<Integer,String> treeLayout = new TreeLayout<>(delegateTree);
        VisualizationViewer<Integer, String> vv = new VisualizationViewer<>(treeLayout);
        vv.getRenderContext().setVertexFillPaintTransformer(new NodeColorTransformer(arr));
        // Customize the size of the VisualizationViewer
        vv.setPreferredSize(new Dimension(150000, 600));
        vv.getRenderContext().setVertexLabelTransformer(new NodeLabelTransformer(delegateTree,arr));
        vv.getRenderContext().setEdgeArrowPredicate(edge -> false);
        Map<Integer, ArrayList<Integer>> depthToVertices = new HashMap<>();

        // Group vertices by depth
        for (Integer vertex : treeLayout.getGraph().getVertices()) {
            int depth = arr.get(vertex).getDepth();
            depthToVertices.computeIfAbsent(depth, k -> new ArrayList<>()).add(vertex);
        }

        int startingX = 50;
        int startingY = 400;
        int horizontalSpacing = 40;
        int verticalSpacing = 100;

        // Place vertices based on depth
        for (ArrayList<Integer> vertices : depthToVertices.values()) {
            for (Integer vertex : vertices) {
                Point2D location = treeLayout.transform(vertex);
                location.setLocation(startingX + (horizontalSpacing * vertices.indexOf(vertex)), startingY);
            }

            // Adjust spacing for the next level
            startingX += horizontalSpacing;
            startingY -= verticalSpacing;

            // Decrease spacing for the next level
            horizontalSpacing *= 8;
        }
        JPanel myPanel = new JPanel();
        myPanel.add(vv);

        // Wrap the VisualizationViewer in a JScrollPane
        JScrollPane scroll = new JScrollPane(myPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVisible(true);

        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        frame.setSize(800, 800);

        // Set the location relative to null for centering
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static DelegateTree<Integer, String> createSampleTree(ArrayList<Node>arr) {
        DelegateTree<Integer, String> delegateTree = new DelegateTree<>();
        arr.sort(Comparator.comparingInt(Node::getParent_id));
        delegateTree.setRoot(0);
        System.out.print(arr.toString());

        for(int i=1;i<arr.size();i++){
            delegateTree.addChild(Integer.toString(arr.get(i).getId())+":"+Integer.toString(arr.get(i).getValue()),arr.get(i).getParent_id(),arr.get(i).getId());
        }
        arr.sort(Comparator.comparingInt(Node::getId));
        return delegateTree;
    }

    private static class NodeLabelTransformer implements Transformer<Integer, String> {
        private final DelegateTree<Integer, String> delegateTree;
        private final ArrayList<Node> tree;

        public NodeLabelTransformer(DelegateTree<Integer, String> delegateTree,ArrayList<Node>tree) {
            this.delegateTree = delegateTree;
            this.tree = tree;
        }

        @Override
        public String transform(Integer node) {
            // Get the edge label associated with the node
            String edgeLabel = Integer.toString(tree.get(node).getValue());
            return edgeLabel;
        }
    }
    private static class NodeColorTransformer implements Transformer<Integer, Paint> {
        private final ArrayList<Node> arr;

        public NodeColorTransformer(ArrayList<Node> arr) {
            this.arr = arr;
        }

        @Override
        public Paint transform(Integer node) {
            // Get the depth of the node
            int depth = getNodeDepth(node);

            // Set color based on depth (even -> red, odd -> blue)
            return (depth % 2 == 0) ? Color.RED : Color.BLUE;
        }

        private int getNodeDepth(Integer node) {
            for (Node n : arr) {
                if (n.getId() == node) {
                    return n.getDepth();
                }
            }
            return 0;
        }
    }

}
