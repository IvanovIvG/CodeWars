package ru.ivanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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


        assertEquals(4, Finder.pathFinder(a));
        assertEquals(-1, Finder.pathFinder(b));
        assertEquals(10, Finder.pathFinder(c));
        assertEquals(-1, Finder.pathFinder(d));
    }
}