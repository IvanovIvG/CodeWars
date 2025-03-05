package ru.ivanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Ivanov
 **/
class SolutionTest {
    @Test
    public void test() {
        assertEquals(23, new Solution().solution(10));
    }
}