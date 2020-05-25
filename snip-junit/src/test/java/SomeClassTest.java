import org.junit.Test;
import static org.junit.Assert.*;
import some_package.*;


public class SomeClassTest {
    @Test public void test_Feature_Scenario() {
	SomeClass sc = new SomeClass();
	assertEquals(sc.Foo(1), 2);
    }
}
