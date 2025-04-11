import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Ivanov
 **/
public class ExampleTests {
    @Test
    public void fixedTest1() {
        char[][] mixedBoard = {
                {'A', 'B'},
                {'C', 'D'}};
        char[][] solvedBoard = {
                {'A', 'B'},
                {'C', 'D'}};

        List<String> solution = Loopover.solve(mixedBoard, solvedBoard);

        assertNotNull(solution);
        assertEquals(0, solution.size());
    }

    @Test
    public void fixedTest2() {
        char[][] mixedBoard = {
                {'D', 'B'},
                {'C', 'A'}};
        char[][] solvedBoard = {
                {'A', 'B'},
                {'C', 'D'}};

        List<String> solution = Loopover.solve(mixedBoard, solvedBoard);

        assertNotNull(solution);
    }

    @Test
    public void fixedTest3() {
        char[][] mixedBoard = {
                {'A', 'C', 'D', 'B', 'E'},
                {'F', 'G', 'H', 'I', 'J'},
                {'K', 'L', 'M', 'N', 'O'},
                {'P', 'Q', 'R', 'S', 'T'}};
        char[][] solvedBoard = {
                {'A', 'B', 'C', 'D', 'E'},
                {'F', 'G', 'H', 'I', 'J'},
                {'K', 'L', 'M', 'N', 'O'},
                {'P', 'Q', 'R', 'S', 'T'}};

        List<String> solution = Loopover.solve(mixedBoard, solvedBoard);

        assertNotNull(solution);
    }

    @Test
    public void fixedTest4() {
        char[][] mixedBoard = {
                {'W', 'C', 'M', 'D', 'J'},
                {'O', 'R', 'F', 'B', 'A'},
                {'K', 'N', 'G', 'L', 'Y'},
                {'P', 'H', 'V', 'S', 'E'},
                {'T', 'X', 'Q', 'U', 'I'}};
        char[][] solvedBoard = {
                {'A', 'B', 'C', 'D', 'E'},
                {'F', 'G', 'H', 'I', 'J'},
                {'K', 'L', 'M', 'N', 'O'},
                {'P', 'Q', 'R', 'S', 'T'},
                {'U', 'V', 'W', 'X', 'Y'}};

        List<String> solution = Loopover.solve(mixedBoard, solvedBoard);

        assertNull(solution);
    }

    @Test
    public void fixedTest5() {
        char[][] mixedBoard = {
                {'W', 'C', 'M', 'D', 'J', '0'},
                {'O', 'R', 'F', 'B', 'A', '1'},
                {'K', 'N', 'G', 'L', 'Y', '2'},
                {'P', 'H', 'V', 'S', 'E', '3'},
                {'T', 'X', 'Q', 'U', 'I', '4'},
                {'Z', '5', '6', '7', '8', '9'}};
        char[][] solvedBoard = {
                {'A', 'B', 'C', 'D', 'E', 'F'},
                {'G', 'H', 'I', 'J', 'K', 'L'},
                {'M', 'N', 'O', 'P', 'Q', 'R'},
                {'S', 'T', 'U', 'V', 'W', 'X'},
                {'Y', 'Z', '0', '1', '2', '3'},
                {'4', '5', '6', '7', '8', '9'}};

        List<String> solution = Loopover.solve(mixedBoard, solvedBoard);

        assertNotNull(solution);
    }

    @Test
    public void fixedTest6() {
        char[][] mixedBoard = {
                {'G', 'D', 'F'},
                {'C', 'B', 'H'},
                {'A', 'E', 'I'}};
        char[][] solvedBoard = {
                {'A', 'B', 'C'},
                {'D', 'E', 'F'},
                {'G', 'H', 'I'}};

        List<String> solution = Loopover.solve(mixedBoard, solvedBoard);

        assertNotNull(solution);
    }

    @Test
    public void fixedTest7() {
        char[][] mixedBoard = {
                {'Q', 'G', 'I', 'N', 'F', 'P'},
                {'C', 'K', 'J', 'B', 'D', 'O'},
                {'L', 'E', 'R', 'H', 'A', 'M'}};
        char[][] solvedBoard = {
                {'A', 'B', 'C', 'D', 'E', 'F'},
                {'G', 'H', 'I', 'J', 'K', 'L'},
                {'M', 'N', 'O', 'P', 'Q', 'R'}};

        List<String> solution = Loopover.solve(mixedBoard, solvedBoard);

        assertNotNull(solution);
    }
}
