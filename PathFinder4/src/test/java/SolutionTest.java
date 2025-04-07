import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

/**
 * @author Ivan Ivanov
 **/
@RunWith(Parameterized.class)
public class SolutionTest {

    @Parameterized.Parameters
    public static Collection<Object[]> prepTests() {
        return Arrays.asList( new Object[] {"", new Point(  0, 0)},
                new Object[] {"RLrl",   new Point(  0, 0)},
                new Object[] {"r5L2l4", new Point(  4, 3)},
                new Object[] {"r5L2l4", new Point(  0, 0)},
                new Object[] {"10r5r0", new Point(-10, 5)},
                new Object[] {"10r5r0", new Point(  0, 0)});
    }
    private final String path;
    private final Point  you;

    public SolutionTest(String path, Point you) {
        this.path = path;  this.you = you;
    }

    @Test public void test() {
        assertEquals("With path=\""+path+"\":", you, PathFinder.iAmHere(path));
    }
}
