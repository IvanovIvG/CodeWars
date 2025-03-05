package ru.ivanov;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan Ivanov
 **/
public class Finder {
    private static Set<Net> achievableNets;
    private static Set<Net> achievableNetsBorder;
    private static Set<Net> achievableNetsBorderNeighbours;

    static boolean pathFinder(String maze) {
        String cleanMaze = cleanMaze(maze);
        if(cleanMaze.length() == 1) return true;
        Maze board = MazeCreator.createMaze(cleanMaze);

        achievableNets = createAchievableNets(board);
        achievableNetsBorder = new HashSet<>(achievableNets);
        achievableNetsBorderNeighbours = new HashSet<>();

        while(thereAreNetsToAchieve()){
            addNewNetsToAchievableNets();
        }

        return achievableNets.contains(board.getFinish());
    }

    private static String cleanMaze(String maze) {
        return maze.replaceAll("\\n","");
    }

    private static Set<Net> createAchievableNets(Maze board) {
        achievableNets = new HashSet<>();
        achievableNets.add(board.getStart());
        return achievableNets;
    }

    private static boolean thereAreNetsToAchieve() {
        return !achievableNetsBorder.isEmpty();
    }

    private static void addNewNetsToAchievableNets() {
        updateAchievableNetsBorder();
        achievableNets.addAll(achievableNetsBorder);
    }

    private static void updateAchievableNetsBorder() {
        achievableNetsBorderNeighbours.clear();
        for(Net borderNet: achievableNetsBorder){
            addNewNetsToAchievableNetsBorderNeighbours(borderNet);
        }
        achievableNetsBorder.clear();
        achievableNetsBorder.addAll(achievableNetsBorderNeighbours);
    }

    private static void addNewNetsToAchievableNetsBorderNeighbours(Net borderNet) {
        addUpNet(borderNet);
        addRightNet(borderNet);
        addDownNet(borderNet);
        addLeftNet(borderNet);
    }

    private static void addUpNet(Net borderNet) {
        Net upNet = borderNet.goUp();
        if(netShouldBeAdded(upNet)){
            achievableNetsBorderNeighbours.add(upNet);
        }
    }

    private static void addRightNet(Net borderNet) {
        Net rightNet = borderNet.goRight();
        if(netShouldBeAdded(rightNet)){
            achievableNetsBorderNeighbours.add(rightNet);
        }
    }

    private static void addDownNet(Net borderNet) {
        Net downNet = borderNet.goDown();
        if(netShouldBeAdded(downNet)){
            achievableNetsBorderNeighbours.add(downNet);
        }
    }

    private static void addLeftNet(Net borderNet) {
        Net leftNet = borderNet.goLeft();
        if(netShouldBeAdded(leftNet)){
            achievableNetsBorderNeighbours.add(leftNet);
        }
    }

    private static boolean netShouldBeAdded(Net net){
        return net != null && !achievableNets.contains(net) && !achievableNetsBorder.contains(net);
    }
}

class Maze{
    private final List<Net> nets;;

    public Maze(int sideLength) {
        nets = new ArrayList<>(sideLength*sideLength);
    }

    public Net getStart(){
        return nets.stream().filter(Net::isStart).findFirst().orElseThrow();
    }

    public Net getFinish() {
        return nets.stream().filter(Net::isFinish).findFirst().orElseThrow();
    }

    public List<Net> getNets() {
        return nets;
    }
}

class MazeCreator{
    private static List<Net> nets;
    private static int sideLength;

    public static Maze createMaze(String maze){
        sideLength = (int) Math.sqrt(maze.length());
        Maze board = new Maze(sideLength);

        nets = board.getNets();
        fillInNetsFromString(maze);
        fillInNetsConnections();

        return board;
    }

    private static void fillInNetsFromString(String maze){
        nets.add(new Net(NetType.Start));
        char[] charNets = maze.toCharArray();
        for(int netIndex = 1; netIndex < charNets.length-1; netIndex++){
            NetType netType = getNetType(charNets[netIndex]);
            nets.add(new Net(netType));
        }
        nets.add(new Net(NetType.Finish));
    }

    private static NetType getNetType(char net){
        return switch (net) {
            case '.' -> NetType.Empty;
            case 'W' -> NetType.Wall;
            default -> throw new IllegalStateException("Unexpected value: " + net);
        };
    }

    private static void fillInNetsConnections() {
        if (sideLength>=2) {
            fillInCornerNetsConnections();
            fillInUpLineNetsConnections();
        }

        if (sideLength>=3) {
            fillInRightColumnNetsConnections();
            fillInDownLineNetsConnections();
            fillInLeftColumnNetsConnections();
            fillInCentralNetsConnections();
        }
    }

    private static void fillInCornerNetsConnections() {
        int leftUpNetIndex = 0;
        int rightUpNetIndex = sideLength - 1;
        int leftDownNetIndex = sideLength * (sideLength - 1);
        int rightDownNetIndex = sideLength * sideLength - 1;

        fillInNetConnections(leftUpNetIndex, NetPosition.LeftUp);
        fillInNetConnections(rightUpNetIndex, NetPosition.RightUp);
        fillInNetConnections(rightDownNetIndex, NetPosition.RightDown);
        fillInNetConnections(leftDownNetIndex, NetPosition.LeftDown);
    }

    private static void fillInUpLineNetsConnections() {
        int firstUpLineNetIndex = 1;
        int lastUpLineNetIndex = sideLength - 1;
        for (int netIndex = firstUpLineNetIndex; netIndex <= lastUpLineNetIndex; netIndex++) {
            fillInNetConnections(netIndex, NetPosition.UpLine);
        }
    }

    private static void fillInRightColumnNetsConnections() {
        int firstRightColumnNetIndex = 2 * sideLength - 1;
        int lastRightColumnNetIndex = sideLength * (sideLength - 1) - 1;
        for (int netIndex = firstRightColumnNetIndex; netIndex <= lastRightColumnNetIndex; netIndex += sideLength) {
            fillInNetConnections(netIndex, NetPosition.RightColumn);
        }
    }

    private static void fillInDownLineNetsConnections() {
        int firstDownLineNetIndex = sideLength * (sideLength - 1) + 1;
        int lastDownLineNetIndex = sideLength * sideLength - 2;
        for (int netIndex = firstDownLineNetIndex; netIndex <= lastDownLineNetIndex; netIndex++) {
            fillInNetConnections(netIndex, NetPosition.DownLine);
        }
    }

    private static void fillInLeftColumnNetsConnections() {
        int firstLeftNetNetIndex = sideLength;
        int lastLeftColumnNetIndex = sideLength * (sideLength - 2) ;
        for (int netIndex = firstLeftNetNetIndex; netIndex <= lastLeftColumnNetIndex; netIndex += sideLength) {
            fillInNetConnections(netIndex, NetPosition.LeftColumn);
        }
    }

    private static void fillInCentralNetsConnections() {
        for (int lineIndex = 1; lineIndex < sideLength - 1; lineIndex++) {
            for(int columnIndex = 1; columnIndex < sideLength - 1; columnIndex++) {
                int netIndex = lineIndex * sideLength + columnIndex;
                fillInNetConnections(netIndex, NetPosition.Central);
            }
        }
    }

    private static void fillInNetConnections(int netIndex, NetPosition netPosition) {
        Net net = nets.get(netIndex);

        Net upNet = getUpNet(netIndex, netPosition);
        Net rightNet = getRightNet(netIndex, netPosition);
        Net downNet = getDownNet(netIndex, netPosition);
        Net leftNet = getLeftNet(netIndex, netPosition);

        net.setUpNet(upNet);
        net.setRightNet(rightNet);
        net.setDownNet(downNet);
        net.setLeftNet(leftNet);
    }

    private static Net getUpNet(int netIndex, NetPosition netPosition) {
        if(netPosition.isUpNetExists()){
            int upNetIndex = netIndex - sideLength;
            return nets.get(upNetIndex);
        }
        else{
            return null;
        }
    }

    private static Net getRightNet(int netIndex, NetPosition netPosition) {
        if(netPosition.isRightNetExists()){
            int rightNetIndex = netIndex + 1;
            return nets.get(rightNetIndex);
        }
        else{
            return null;
        }
    }

    private static Net getDownNet(int netIndex, NetPosition netPosition) {
        if(netPosition.isDownNetExists()){
            int downNetIndex = netIndex + sideLength;
            return nets.get(downNetIndex);
        }
        else{
            return null;
        }
    }

    private static Net getLeftNet( int netIndex, NetPosition netPosition) {
        if(netPosition.isLeftNetExists()){
            int leftNetIndex = netIndex - 1;
            return nets.get(leftNetIndex);
        }
        else{
            return null;
        }
    }

    enum NetPosition {
        LeftUp(false, true, true, false),
        RightUp(false, false, true, true),
        RightDown(true, false, false, true),
        LeftDown(true, true, false, false),
        UpLine(false, true, true, true),
        RightColumn(true, false, true, true),
        DownLine(true, true, false, true),
        LeftColumn(true, true, true, false),
        Central(true, true, true, true);

        private final boolean upNetExists;
        private final boolean rightNetExists;
        private final boolean downNetExists;
        private final boolean leftNetExists;

        NetPosition(boolean upNetExists, boolean rightNetExists, boolean downNetExists, boolean leftNetExists) {
            this.upNetExists = upNetExists;
            this.rightNetExists = rightNetExists;
            this.downNetExists = downNetExists;
            this.leftNetExists = leftNetExists;
        }

        public boolean isUpNetExists() {
            return upNetExists;
        }

        public boolean isRightNetExists() {
            return rightNetExists;
        }

        public boolean isDownNetExists() {
            return downNetExists;
        }

        public boolean isLeftNetExists() {
            return leftNetExists;
        }
    }
}

class Net {
    private final NetType type;
    private Net upNet;
    private Net downNet;
    private Net leftNet;
    private Net rightNet;

    public Net(NetType type) {
        this.type = type;
    }

    public Net goUp(){
        if(type==NetType.Wall){
            return null;
        }
        return upNet;
    }

    public Net goDown(){
        if(type==NetType.Wall){
            return null;
        }
        return downNet;
    }

    public Net goLeft(){
        if(type==NetType.Wall){
            return null;
        }
        return leftNet;
    }

    public Net goRight(){
        if(type==NetType.Wall){
            return null;
        }
        return rightNet;
    }

    public boolean isStart() {
        return type==NetType.Start;
    }

    public boolean isFinish(){
        return type==NetType.Finish;
    }

    public void setUpNet(Net upNet) {
        this.upNet = upNet;
    }

    public void setDownNet(Net downNet) {
        this.downNet = downNet;
    }

    public void setLeftNet(Net leftNet) {
        this.leftNet = leftNet;
    }

    public void setRightNet(Net rightNet) {
        this.rightNet = rightNet;
    }
}

enum NetType{
    Empty, Wall, Start, Finish;
}
