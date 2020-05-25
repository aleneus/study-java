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
	ArrayList<String> lines = load();

	for(int i = 0; i < lines.size(); i++) {
	    String curr = lines.get(i);

	    if (!lineValid(curr)) {
		continue;
	    }

	    String []parts = curr.split(":");

	    if (parts[0].equals(login)) {
		return true;
	    }
	}

	return false;
    }

    /**
     * Checks login and password of user
     */
    public boolean auth(String login, String passwd) {
	ArrayList<String> lines = load();

	for (int i = 0; i < lines.size(); i++) {
	    String curr = lines.get(i);

	    if (!lineValid(curr)) {
		continue;
	    }

	    String []parts = curr.split(":");

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
	ArrayList<String> linesOld = load();
	ArrayList<String> linesNew = new ArrayList<String>();

	boolean found = false;
	String curr;

	for (int i = 0; i < linesOld.size(); i++) {
	    curr = linesOld.get(i);

	    if (lineValid(curr)) {
		String []parts = curr.split(":");
		if (parts[0].equals(login) && parts[1].equals(passwd)) {
		    found = true;
		    continue;
		}
	    }

	    linesNew.add(curr);
	}

	save(linesNew);
	return found;
    }

    private boolean lineValid(String line) {
	for (int i=0; i<line.length(); i++) {
	    if (line.charAt(i) == ':') {
		return true;
	    }
	}
	return false;
    }

    private ArrayList<String> load() {
	String c = storage.read();

	String [] lines = c.split("\n");

	ArrayList<String> res = new ArrayList<String>();
	for (int i = 0; i < lines.length; i++) {
	    res.add(lines[i]);
	}

	return res;
    }

    private void save(ArrayList<String> lines) {
	String s = "";
	for (int i = 0; i < lines.size(); i++) {
	    s = s + lines.get(i) + "\n";
	}
	storage.write(s);
    }

    private String buildLine(String login, String passwd) {
	return login + ":" + passwd;
    }
}
