import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivan Ivanov
 **/
public class SG1 {
    private static int length;
    private static int width;

    public static String wireDHD(String existingWires) {
        String[] lines = existingWires.split("\\n");
        length = lines.length;
        width = lines[0].length();

        Node[][] nodesMatrix = getNodesMatrix(lines);
        List<Node> path = createPath(nodesMatrix);
        return createAnswer(path, nodesMatrix);
    }

    private static Node[][] getNodesMatrix(String[] lines) {
        Node[][] nodesMatrix = createNodesMatrix(lines);
        addJumps(nodesMatrix);
        return nodesMatrix;
    }

    private static Node[][] createNodesMatrix(String[] lines) {
        Node[][] nodesMatrix = new Node[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                nodesMatrix[i][j] = new Node(lines[i].toCharArray()[j]);
            }
        }
        return nodesMatrix;
    }

    private static void addJumps(Node[][] nodesMatrix) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                List<Node> closeNodes = new ArrayList<>();
                addCloseNodeToListIfPossible(closeNodes, nodesMatrix, i, j-1);
                addCloseNodeToListIfPossible(closeNodes, nodesMatrix, i-1, j);
                addCloseNodeToListIfPossible(closeNodes, nodesMatrix, i+1, j);
                addCloseNodeToListIfPossible(closeNodes, nodesMatrix, i, j+1);
                for (Node closeNode: closeNodes){
                    nodesMatrix[i][j].addJumpFromNode(new Jump(nodesMatrix[i][j], closeNode, 1));
                }

                List<Node> closeDiagonalNodes = new ArrayList<>();
                addCloseNodeToListIfPossible(closeDiagonalNodes, nodesMatrix, i-1, j-1);
                addCloseNodeToListIfPossible(closeDiagonalNodes, nodesMatrix, i+1, j-1);
                addCloseNodeToListIfPossible(closeDiagonalNodes, nodesMatrix, i-1, j+1);
                addCloseNodeToListIfPossible(closeDiagonalNodes, nodesMatrix, i+1, j+1);
                for (Node closeDiagonalNode: closeDiagonalNodes){
                    nodesMatrix[i][j].addJumpFromNode(new Jump(nodesMatrix[i][j], closeDiagonalNode, Math.sqrt(2)));
                }
            }
        }
    }

    private static void addCloseNodeToListIfPossible(List<Node> closeNodes, Node[][] nodesMatrix, int i, int j) {
        if(i>=0 && i<length && j>=0 && j<width && nodesMatrix[i][j].isTraversable()){
            closeNodes.add(nodesMatrix[i][j]);
        }
    }

    private static List<Node> createPath(Node[][] nodesMatrix) {
        Node start = findStart(nodesMatrix);
        Node finish = findFinish(nodesMatrix);

        achieveAllPossibleNodes(start);
        return getPath(start, finish);
    }

    private static List<Node> getPath(Node start, Node finish) {
        List<Node> path = new ArrayList<>();
        if(!finish.isVisited()){
            return path;
        }

        Node pathNode = finish;
        while(pathNode != start){
            path.add(pathNode);
            pathNode = pathNode.getJumpsFromNode().stream().min(Comparator.comparing(jump -> jump.cost()+jump.finish().getCostToAchieve())).
                    orElseThrow().finish();
        }
        path.add(start);
        return path;
    }

    private static void achieveAllPossibleNodes(Node start) {
        Set<Jump> possibleJumps = start.getJumpsFromNode();
        start.setVisited(true);

        while (!possibleJumps.isEmpty()){
            Jump bestJump = possibleJumps.stream().min(Comparator.comparing(jump -> jump.cost()+jump.start().getCostToAchieve())).
                    orElseThrow();

            bestJump.finish().setVisited(true);
            bestJump.finish().setCostToAchieve(bestJump.cost()+bestJump.start().getCostToAchieve());

            Set<Jump> noMoreActualJumps = possibleJumps.stream().
                    filter(jump -> jump.finish() == bestJump.finish()).collect(Collectors.toSet());
            possibleJumps.removeAll(noMoreActualJumps);

            Set<Jump> newJumps = bestJump.finish().getJumpsFromNode().stream().
                    filter(jump -> !jump.finish().isVisited()).collect(Collectors.toSet());
            possibleJumps.addAll(newJumps);
        }
    }

    private static Node findStart(Node[][] nodesMatrix) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if(nodesMatrix[i][j].getSymbol() == 'S'){
                    return nodesMatrix[i][j];
                }
            }
        }
        throw new RuntimeException("No start");
    }

    private static Node findFinish(Node[][] nodesMatrix) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if(nodesMatrix[i][j].getSymbol() == 'G'){
                    return nodesMatrix[i][j];
                }
            }
        }
        throw new RuntimeException("No finish");
    }

    private static String createAnswer(List<Node> path, Node[][] nodesMatrix) {
        if(path.isEmpty()){
            return "Oh for crying out loud...";
        }
        for(Node node: path){
            if(node.getSymbol()!='S' && node.getSymbol()!='G'){
                node.setSymbol('P');
            }
        }
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                answer.append(nodesMatrix[i][j].getSymbol());
            }
            answer.append("\n");
        }
        answer.deleteCharAt(answer.length() - 1);
        return answer.toString();
    }
}

class Node {
    private final boolean traversable;
    private final Set<Jump> jumpsFromNode;

    private double costToAchieve;
    private char symbol;
    private boolean visited;

    public Node(char symbol) {
        this.jumpsFromNode = new HashSet<>();

        this.costToAchieve = 0;
        this.symbol = symbol;
        traversable = symbol != 'X';
        this.visited = false;
    }

    public boolean isTraversable() {
        return traversable;
    }

    public boolean isVisited() {
        return visited;
    }

    public Set<Jump> getJumpsFromNode() {
        return jumpsFromNode;
    }



    public double getCostToAchieve() {
        return costToAchieve;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setCostToAchieve(double costToAchieve) {
        this.costToAchieve = costToAchieve;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void addJumpFromNode(Jump jump){
        this.jumpsFromNode.add(jump);
    }
}

record Jump(Node start, Node finish, double cost){}