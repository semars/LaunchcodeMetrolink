import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SimpleTest {

    @Test
    public void testOneEqualsOne() {
        assertEquals(1, 1);
    }

    @Test
    public void testStringOneEqualsStringTwo() {
        assertEquals("one", "one");
    }

}
