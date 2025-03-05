package ru.ivanov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Ivanov
 **/
class KataTest {
    @Test
    @DisplayName("Sample tests")
    void sampleTests() {
        assertEquals(7, Kata.oddCount(15), "For n = 7");
        assertEquals(7511, Kata.oddCount(15023), "For n = 15023");
    }
}