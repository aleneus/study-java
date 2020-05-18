import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class SecurityTest {
    @Test public void testNew() {
	Security s = new Security(new StubStorage());
	assertTrue(s != null);
    }
}
