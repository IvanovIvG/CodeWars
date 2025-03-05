package ru.ivanov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Ivan Ivanov
 **/
class FinderTest {
    @Test
    public void sampleTests() {

        String a = """
                .W.
                .W.
                ...""",

                b = """
                        .W.
                        .W.
                        W..""",

                c = """
                        ......
                        ......
                        ......
                        ......
                        ......
                        ......""",

                d = """
                        ......
                        ......
                        ......
                        ......
                        .....W
                        ....W.""";

        assertTrue(Finder.pathFinder(a));
        assertFalse(Finder.pathFinder(b));
        assertTrue(Finder.pathFinder(c));
        assertFalse(Finder.pathFinder(d));
    }

}