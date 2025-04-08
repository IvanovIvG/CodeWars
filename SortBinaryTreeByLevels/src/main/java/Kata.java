import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


/**
 * @author Ivan Ivanov
 **/
public class Kata {
    private static List<Integer> solution = new ArrayList<>();
    private static List<Node> level = new ArrayList<>();

    public static List<Integer> treeByLevels(Node node) {
        List<Integer> solution = new ArrayList<>();
        List<Node> level = new ArrayList<>();
        level.add(node);
        if(node == null)
            return Collections.emptyList();
        else {
            solution.add(node.value);
        }
        while (!level.isEmpty()){
            List<Node> nextLevel = new ArrayList<>();
            for(Node levelNode: level){
                if(levelNode.left != null) {
                    nextLevel.add(levelNode.left);
                    solution.add(levelNode.left.value);
                }
                if(levelNode.right != null) {
                    nextLevel.add(levelNode.right);
                    solution.add(levelNode.right.value);
                }
            }
            level = nextLevel;
        }
        return solution;
    }
}
