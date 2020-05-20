import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class SecurityTest {
    @Test public void testAddUser_ContentIncreased() {
	StubStorage st = new StubStorage();
	Security sc = new Security(st);
	sc.addUser("user", "password");
	String content = st.read();
	assertTrue(content.length() > 0);
    }

    @Test public void testAddUser_ProperContent() {
	StubStorage st = new StubStorage();
	Security sc = new Security(st);
	sc.addUser("user", "password");
	String content = st.read();
	assertEquals(content, "user:password\n");
    }

    @Test public void testAddUser_TwoDifferentUsers() {
	StubStorage st = new StubStorage();
	Security sc = new Security(st);
	sc.addUser("user1", "password1");
	sc.addUser("user2", "password2");
	String content = st.read();
	assertEquals(content, "user1:password1\nuser2:password2\n");
    }

    @Test public void testAddUserOutput_SingleUser() {
	StubStorage st = new StubStorage();
	Security sc = new Security(st);
	boolean added = sc.addUser("us", "ps");
	assertTrue(added);
    }

    @Test public void testUserExists_NoUsers() {
	StubStorage st = new StubStorage();
	Security sc = new Security(st);
	boolean e = sc.userExists("us");
	assertFalse(e);
    }
}
