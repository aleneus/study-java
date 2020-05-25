package security;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

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
    public boolean checkUser(String login, String passwd) {
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
     * Removes user if the user exists and password is correct
     */
    public boolean removeUser(String login, String passwd) {
	String c = storage.read();

	String [] lines = c.split("\n");
	ArrayList<String> linesNew = new ArrayList<>();

	boolean found = false;
	for (int i = 0; i < lines.length; i++) {
	    String curLine = lines[i];

	    if (!lineValid(curLine)) {
		linesNew.add(curLine);
		continue;
	    }

	    String []parts = curLine.split(":");

	    if (parts[0].equals(login) && parts[1].equals(passwd)) {
		found = true;
		continue;
	    }

	    linesNew.add(lines[i]);
	}

	String contentNew = "";
	for(int i = 0; i<linesNew.size(); i++) {
	    String line = linesNew.get(i);
	    contentNew += line + "\n";
	}

	storage.write(contentNew);
	return found;
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
