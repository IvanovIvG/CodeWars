import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Ivanov
 **/
class CounterTest {
    private final Counter counter = new Counter();

    @Test
    void test(){
        assertEquals(0, counter.countBits(0));
        assertEquals(1, counter.countBits(4));
        assertEquals(3, counter.countBits(7));
        assertEquals(2, counter.countBits(9));
        assertEquals(2, counter.countBits(10));
    }
}