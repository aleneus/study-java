import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class StubStorageTest {
    @Test public void testWriteRead() {
	StubStorage sd = new StubStorage();
	sd.write("hello");
	String s = sd.read();
	assertTrue(s == "hello");
    }
}
