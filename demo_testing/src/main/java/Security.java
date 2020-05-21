package security;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides aothentication functionality.
 */
public class Security {
    /**
     * Storage used for hold data about users
     */
    Storage storage;

    public static final Logger log = Logger.getLogger("Security");

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
	if (userExists(name)) {
	    return false;
	}

	String s = storage.read();
	s = s + buildLine(name, passwd) + "\n";
	storage.write(s);
	return true;
    }

    /**
     * Checks if user exists
     */
    public boolean userExists(String name) {
	String c = storage.read();

	String [] lines = c.split("\n");

	boolean found = false;
	for (int i = 0; i < lines.length; i++) {
	    String []parts = lines[i].split(":");

	    if (parts.length < 2) {
		continue;
	    }

	    if (parts[0].equals(name)) {
		found = true;
		break;
	    }
	}

	return found;
    }

    /**
     * Builds line with record about single user
     */
    public String buildLine(String name, String passwd) {
	return name + ":" + passwd;
    }
}
