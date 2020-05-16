import org.junit.Test;
import static org.junit.Assert.*;
import security.*;

/**
 * Tests for stub storage.
 */
public class StubStorageTest {
    /**
     * Default content of storage
     */
    @Test public void testInit() {
	StubStorage sd = new StubStorage();
	String s = sd.read();
	assertTrue(s == "");
    }

    /**
     * Ordinary write and read
     */
    @Test public void testWriteRead() {
	StubStorage sd = new StubStorage();
	sd.write("hello");
	String s = sd.read();
	assertTrue(s == "hello");
    }

    /**
     * Write data to non-empty storage
     */
    @Test public void testReplace() {
	StubStorage sd = new StubStorage();
	sd.write("first");
	sd.write("second");
	String s = sd.read();
	assertTrue(s == "second");
    }
}
