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
    public boolean addUser(String login, String passwd) {
	if (userExists(login)) {
	    return false;
	}

	String s = storage.read();
	s = s + buildLine(login, passwd) + "\n";
	storage.write(s);
	return true;
    }

    /**
     * Checks if user exists
     */
    public boolean userExists(String login) {
	String c = storage.read();

	String [] lines = c.split("\n");

	for (int i = 0; i < lines.length; i++) {
	    if (!lineValid(lines[i])) {
		continue;
	    }

	    String []parts = lines[i].split(":");

	    if (parts[0].equals(login)) {
		return true;
	    }
	}

	return false;
    }

    /**
     * Checks login and password of user
     */
    public boolean check(String login, String passwd) {
	String c = storage.read();

	String [] lines = c.split("\n");

	for (int i = 0; i < lines.length; i++) {
	    if (!lineValid(lines[i])) {
		continue;
	    }

	    String []parts = lines[i].split(":");

	    if (parts[0].equals(login) && parts[1].equals(passwd)) {
		return true;
	    }
	}

	return false;
    }

    /**
     * Builds line with record about single user
     */
    public String buildLine(String login, String passwd) {
	return login + ":" + passwd;
    }

    private boolean lineValid(String line) {
	for (int i=0; i<line.length(); i++) {
	    if (line.charAt(i) == ':') {
		return true;
	    }
	}
	return false;
    }
}
