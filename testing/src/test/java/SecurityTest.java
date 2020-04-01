import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class SecurityTest {
    @Test public void testNew() {
	// Scenario: создание объекта
	// Given
	// When
	Security s = new Security();
	// Then
	assertTrue(s != null);
    }
}
