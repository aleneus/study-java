import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class SecurityTest {
    @Test public void test_AddUser_ContentIncreased() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean added = security.addUser(new User("user", "password"));
	assertTrue(added);
	String content = storage.read();
	assertTrue(content.length() > 0);
    }

    @Test public void test_AddUser_ProperContent() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean added = security.addUser(new User("user", "password"));
	assertTrue(added);
	String content = storage.read();
	assertEquals(content, "user:password\n");
    }

    @Test public void test_AddUser_TwoDifferentUsers() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean added;

	added = security.addUser(new User("user1", "password1"));
	assertTrue(added);

	added = security.addUser(new User("user2", "password2"));
	assertTrue(added);

	String content = storage.read();
	assertEquals(content, "user1:password1\nuser2:password2\n");
    }

    @Test public void test_AddUser_TwoSameUsers() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	assertTrue(security.addUser(new User("user1", "password1")));
	assertFalse(security.addUser(new User("user1", "password2")));

	String content = storage.read();
	assertEquals(content, "user1:password1\n");
    }

    @Test public void test_Auth() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	content += "u3:p3\n";
	storage.write(content);

	assertTrue(security.auth(new User("u1", "p1")));
	assertFalse(security.auth(new User("u1", "p2")));
	assertFalse(security.auth(new User("u2", "p1")));
	assertTrue(security.auth (new User("u2", "p2")));
	assertFalse(security.auth(new User("u4", "")));
    }

    @Test public void test_RemoveUser_UnknownUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	content += "u3:p3\n";
	storage.write(content);

	boolean removed = security.removeUser(new User("u4", "p4"));
	assertFalse(removed);
    }

    @Test public void test_RemoveUser_FirstUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	content += "u3:p3\n";
	storage.write(content);

	boolean removed = security.removeUser(new User("u1", "p1"));
	assertTrue(removed);
	assertEquals(storage.read(), "u2:p2\nu3:p3\n");
    }

    @Test public void test_RemoveUser_MiddleUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	content += "u3:p3\n";
	storage.write(content);

	boolean removed = security.removeUser(new User("u2", "p2"));
	assertTrue(removed);
	assertEquals(storage.read(), "u1:p1\nu3:p3\n");
    }

    @Test public void test_RemoveUser_LastUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	content += "u3:p3\n";
	storage.write(content);

	boolean removed = security.removeUser(new User("u3", "p3"));
	assertTrue(removed);
	assertEquals(storage.read(), "u1:p1\nu2:p2\n");
    }
}
