import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Ivanov
 **/
class SG1Test {
    @Test
    public void testNoSolution() {
        String existingWires = """
                SX.
                XX.
                ..G""";

        String solution = "Oh for crying out loud...";

        assertEquals(solution, SG1.wireDHD(existingWires));
    }

    @Test
    public void test3x3() {
        String existingWires = """
                SX.
                X..
                XXG""";

        String solution = """
                SX.
                XP.
                XXG""";

        assertEquals(solution, SG1.wireDHD(existingWires));


        existingWires = """
                .S.
                ...
                .G.""";

        solution = """
                .S.
                .P.
                .G.""";

        assertEquals(solution, SG1.wireDHD(existingWires));


        existingWires = """
                ...
                S.G
                ...""";

        solution = """
                ...
                SPG
                ...""";

        assertEquals(solution, SG1.wireDHD(existingWires));


        existingWires = """
                ...
                SG.
                ...""";

        solution = "...\n" + "SG.\n" + "...";

        assertEquals(solution, SG1.wireDHD(existingWires));
    }

    @Test
    public void test5x5() {
        String existingWires = """
                .S...
                XXX..
                .X.XX
                ..X..
                G...X""";

        String solution = """
                .SP..
                XXXP.
                .XPXX
                .PX..
                G...X""";

        assertEquals(solution, SG1.wireDHD(existingWires));
    }

    @Test
    public void test10x10() {
        String existingWires = """
                XX.S.XXX..
                XXXX.X..XX
                ...X.XX...
                XX...XXX.X
                ....XXX...
                XXXX...XXX
                X...XX...X
                X...X...XX
                XXXXXXXX.X
                G........X""";

        Set<String> solutionsSet = new HashSet<String>(Arrays.asList("""
                        XX.S.XXX..
                        XXXXPX..XX
                        ...XPXX...
                        XX.P.XXX.X
                        ...PXXX...
                        XXXXPP.XXX
                        X...XXP..X
                        X...X..PXX
                        XXXXXXXXPX
                        GPPPPPPP.X""",

                """
                        XX.S.XXX..
                        XXXXPX..XX
                        ...XPXX...
                        XX..PXXX.X
                        ...PXXX...
                        XXXXPP.XXX
                        X...XXP..X
                        X...X..PXX
                        XXXXXXXXPX
                        GPPPPPPP.X"""));

        String actual = SG1.wireDHD(existingWires);

        assertTrue(solutionsSet.contains(actual), String.format("Your solution:\n%s\n\nShould be:%s", actual, solutionsSet));
    }

    @Test
    public void someTest() {
        String condition = """
                .X.X.X....XXXXXX...X
                XX.XX.XXXXXXXXXXX..X
                .X.X.XX..X..X.XXXXXX
                X.X..XXX...XX.X.XXX.
                X.X..X..XXX.X.X.X...
                .XXX..XXXXX.X.X..XX.
                X.XX.SX......XXX..X.
                .XXXXX.XXX...XX..X..
                ....X.XX..X.XX.X..XX
                ....X..XX..XX..X.XX.
                X...X..XX.X.X.XX...X
                .XXX.........X.XX..G
                ..XX.XX.XX.X.XXXXXX.
                .X.X...X.X.XXXX..X.X
                ..X..XXX.XX....XXXX.
                XX..XXXXXXX.....XXXX
                XXXX.X.X..XXXXXX...X
                X...X..X..XXXX..X..X
                X.XXXXX..XX..XXX.X.X
                XX.X.XX.XXXX.X..X.XX""";

        String answer = """
                .X.X.X....XXXXXX...X
                XX.XX.XXXXXXXXXXX..X
                .X.X.XX..X..X.XXXXXX
                X.X..XXX...XX.X.XXX.
                X.X..X..XXX.X.X.X...
                .XXX..XXXXX.X.X..XX.
                X.XX.SX......XXX..X.
                .XXXXXPXXX...XXP.X..
                ....XPXX..X.XXPXP.XX
                ....X.PXX..XXP.XPXX.
                X...X.PXX.X.XPXX.PPX
                .XXX...PPPPPPX.XX..G
                ..XX.XX.XX.X.XXXXXX.
                .X.X...X.X.XXXX..X.X
                ..X..XXX.XX....XXXX.
                XX..XXXXXXX.....XXXX
                XXXX.X.X..XXXXXX...X
                X...X..X..XXXX..X..X
                X.XXXXX..XX..XXX.X.X
                XX.X.XX.XXXX.X..X.XX""";

        String actual = SG1.wireDHD(condition);

        System.out.println(actual);
        System.out.println();
        System.out.println(answer);
    }

}