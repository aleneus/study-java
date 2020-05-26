import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class UserTest {
    @Test public void test_Equals() {
	User u1 = new User("asd", "123");
	User u2 = new User("asd", "123");
	assertTrue(u1.equals(u2));
    }
}
