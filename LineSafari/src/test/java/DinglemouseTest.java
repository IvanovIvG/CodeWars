import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Ivanov
 **/
class DinglemouseTest {
    // "Good" examples from the Kata description.
    @Test
    public void exGood1() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "           ",
                "X---------X",
                "           ",
                "           "
        });
        Preloaded.showGrid(grid);
        assertEquals(true, Dinglemouse.line(grid));
    }

    @Test
    public void exGood2() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "     ",
                "  X  ",
                "  |  ",
                "  |  ",
                "  X  "
        });
        Preloaded.showGrid(grid);
        assertEquals(true, Dinglemouse.line(grid));
    }

    @Test
    public void exGood3() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "                    ",
                "     +--------+     ",
                "  X--+        +--+  ",
                "                 |  ",
                "                 X  ",
                "                    "
        });
        Preloaded.showGrid(grid);
        assertEquals(true, Dinglemouse.line(grid));
    }

    @Test
    public void exGood4() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "                     ",
                "    +-------------+  ",
                "    |             |  ",
                " X--+      X------+  ",
                "                     "
        });
        Preloaded.showGrid(grid);
        assertEquals(true, Dinglemouse.line(grid));
    }

    @Test
    public void exGood5() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "                      ",
                "   +-------+          ",
                "   |      +++---+     ",
                "X--+      +-+   X     "
        });
        Preloaded.showGrid(grid);
        assertEquals(true, Dinglemouse.line(grid));
    }

    // "Bad" examples from the Kata description.

    @Test
    public void exBad1() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "X-----|----X"
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

    @Test
    public void exBad2() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                " X  ",
                " |  ",
                " +  ",
                " X  "
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

    @Test
    public void exBad3() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "   |--------+    ",
                "X---        ---+ ",
                "               | ",
                "               X "
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

    @Test
    public void exBad4() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "              ",
                "   +------    ",
                "   |          ",
                "X--+      X   ",
                "              "
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

    @Test
    public void exBad5() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "      +------+",
                "      |      |",
                "X-----+------+",
                "      |       ",
                "      X       ",
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

    @Test
    public void noLine() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "           ",
                "X         X",
                "           ",
                "           "
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

    @Test
    public void loopAmbiguous() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                " X-----+ ",
                " X     | ",
                " |     | ",
                " +-----+ "
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

    @Test
    public void edgeCases() {
        final char grid[][] = Preloaded.makeGrid(new String[]{
                "  ++  ",
                " ++++ ",
                " ++++ ",
                "X-++-X"
        });
        Preloaded.showGrid(grid);
        assertEquals(false, Dinglemouse.line(grid));
    }

}