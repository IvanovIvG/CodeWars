import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ivan Ivanov
 **/
public class SolutionTest {
    @Test
    public void sampleTests() {

        String a = """
                000
                000
                000""",

                b = """
                        010
                        010
                        010""",

                c = """
                        010
                        101
                        010""",

                d = """
                        0707
                        7070
                        0707
                        7070""",

                e = """
                        700000
                        077770
                        077770
                        077770
                        077770
                        000007""",

                f = """
                        777000
                        007000
                        007000
                        007000
                        007000
                        007777""",

                g = """
                        000000
                        000000
                        000000
                        000010
                        000109
                        001010""",
                h = """
                        00000000000
                        00000000000
                        00000000000
                        00001000000
                        00010900000
                        00000000000
                        00030000000
                        00000000800
                        00001000000
                        00010900000
                        00101000000""";


        assertEquals(2,  Finder.pathFinder(b));
        assertEquals(0,  Finder.pathFinder(a));
        assertEquals(4,  Finder.pathFinder(c));
        assertEquals(42, Finder.pathFinder(d));
        assertEquals(14, Finder.pathFinder(e));
        assertEquals(0,  Finder.pathFinder(f));
        assertEquals(4,  Finder.pathFinder(g));
        assertEquals(0,  Finder.pathFinder(h));
    }

    @Test
    @Timeout(value = 14)
    void testPerformance(){
        int runsTimes = 1000;
        for (int i = 0; i < runsTimes; i++) {
            String randomData = generateRandomMaze();
            Finder.pathFinder(randomData);
        }
    }

    private String generateRandomMaze() {
        int mazeSize = ThreadLocalRandom.current().nextInt(1, 100);
        StringBuilder maze = new StringBuilder();
        for (int i = 0; i < mazeSize - 1; i++) {
            String randomLine = RandomStringUtils.randomNumeric(mazeSize);
            maze.append(randomLine);
            maze.append("\n");
        }
        String randomLine = RandomStringUtils.randomNumeric(mazeSize);
        maze.append(randomLine);
        return maze.toString();
    }
}
