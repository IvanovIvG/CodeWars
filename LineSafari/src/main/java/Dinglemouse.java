import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivan Ivanov
 **/
public class Dinglemouse {
    private static Grid grid;
    public static boolean line(final char[][] charGrid) {
        grid = new Grid(charGrid);

        boolean isLine = goOnLine(grid.getFirstStart(), grid.getSecondStart());
        if (isLine) {
            return true;
        }
        grid.makeAllNodesNotVisited();
        isLine = goOnLine(grid.getSecondStart(), grid.getFirstStart());
        return isLine;
    }

    private static boolean goOnLine(Node start, Node finish) {
        Node currentNode = start;
        Node previousNode = null;
        while (currentNode != finish) {
            List<Node> possibleNodesToGo = grid.getPossibleNodesToGo(previousNode, currentNode);
            if (possibleNodesToGo.size() != 1) {
                return false;
            }
            currentNode.setVisited(true);
            previousNode = currentNode;
            currentNode = possibleNodesToGo.get(0);
        }
        currentNode.setVisited(true);
        return grid.allNodesVisited();
    }
}

class Grid{
    private final Node[][] gridNodes;
    private final int height;
    private final int width;

    private final Node firstStart;
    private final Node secondStart;

    public Grid(char[][] grid) {
        height = grid.length;
        width = grid[0].length;
        gridNodes = new Node[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gridNodes[i][j] = new Node(grid[i][j], i, j);
            }
        }

        List<Node> startAndFinish = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (gridNodes[i][j].isFinish()) {
                    startAndFinish.add(gridNodes[i][j]);
                }
            }
        }
        if (startAndFinish.size() != 2) {
            throw new RuntimeException("There are no two X");
        }
        firstStart = startAndFinish.get(0);
        secondStart = startAndFinish.get(1);
    }

    public Node getFirstStart() {
        return firstStart;
    }

    public Node getSecondStart() {
        return secondStart;
    }

    public boolean allNodesVisited() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (gridNodes[i][j].isNotVisited() && gridNodes[i][j].shouldGo()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Node> getPossibleNodesToGo(Node previousNode, Node currentNode) {
        List<Node> nextNodes = findNextNodes(previousNode, currentNode);
        return takePossibleNodesToGo(nextNodes, currentNode);
    }

    private List<Node> findNextNodes(Node previousNode, Node currentNode) {
        return switch (currentNode.getSymbol()) {
            case '-' -> goOnSameLine(currentNode, previousNode);
            case '|' -> goOnSameColumn(currentNode, previousNode);
            case '+' -> turn(currentNode, previousNode);
            case 'X' -> goAnyDirection(currentNode);
            default -> new ArrayList<>();
        };
    }

    private List<Node> goOnSameLine(Node nodeToVisit, Node nodeVisitedFrom) {
        List<Node> nodesToGo = new ArrayList<>();
        if (nodesOnSameLine(nodeToVisit, nodeVisitedFrom)) {
            if(shouldGoLeft(nodeToVisit, nodeVisitedFrom)){
                if (nodeToVisit.getJ()-1 >= 0) {
                    nodesToGo.add(gridNodes[nodeToVisit.getI()][nodeToVisit.getJ() - 1]);
                }
            }
            else{
                if (nodeToVisit.getJ()+1 < width) {
                    nodesToGo.add(gridNodes[nodeToVisit.getI()][nodeToVisit.getJ() + 1]);
                }
            }
        }
        return nodesToGo.stream().filter(Node::isNotVisited).filter(Node::shouldGo).collect(Collectors.toList());
    }

    private List<Node> goAnyDirection(Node nodeToVisit) {
        List<Node> nodesToGo = new ArrayList<>();
        if (nodeToVisit.getJ()-1 >= 0) {
            nodesToGo.add(gridNodes[nodeToVisit.getI()][nodeToVisit.getJ() - 1]);
        }
        if (nodeToVisit.getJ()+1 < width) {
            nodesToGo.add(gridNodes[nodeToVisit.getI()][nodeToVisit.getJ() + 1]);
        }
        if (nodeToVisit.getI()-1 >= 0) {
            nodesToGo.add(gridNodes[nodeToVisit.getI() - 1][nodeToVisit.getJ()]);
        }
        if (nodeToVisit.getI()+1 < height) {
            nodesToGo.add(gridNodes[nodeToVisit.getI() + 1][nodeToVisit.getJ()]);
        }
        return nodesToGo.stream().filter(Node::isNotVisited).filter(Node::shouldGo).collect(Collectors.toList());
    }
    private boolean shouldGoLeft(Node nodeToVisit, Node nodeVisitedFrom) {
        return nodeToVisit.getJ() < nodeVisitedFrom.getJ();
    }

    private boolean nodesOnSameLine(Node nodeToVisit, Node nodeVisitedFrom) {
        return nodeToVisit.getI() == nodeVisitedFrom.getI();
    }

    private List<Node> goOnSameColumn(Node nodeToVisit, Node nodeVisitedFrom) {
        List<Node> nodesToGo = new ArrayList<>();
        if (nodesOnSameColumn(nodeToVisit, nodeVisitedFrom)) {
            if(shouldGoDown(nodeToVisit, nodeVisitedFrom)){
                if (nodeToVisit.getI()-1 >= 0) {
                    nodesToGo.add(gridNodes[nodeToVisit.getI() - 1][nodeToVisit.getJ()]);
                }
            }
            else{
                if (nodeToVisit.getI()+1 < height) {
                    nodesToGo.add(gridNodes[nodeToVisit.getI() + 1][nodeToVisit.getJ()]);
                }
            }
        }
        return nodesToGo.stream().filter(Node::isNotVisited).filter(Node::shouldGo).collect(Collectors.toList());
    }

    private boolean shouldGoDown(Node nodeToVisit, Node nodeVisitedFrom) {
        return nodeToVisit.getI() < nodeVisitedFrom.getI();
    }

    private boolean nodesOnSameColumn(Node nodeToVisit, Node nodeVisitedFrom) {
        return nodeToVisit.getJ() == nodeVisitedFrom.getJ();
    }

    private List<Node> turn(Node nodeToVisit, Node nodeVisitedFrom) {
        List<Node> nodesToGo = new ArrayList<>();
        if (shouldGoUpAndDown(nodeToVisit, nodeVisitedFrom)) {
            if (nodeToVisit.getI()-1 >= 0) {
                nodesToGo.add(gridNodes[nodeToVisit.getI() - 1][nodeToVisit.getJ()]);
            }
            if (nodeToVisit.getI()+1 < height) {
                nodesToGo.add(gridNodes[nodeToVisit.getI() + 1][nodeToVisit.getJ()]);
            }
        }
        else {
            if (nodeToVisit.getJ()-1 >= 0) {
                nodesToGo.add(gridNodes[nodeToVisit.getI()][nodeToVisit.getJ() - 1]);
            }
            if (nodeToVisit.getJ()+1 < width) {
                nodesToGo.add(gridNodes[nodeToVisit.getI()][nodeToVisit.getJ() + 1]);
            }
        }
        return nodesToGo.stream().filter(Node::isNotVisited).filter(Node::shouldGo).collect(Collectors.toList());
    }

    private boolean shouldGoUpAndDown(Node nodeToVisit, Node nodeVisitedFrom) {
        return nodesOnSameLine(nodeToVisit, nodeVisitedFrom);
    }

    private List<Node> takePossibleNodesToGo(List<Node> nextNodes, Node nodeGoFrom) {
        List<Node> possibleNodesToGo = new ArrayList<>();
        for(Node nodeToGo: nextNodes){
            if(nodeIsPossibleToGo(nodeToGo, nodeGoFrom)){
                possibleNodesToGo.add(nodeToGo);
            }
        }
        return possibleNodesToGo;
    }

    private boolean nodeIsPossibleToGo(Node nodeToGo, Node nodeGoFrom) {
        return switch (nodeToGo.getSymbol()) {
            case '-' -> nodesOnSameLine(nodeToGo, nodeGoFrom);
            case '|' -> nodesOnSameColumn(nodeToGo, nodeGoFrom);
            default -> true;
        };
    }


    public void makeAllNodesNotVisited() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gridNodes[i][j].setVisited(false);
            }
        }
    }
}

class Node {
    private final char symbol;
    private final int i;
    private final int j;
    private boolean isVisited;

    public Node(char symbol, int i, int j) {
        this.symbol = symbol;
        this.i = i;
        this.j = j;
    }

    public boolean isNotVisited() {
        return !isVisited;
    }

    public boolean shouldGo(){
        return symbol != ' ';
    }

    public boolean isFinish() {
        return symbol == 'X';
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}