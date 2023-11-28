public class Node {
    private int value;
    private int id;

    private int parent_id;

    private int depth;
    public int getParent_id() {
        return parent_id;
    }

    public Node(int value, int id,int parent_id,int depth) {
        this.value = value;
        this.id = id;
        this.parent_id = parent_id;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", id=" + id +
                ", parent_id=" + parent_id +
                '}';
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getValue() {
        return value;
    }

    public int getDepth() {
        return depth;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
