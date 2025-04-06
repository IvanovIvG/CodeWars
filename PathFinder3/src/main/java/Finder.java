import java.util.*;

/**
 * @author Ivan Ivanov
 **/
public class Finder {
    //Nets of maze
    private static List<Net> mazeNets;
    private static Net start;
    private static Net finish;

    //possible jumps from achieved nets to unachieved
    private static List<Jump> possibleJumps;

    static int pathFinder(String maze) {
        if (mazeIsTrivial(maze)) return 0;
        createStaticCollections(maze);

        while (true) {
            Jump bestJump = findBestJump();
            makeNewJump(bestJump);
            if(finishAchievedAfterJump(bestJump)){
                break;
            }
        }

        return numberOfClimbsToAchieveFinish();
    }

    private static boolean mazeIsTrivial(String maze) {
        return maze.length() == 1;
    }

    private static void createStaticCollections(String maze) {
        mazeNets = NetsCreator.createNets(maze);
        start = mazeNets.get(0);
        finish = mazeNets.get(mazeNets.size() - 1);

        start.setAchieved(true);

        possibleJumps = createInitialJumps();
    }

    private static List<Jump> createInitialJumps() {
        List<Net> closeNetsToStart = start.getCloseNets();
        return createJumpsFromNetToNets(start, closeNetsToStart);
    }

    private static Jump findBestJump() {
        Jump bestJump = new Jump(null, null, 1000);
        for (int i = 0; i < possibleJumps.size(); i++) {
            Jump jump = possibleJumps.get(i);
            if(jump.numberOfClimbsToAchieveToNet() < bestJump.numberOfClimbsToAchieveToNet()){
                bestJump = jump;
            }
        }
        return bestJump;
    }

    private static List<Jump> createJumpsFromNetToNets(Net fromNet, List<Net> netsToJump) {
        List<Jump> jumps = new ArrayList<>();
        for (Net toNet : netsToJump) {
            int numberOfClimbs = Math.abs(fromNet.getAltitude() - toNet.getAltitude())
                    + fromNet.getNumberOfClimbsToAchieve();
            Jump jump = new Jump(fromNet, toNet, numberOfClimbs);
            jumps.add(jump);
        }
        return jumps;
    }

    private static void makeNewJump(Jump bestJump) {
        removeImpossibleJumps(bestJump);
        achieveNewNet(bestJump);
        addNewJumps(bestJump);
    }

    private static void removeImpossibleJumps(Jump jumpToMake) {
        Net netToJump = jumpToMake.netToJump();
        List<Jump> impossibleJumps = new ArrayList<>();
        for (int i = 0; i < possibleJumps.size(); i++) {
            Jump jump = possibleJumps.get(i);
            if(jump.netToJump() == netToJump){
                impossibleJumps.add(jump);
            }
        }
        for (int i = 0; i < impossibleJumps.size(); i++) {
            Jump jump = impossibleJumps.get(i);
            possibleJumps.remove(jump);
        }
    }

    private static void addNewJumps(Jump jumpToMake) {
        Net netToJump = jumpToMake.netToJump();
        List<Net> possibleNetsJumpTo = netToJump.getCloseNets();
        List<Net> newNetsJumpTo = new ArrayList<>();
        for (int i = 0; i < possibleNetsJumpTo.size(); i++) {
            Net net = possibleNetsJumpTo.get(i);
            if(!net.isAchieved()){
                newNetsJumpTo.add(net);
            }
        }
        List<Jump> newJumps = createJumpsFromNetToNets(netToJump, newNetsJumpTo);
        possibleJumps.addAll(newJumps);
    }

    private static void achieveNewNet(Jump jump) {
        Net netToAchieve = jump.netToJump();
        netToAchieve.setNumberOfClimbsToAchieve(jump.numberOfClimbsToAchieveToNet());
        netToAchieve.setAchieved(true);
    }

    private static boolean finishAchievedAfterJump(Jump jump) {
        return jump.netToJump() == finish;
    }

    private static int numberOfClimbsToAchieveFinish() {
        return finish.getNumberOfClimbsToAchieve();
    }
}

record Jump(Net netFromJump, Net netToJump, int numberOfClimbsToAchieveToNet) {
}

class NetsCreator {
    private static List<Net> nets;
    private static int sideLength;

    public static List<Net> createNets(String maze) {
        maze = cleanMaze(maze);
        sideLength = (int) Math.sqrt(maze.length());
        nets = new ArrayList<>(sideLength * sideLength);
        fillInNetsFromString(maze);
        fillInNetsConnections();

        return nets;
    }

    private static String cleanMaze(String maze) {
        return maze.replaceAll("\\n", "");
    }

    private static void fillInNetsFromString(String maze) {
        char[] charNets = maze.toCharArray();
        for (char charNet : charNets) {
            int netNumber = Character.getNumericValue(charNet);
            nets.add(new Net(netNumber));
        }
    }

    private static void fillInNetsConnections() {
        if (sideLength >= 2) {
            fillInCornerNetsConnections();
            fillInUpLineNetsConnections();
        }

        if (sideLength >= 3) {
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
        int lastLeftColumnNetIndex = sideLength * (sideLength - 2);
        for (int netIndex = firstLeftNetNetIndex; netIndex <= lastLeftColumnNetIndex; netIndex += sideLength) {
            fillInNetConnections(netIndex, NetPosition.LeftColumn);
        }
    }

    private static void fillInCentralNetsConnections() {
        for (int lineIndex = 1; lineIndex < sideLength - 1; lineIndex++) {
            for (int columnIndex = 1; columnIndex < sideLength - 1; columnIndex++) {
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
        if (netPosition.isUpNetExists()) {
            int upNetIndex = netIndex - sideLength;
            return nets.get(upNetIndex);
        } else {
            return null;
        }
    }

    private static Net getRightNet(int netIndex, NetPosition netPosition) {
        if (netPosition.isRightNetExists()) {
            int rightNetIndex = netIndex + 1;
            return nets.get(rightNetIndex);
        } else {
            return null;
        }
    }

    private static Net getDownNet(int netIndex, NetPosition netPosition) {
        if (netPosition.isDownNetExists()) {
            int downNetIndex = netIndex + sideLength;
            return nets.get(downNetIndex);
        } else {
            return null;
        }
    }

    private static Net getLeftNet(int netIndex, NetPosition netPosition) {
        if (netPosition.isLeftNetExists()) {
            int leftNetIndex = netIndex - 1;
            return nets.get(leftNetIndex);
        } else {
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
    private final int altitude;
    private int numberOfClimbsToAchieve;
    private Net upNet;
    private Net downNet;
    private Net leftNet;
    private Net rightNet;
    private List<Net> closeNets;
    private boolean achieved;

    public Net(int altitude) {
        this.altitude = altitude;
        numberOfClimbsToAchieve = 0;
        closeNets = null;
        achieved = false;
    }

    public List<Net> getCloseNets() {
        if (closeNets == null) {
            closeNets = new ArrayList<>();
            closeNets.add(upNet);
            closeNets.add(leftNet);
            closeNets.add(rightNet);
            closeNets.add(downNet);
            closeNets = closeNets.stream().filter(Objects::nonNull).toList();
        }
        return closeNets;
    }

    public int getAltitude() {
        return altitude;
    }


    public int getNumberOfClimbsToAchieve() {
        return numberOfClimbsToAchieve;
    }

    public void setNumberOfClimbsToAchieve(int numberOfClimbsToAchieve) {
        this.numberOfClimbsToAchieve = numberOfClimbsToAchieve;
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

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}

