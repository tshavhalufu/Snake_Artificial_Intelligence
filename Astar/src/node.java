import java.util.HashSet;
import java.util.LinkedList;
public class node {
    int x;
    int y;
    node parent;

    node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setParent(node parentValue) {
        this.parent = parentValue;
    }

    public node getParent() {
        return this.parent;
    }

    @Override
    public boolean equals(Object obj) {
        node node = (node) obj;
        return this.x == node.x && this.y == node.y;
    }

}