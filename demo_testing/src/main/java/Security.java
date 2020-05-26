package security;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Provides aothentication functionality.
 */
public class Security {
    final String DELIMITER = ":";

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
    public boolean addUser(User user) {
	ArrayList<String> lines = load();

	boolean found = false;
	for(int i = 0; i < lines.size(); i++) {
	    String curr = lines.get(i);

	    if (!lineValid(curr)) {
		continue;
	    }

	    String []parts = curr.split(DELIMITER);

	    if (parts[0].equals(user.getName())) {
		found = true;
		break;
	    }
	}

	if (found) {
	    return false;
	}

	String s = storage.read();
	s = s + buildLine(user) + "\n";
	storage.write(s);
	return true;
    }

    /**
     * Checks login and password of user
     */
    public boolean auth(User user) {
	ArrayList<String> lines = load();

	for (int i = 0; i < lines.size(); i++) {
	    String curr = lines.get(i);

	    if (!lineValid(curr)) {
		continue;
	    }

	    String []parts = curr.split(DELIMITER);

	    if (parts[0].equals(user.getName()) && parts[1].equals(user.getHash())) {
		return true;
	    }
	}

	return false;
    }

    /**
     * Removes user if the user exists and password is correct
     */
    public boolean removeUser(User user) {
	ArrayList<String> linesOld = load();
	ArrayList<String> linesNew = new ArrayList<String>();

	boolean found = false;
	String curr;

	for (int i = 0; i < linesOld.size(); i++) {
	    curr = linesOld.get(i);

	    if (lineValid(curr)) {
		String []parts = curr.split(DELIMITER);
		if (parts[0].equals(user.getName()) && parts[1].equals(user.getHash())) {
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
	if (line.split(DELIMITER).length == 2) {
	    return true;
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

    private String buildLine(User user) {
	String line = user.getName() + DELIMITER + user.getHash();
	return line;
    }
}
