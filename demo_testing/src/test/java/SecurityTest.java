import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class SecurityTest {
    @Test public void test_AddUser_ContentIncreased() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	security.addUser("user", "password");
	String content = storage.read();
	assertTrue(content.length() > 0);
    }

    @Test public void test_AddUser_ProperContent() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	security.addUser("user", "password");
	String content = storage.read();
	assertEquals(content, "user:password\n");
    }

    @Test public void test_AddUser_TwoDifferentUsers() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	security.addUser("user1", "password1");
	security.addUser("user2", "password2");
	String content = storage.read();
	assertEquals(content, "user1:password1\nuser2:password2\n");
    }

    @Test public void test_AddUser_TwoSameUsers() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	assertTrue(security.addUser("user1", "password1"));
	assertFalse(security.addUser("user1", "password2"));

	String content = storage.read();
	assertEquals(content, "user1:password1\n");
    }

    @Test public void test_AddUserOutputsSuccess_SingleUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean added = security.addUser("us", "ps");
	assertTrue(added);
    }

    @Test public void test_UserExists_NoUsers() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean e = security.userExists("us");
	assertFalse(e);
    }

    @Test public void test_UserExists_OnlyThisUserExists() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	storage.write("us:ps\n");
	assertTrue(security.userExists("us"));
    }

    @Test public void test_UserExists_TwoUsers() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	storage.write(content);

	assertTrue(security.userExists("u1"));
	assertTrue(security.userExists("u2"));
	assertFalse(security.userExists("u3"));
    }

    // TODO: test: add existing user
    // TODO: test: double adding of same user
    // TODO: feat: validation of password
}
