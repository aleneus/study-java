import org.junit.Test;
import static org.junit.Assert.*;
import security.*;


public class SecurityTest {
    @Test public void test_AddUser_ContentIncreased() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean added = security.addUser("user", "password");
	assertTrue(added);
	String content = storage.read();
	assertTrue(content.length() > 0);
    }

    @Test public void test_AddUser_ProperContent() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean added = security.addUser("user", "password");
	assertTrue(added);
	String content = storage.read();
	assertEquals(content, "user:password\n");
    }

    @Test public void test_AddUser_TwoDifferentUsers() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	boolean added;

	added = security.addUser("user1", "password1");
	assertTrue(added);

	added = security.addUser("user2", "password2");
	assertTrue(added);

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

    @Test public void test_AddUser_AddExistingUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	storage.write(content);

	assertFalse(security.addUser("u1", "xx"));
	assertFalse(security.addUser("u2", "yy"));
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

    @Test public void test_CheckUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	content += "u3:p3\n";
	storage.write(content);

	assertTrue(security.checkUser("u1", "p1"));
	assertFalse(security.checkUser("u1", "p2"));
	assertFalse(security.checkUser("u2", "p1"));
	assertTrue(security.checkUser("u2", "p2"));
	assertFalse(security.checkUser("u4", ""));
    }

    @Test public void test_RemoveUser_UnknownUser() {
	StubStorage storage = new StubStorage();
	Security security = new Security(storage);

	String content = "";
	content += "u1:p1\n";
	content += "u2:p2\n";
	content += "u3:p3\n";
	storage.write(content);

	boolean removed = security.removeUser("u4", "p4");
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

	boolean removed = security.removeUser("u1", "p1");
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

	boolean removed = security.removeUser("u2", "p2");
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

	boolean removed = security.removeUser("u3", "p3");
	assertTrue(removed);
	assertEquals(storage.read(), "u1:p1\nu2:p2\n");
    }
}
