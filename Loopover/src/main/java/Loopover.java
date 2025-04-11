import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ivan Ivanov
 **/
public class Loopover {
    private static Board board;
    private static Board solutiondBoard;
    private static List<String> solution;

    private static int boardHeight;
    private static int boardWidth;

    public static List<String> solve(char[][] mixedUpBoard, char[][] solvedBoard) {
        if (alreadySolved(mixedUpBoard, solvedBoard)) {
            return Collections.emptyList();
        }
        solution = new ArrayList<>();
        board = new Board(solution, mixedUpBoard);
        solutiondBoard = new Board(Collections.emptyList(), solvedBoard);
        boardHeight = mixedUpBoard.length;
        boardWidth = mixedUpBoard[0].length;

        solveFirstLines();
        solveLastLine();
        solveRightColumn();

        if (boardCanBeSolvedByCornerPermutations()) {
            solveByCornerPermutations();
        } else {
            solveByEdgePermutations();
        }

        board.printBoard();
        return solution;
    }

    private static boolean alreadySolved(char[][] mixedUpBoard, char[][] notMixedUpBoard) {
        int height = mixedUpBoard.length;
        int width = mixedUpBoard[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (mixedUpBoard[i][j] != notMixedUpBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void solveFirstLines() {
        for (int lineIndex = 0; lineIndex < boardHeight - 1; lineIndex++) {
            solveLine(lineIndex);
        }
    }

    private static void solveLine(int lineIndex) {
        List<Net> symbolsInRightOrder = solutiondBoard.getLine(lineIndex).subList(0, boardWidth - 1);
        for (Net rightSymbol : symbolsInRightOrder) {
            Net symbol = board.findOnBoard(rightSymbol);
            putSymbolInCorrectPositionOnLine(symbol, lineIndex);
        }
    }

    private static void putSymbolInCorrectPositionOnLine(Net symbol, int lineIndex) {
        if (symbolIsOnCurrentLine(symbol, lineIndex)) {
            putSymbolInRightColumnOnLineLower(symbol, lineIndex);
        } else {
            putSymbolInRightColumnOnAnyLine(symbol);
        }
        putSymbolToRightColumnOnCurrentLine(symbol, lineIndex);
        board.moveLineLeft(lineIndex, 1);
    }

    private static boolean symbolIsOnCurrentLine(Net symbol, int lineIndex) {
        return symbol.getI() == lineIndex;
    }

    private static void putSymbolInRightColumnOnLineLower(Net symbol, int lineIndex) {
        //put in right column
        int movesToRightColumn = boardWidth - symbol.getJ() - 1;
        board.moveLineRight(lineIndex, movesToRightColumn);

        //put on line lower
        int rightColumnIndex = boardWidth - 1;
        board.moveColumnDown(rightColumnIndex, 1);

        //put line in previous position
        board.moveLineLeft(lineIndex, movesToRightColumn);
    }

    private static void putSymbolInRightColumnOnAnyLine(Net symbol) {
        int lineIndex = symbol.getI();
        int movesToRightColumn = boardWidth - symbol.getJ() - 1;
        board.moveLineRight(lineIndex, movesToRightColumn);
    }

    private static void putSymbolToRightColumnOnCurrentLine(Net symbol, int lineIndex) {
        int movesToCurrentLine;
        int rightColumnIndex = boardWidth - 1;
        if (symbolIsHigherCurrentLine(symbol, lineIndex)) {
            movesToCurrentLine = lineIndex - symbol.getI();
            board.moveColumnDown(rightColumnIndex, movesToCurrentLine);
        } else {
            movesToCurrentLine = symbol.getI() - lineIndex;
            board.moveColumnUp(rightColumnIndex, movesToCurrentLine);
        }
    }

    private static boolean symbolIsHigherCurrentLine(Net symbol, int lineIndex) {
        return symbol.getI() < lineIndex;
    }

    private static void solveLastLine() {
        int lineIndex = boardHeight - 1;
        List<Net> symbolsInRightOrder = solutiondBoard.getLine(lineIndex).subList(0, boardWidth - 2);
        for (Net rightSymbol : symbolsInRightOrder) {
            Net symbol = board.findOnBoard(rightSymbol);
            putSymbolInCorrectPositionOnLastLine(symbol, lineIndex);
        }
        board.moveLineLeft(lineIndex, 1);
    }

    private static void putSymbolInCorrectPositionOnLastLine(Net symbol, int lineIndex) {
        if (symbolIsOnCurrentLine(symbol, lineIndex)) {
            putSymbolToRightColumnOnLineUp(symbol, lineIndex);
        }
        putSymbolToRightColumnOnCurrentLine(symbol, lineIndex);
        board.moveLineLeft(lineIndex, 1);
    }

    private static void putSymbolToRightColumnOnLineUp(Net symbol, int lineIndex) {
        //put symbol to right column
        int movesToRightColumn = boardWidth - symbol.getJ() - 1;
        board.moveLineRight(lineIndex, movesToRightColumn);

        //put symbol on line up
        int rightColumnIndex = boardWidth - 1;
        board.moveColumnUp(rightColumnIndex, 1);

        //put line in previous position
        board.moveLineLeft(lineIndex, movesToRightColumn);
    }

    private static void solveRightColumn() {
        int columnIndex = boardWidth - 1;
        List<Net> symbolsInRightOrder = solutiondBoard.getColumn(columnIndex).subList(0, boardHeight - 2);
        for (Net rightSymbol : symbolsInRightOrder) {
            Net symbol = board.findOnBoard(rightSymbol);
            putSymbolInCorrectPositionInRightColumn(symbol, columnIndex);

        }
        board.moveColumnUp(columnIndex, 1);
    }

    private static void putSymbolInCorrectPositionInRightColumn(Net symbol, int columnIndex) {
        if (symbolIsInRightColumnOnLastLine(symbol, columnIndex)) {
            board.moveColumnUp(columnIndex, 1);
            return;
        }
        if (symbolIsInCurrentColumn(symbol, columnIndex)) {
            moveToColumnLeftOnLastLine(symbol, columnIndex);
        }
        moveToRightColumnInCorrectPosition(columnIndex);
    }

    private static boolean symbolIsInRightColumnOnLastLine(Net symbol, int columnIndex) {
        return symbolIsInCurrentColumn(symbol, columnIndex) && symbolIsOnLastLine(symbol);
    }

    private static boolean symbolIsInCurrentColumn(Net symbol, int columnIndex) {
        return symbol.getJ() == columnIndex;
    }

    private static boolean symbolIsOnLastLine(Net symbol) {
        int lastLineIndex = boardHeight - 1;
        return symbol.getI() == lastLineIndex;
    }

    private static void moveToColumnLeftOnLastLine(Net symbol, int columnIndex) {
        //prepare net for symbol in column left
        int lastLineIndex = boardHeight - 1;
        board.moveLineRight(lastLineIndex, 1);

        //put symbol to last line
        int movesToLastLine = boardHeight - symbol.getI() - 1;
        board.moveColumnDown(columnIndex, movesToLastLine);

        //put symbol in column left
        board.moveLineLeft(lastLineIndex, 1);

        //put column in previous position
        board.moveColumnUp(columnIndex, movesToLastLine);
    }

    private static void moveToRightColumnInCorrectPosition(int columnIndex) {
        //put symbol in right column
        int lastLineIndex = boardHeight - 1;
        board.moveLineRight(lastLineIndex, 1);

        //put symbol in right position in column
        board.moveColumnUp(columnIndex, 1);

        //put line in previous position
        board.moveLineLeft(lastLineIndex, 1);
    }

    private static boolean boardCanBeSolvedByCornerPermutations() {
        return numberOfCornerNetsOnCorrectPlaces() != 1;
    }

    private static int numberOfCornerNetsOnCorrectPlaces() {
        int numberOfCoincidence = 0;
        List<Net> boardCornerNets = board.getCornerNetsInSpecificOrder();
        List<Net> solvedBoardCornerNets = solutiondBoard.getCornerNetsInSpecificOrder();
        for (int i = 0; i < 3; i++) {
            Net boardCornerNet = boardCornerNets.get(i);
            Net solvedBoardCornerNet = solvedBoardCornerNets.get(i);
            if (boardCornerNet.equals(solvedBoardCornerNet)) {
                numberOfCoincidence++;
            }
        }
        return numberOfCoincidence;
    }

    private static void solveByCornerPermutations() {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;
        int counter = 0;
        while (gameGoOn()) {
            if (counter > 100) {
                solution = null;
                return;
            }
            board.moveLineRight(lastLineIndex, 1);
            board.moveColumnDown(rightColumnIndex, 1);
            board.moveLineLeft(lastLineIndex, 1);
            board.moveColumnUp(rightColumnIndex, 1);
            counter++;
        }
    }

    private static boolean gameGoOn() {
        return numberOfCornerNetsOnCorrectPlaces() != 3;
    }

    private static void solveByEdgePermutations() {
        if (oneSideIsTwoSize()) {
            moveTwoSizeSide();
            solveByCornerPermutations();
            return;
        }

        if (oneSideIsOneMoreThenOther()) {
            swapTwoCornerNetsWhenOneSideIsOneMoreThenOther();
            solveByCornerPermutations();
            return;
        }

        if (oneSideIsEven()) {
            swapTwoCornerNetsOnEvenSide();
            solveByCornerPermutations();
            return;
        }

        solution = null;
    }

    private static boolean oneSideIsTwoSize() {
        return boardWidth == 2 || boardHeight == 2;
    }

    private static void moveTwoSizeSide() {
        if (boardWidth == 2) {
            int lastLineIndex = boardHeight - 1;
            board.moveLineLeft(lastLineIndex, 1);
        } else {
            int lastColumnIndex = boardWidth - 1;
            board.moveColumnUp(lastColumnIndex, 1);
        }
    }

    private static boolean oneSideIsOneMoreThenOther() {
        return Math.abs(boardHeight - boardWidth) == 1;
    }

    private static void swapTwoCornerNetsWhenOneSideIsOneMoreThenOther() {
        if (boardIsWide()) {
            putCornerNetInLeftDownCorner();
            bringAnotherNetToLeftDownCorner();
            putNetsFromLeftDownCornerToCorrectPositions();
        } else {
            putCornerNetInRightUpCorner();
            bringAnotherNetToRightUpCorner();
            putNetsFromRightUpCornerToCorrectPositions();
        }
    }

    private static boolean boardIsWide() {
        return boardWidth > boardHeight;
    }

    private static void putCornerNetInLeftDownCorner() {
        int lastLineIndex = boardHeight - 1;
        board.moveLineRight(lastLineIndex, 1);
    }

    private static void bringAnotherNetToLeftDownCorner() {
        int numberOfNetsFromCenterToMove = boardWidth - 2;
        int movesTimes = boardWidth - 2;
        for (int i = 0; i < movesTimes; i++) {
            roundEdgePartOnClockWise(numberOfNetsFromCenterToMove);
        }
    }

    private static void putNetsFromLeftDownCornerToCorrectPositions() {
        int movesTimes = boardWidth - 2;
        for (int i = 0; i < movesTimes; i++) {
            roundEdgeOnAntiClockWise();
        }
    }

    private static void putCornerNetInRightUpCorner() {
        int rightColumnIndex = boardWidth - 1;
        board.moveColumnDown(rightColumnIndex, 1);
    }

    private static void bringAnotherNetToRightUpCorner() {
        int numberOfNetsFromCenterToMove = boardHeight - 2;
        int movesTimes = boardHeight - 2;
        for (int i = 0; i < movesTimes; i++) {
            roundEdgePartOnAntiClockWise(numberOfNetsFromCenterToMove);
        }
    }

    private static void putNetsFromRightUpCornerToCorrectPositions() {
        int movesTimes = boardHeight - 2;
        for (int i = 0; i < movesTimes; i++) {
            roundEdgeOnClockWise();
        }
    }

    private static void roundEdgeOnClockWise() {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;
        board.moveLineLeft(lastLineIndex, 1);
        board.moveColumnDown(rightColumnIndex, 1);
    }

    private static void roundEdgeOnAntiClockWise() {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;
        board.moveColumnUp(rightColumnIndex, 1);
        board.moveLineRight(lastLineIndex, 1);
    }

    private static void roundEdgePartOnClockWise(int numberOfNetsFromCenter) {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;
        for (int i = 0; i < numberOfNetsFromCenter; i++) {
            board.moveColumnDown(rightColumnIndex, 1);
            board.moveLineRight(lastLineIndex, 1);
        }
        for (int i = 0; i < numberOfNetsFromCenter; i++) {
            board.moveColumnUp(rightColumnIndex, 1);
            board.moveLineLeft(lastLineIndex, 1);
        }
    }

    private static void roundEdgePartOnAntiClockWise(int numberOfNetsFromCenter) {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;
        for (int i = 0; i < numberOfNetsFromCenter; i++) {
            board.moveLineRight(lastLineIndex, 1);
            board.moveColumnDown(rightColumnIndex, 1);
        }
        for (int i = 0; i < numberOfNetsFromCenter; i++) {
            board.moveLineLeft(lastLineIndex, 1);
            board.moveColumnUp(rightColumnIndex, 1);
        }
    }

    private static boolean oneSideIsEven() {
        return (boardWidth % 2 == 0) || (boardHeight % 2 == 0);
    }

    private static void swapTwoCornerNetsOnEvenSide() {
        if (columnIsEven()) {
            swapTwoCornerNetsOnEvenColumn();
        } else {
            swapTwoCornerNetsOnEvenLine();
        }
    }

    private static boolean columnIsEven() {
        return boardHeight % 2 == 0;
    }

    private static void swapTwoCornerNetsOnEvenColumn() {
        int numberOfIterations = boardHeight - 3;
        for (int i = 0; i < numberOfIterations; i++) {
            iterateCornerNetsSwapOnEvenColumn();
        }
        roundRightDownCornerOnClockWise();
    }

    private static void iterateCornerNetsSwapOnEvenColumn() {
        int rightColumnIndex = boardWidth - 1;
        int downMoves = boardHeight - 4;

        board.moveColumnDown(rightColumnIndex, 2);
        roundRightColumnWithCornerNetsWithoutCentralNetOnClockWise(1);
        board.moveColumnDown(rightColumnIndex, downMoves);
        roundRightDownCornerOnClockWise();
        roundRightColumnWithCornerNetsWithoutCentralNetOnClockWise(2);
    }

    private static void swapTwoCornerNetsOnEvenLine() {
        int numberOfIterations = boardWidth - 3;
        for (int i = 0; i < numberOfIterations; i++) {
            iterateCornerNetsSwapOnEvenLine();
        }
        roundRightDownCornerOnAntiClockWise();
    }

    private static void iterateCornerNetsSwapOnEvenLine() {
        int lastLineIndex = boardHeight - 1;
        int rightMoves = boardWidth - 4;

        board.moveLineRight(lastLineIndex, 2);
        roundLastLineWithCornerNetsWithoutCentralNetOnAntiClockWise(1);
        board.moveLineRight(lastLineIndex, rightMoves);
        roundRightDownCornerOnAntiClockWise();
        roundLastLineWithCornerNetsWithoutCentralNetOnAntiClockWise(2);
    }

    private static void roundRightDownCornerOnClockWise() {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;

        board.moveColumnDown(rightColumnIndex, 1);
        board.moveLineRight(lastLineIndex, 1);
        board.moveColumnUp(rightColumnIndex, 1);
        board.moveLineLeft(lastLineIndex, 1);
    }

    private static void roundRightDownCornerOnAntiClockWise() {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;

        board.moveLineRight(lastLineIndex, 1);
        board.moveColumnDown(rightColumnIndex, 1);
        board.moveLineLeft(lastLineIndex, 1);
        board.moveColumnUp(rightColumnIndex, 1);

    }

    private static void roundRightColumnWithCornerNetsWithoutCentralNetOnClockWise(int moves) {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;

        for (int i = 0; i < moves; i++) {
            board.moveLineRight(lastLineIndex, 1);
            board.moveColumnDown(rightColumnIndex, 1);
            board.moveLineLeft(lastLineIndex, 1);
        }
    }

    private static void roundLastLineWithCornerNetsWithoutCentralNetOnAntiClockWise(int moves) {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;

        for (int i = 0; i < moves; i++) {
            board.moveColumnDown(rightColumnIndex, 1);
            board.moveLineRight(lastLineIndex, 1);
            board.moveColumnUp(rightColumnIndex, 1);
        }
    }

}

class Board {
    private final List<String> solution;
    private final List<Net> board;
    private final int boardHeight;
    private final int boardWidth;


    public Board(List<String> solution, char[][] board) {
        this.solution = solution;
        this.board = new ArrayList<>();
        boardHeight = board.length;
        boardWidth = board[0].length;

        Net.setMaxI(boardHeight - 1);
        Net.setMaxJ(boardWidth - 1);
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                Net nextNet = new Net(board[i][j], i, j);
                this.board.add(nextNet);
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                int line = i;
                int column = j;
                board.stream().filter((Net net) -> net.getI() == line).filter((Net net) -> net.getJ() == column).forEach(System.out::print);
            }
            System.out.println();
        }
        System.out.println();
    }

    public List<Net> getLine(int lineIndex) {
        if (lineIndex < 0 || lineIndex > boardHeight - 1) throw new IllegalArgumentException("Incorrect line number");
        return board.stream().filter((Net net) -> net.getI() == lineIndex).sorted(Comparator.comparingInt(Net::getJ))
                .collect(Collectors.toList());
    }

    public List<Net> getColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex > boardWidth - 1)
            throw new IllegalArgumentException("Incorrect line number");
        return board.stream().filter((Net net) -> net.getJ() == columnIndex).sorted(Comparator.comparingInt(Net::getI))
                .collect(Collectors.toList());
    }

    public Net findOnBoard(Net netToFind) {
        for (Net net : board) {
            if (net.equals(netToFind)) {
                return net;
            }
        }
        throw new RuntimeException("Element not found");
    }

    public List<Net> getCornerNetsInSpecificOrder() {
        int lastLineIndex = boardHeight - 1;
        int rightColumnIndex = boardWidth - 1;
        List<Net> cornerNets = new ArrayList<>(3);

        cornerNets.add(this.getColumn(rightColumnIndex).get(lastLineIndex - 1));
        cornerNets.add(this.getLine(lastLineIndex).get(rightColumnIndex - 1));
        cornerNets.add(this.getLine(lastLineIndex).get(rightColumnIndex));
        return cornerNets;
    }

    public void moveLineLeft(int lineIndex, int moves) {
        if (lineIndex < 0 || lineIndex > boardHeight - 1) throw new IllegalArgumentException("Incorrect line number");
        for (int i = 0; i < moves; i++) {
            board.stream().filter((Net net) -> net.getI() == lineIndex).forEach(Net::moveLeft);
            solution.add("L" + lineIndex);
        }
    }

    public void moveLineRight(int lineIndex, int moves) {
        if (lineIndex < 0 || lineIndex > boardHeight - 1) throw new IllegalArgumentException("Incorrect line number");
        for (int i = 0; i < moves; i++) {
            board.stream().filter((Net net) -> net.getI() == lineIndex).forEach(Net::moveRight);
            solution.add("R" + lineIndex);
        }
    }

    public void moveColumnUp(int columnIndex, int moves) {
        if (columnIndex < 0 || columnIndex > boardWidth - 1)
            throw new IllegalArgumentException("Incorrect column number");
        for (int i = 0; i < moves; i++) {
            board.stream().filter((Net net) -> net.getJ() == columnIndex).forEach(Net::moveUp);
            solution.add("U" + columnIndex);
        }
    }

    public void moveColumnDown(int columnIndex, int moves) {
        if (columnIndex < 0 || columnIndex > boardWidth - 1)
            throw new IllegalArgumentException("Incorrect column number");
        for (int i = 0; i < moves; i++) {
            board.stream().filter((Net net) -> net.getJ() == columnIndex).forEach(Net::moveDown);
            solution.add("D" + columnIndex);
        }
    }
}

class Net {
    private static int maxI;
    private static int maxJ;

    private final char value;
    private int i;
    private int j;

    public Net(char value, int i, int j) {
        this.value = value;
        this.i = i;
        this.j = j;
    }

    public void moveUp() {
        if (i == 0) i = maxI;
        else i--;
    }

    public void moveDown() {
        if (i == maxI) i = 0;
        else i++;
    }

    public void moveLeft() {
        if (j == 0) j = maxJ;
        else j--;
    }

    public void moveRight() {
        if (j == maxJ) j = 0;
        else j++;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Net net = (Net) o;
        return value == net.value;
    }

    public static void setMaxI(int maxI) {
        Net.maxI = maxI;
    }

    public static void setMaxJ(int maxJ) {
        Net.maxJ = maxJ;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
