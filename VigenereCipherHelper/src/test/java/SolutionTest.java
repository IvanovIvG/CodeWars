import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ivan Ivanov
 **/
public class SolutionTest {
    @Test
    void testSomething() {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        String key = "password";
        VigenereCipher c = new VigenereCipher(key, abc);

        assertEquals("rovwsoiv", c.encode("codewars"), "encode(\"codewars\")");
        assertEquals("codewars", c.decode("rovwsoiv"), "decode(\"rovwsoiv\")");

        assertEquals("laxxhsj", c.encode("waffles"), "encode(\"waffles\")");
        assertEquals("waffles", c.decode("laxxhsj"), "decode(\"laxxhsj\")");

        assertEquals("CODEWARS", c.encode("CODEWARS"), "encode(\"CODEWARS\")");
        assertEquals("CODEWARS", c.decode("CODEWARS"), "decode(\"CODEWARS\")");
    }
}
