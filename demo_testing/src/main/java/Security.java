package security;


/**
 * Provides aothentication functionality.
 */
public class Security {
    /**
     * Storage used for hold data about users
     */
    Storage storage;

    /**
     * Constructor
     */
    public Security(Storage s) {
	storage = s;
    }

    /**
     * Registers new user
     */
    public boolean addUser(String name, String passwd) {
	String s = storage.read();
	s = s + buildLine(name, passwd) + "\n";
	storage.write(s);
	return true;
    }

    /**
     * Checks if user exists
     */
    public boolean userExists(String name) {
	return false;
    }

    /**
     * Builds line with record about single user
     */
    public String buildLine(String name, String passwd) {
	return name + ":" + passwd;
    }
}
