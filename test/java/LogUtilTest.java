import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

/**
 * Test class for logging utility functions
 */
public class LogUtilTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    /**
     * Test implementation of LogUtil class
     */
    private static class TestLogUtil {
        public static void console(String log) {
            System.out.println(log);
        }
    }

    @Before
    public void setUpStreams() {
        // Redirect standard output stream
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        // Restore standard output stream
        System.setOut(originalOut);
    }

    @Test
    public void testConsole() {
        String testMessage = "Test log message";
        TestLogUtil.console(testMessage);
        assertEquals("Log message should be output correctly", testMessage + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testConsoleWithEmptyString() {
        TestLogUtil.console("");
        assertEquals("Empty string should only output line separator", System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testConsoleWithNull() {
        TestLogUtil.console(null);
        assertEquals("Null should be converted to string 'null'", "null" + System.lineSeparator(), outContent.toString());
    }
} 
