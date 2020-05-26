package security;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Provides authentication functionality.
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
	ArrayList<User> users = loadUsers();

	for(int i = 0; i < users.size(); i++) {
	    User curr = users.get(i);
	    if (user.getName().equals(curr.getName())) {
		return false;
	    }
	}

	users.add(user);
	saveUsers(users);
	return true;
    }

    /**
     * Checks login and password of user
     */
    public boolean auth(User user) {
	ArrayList<User> users = loadUsers();
	for (int i = 0; i < users.size(); i++) {
	    User curr = users.get(i);
	    if (user.equals(curr)) {
		return true;
	    }
	}

	return false;
    }

    /**
     * Removes user if the user exists and password is correct
     */
    public boolean removeUser(User user) {
	ArrayList<User> usersOld = loadUsers();
	ArrayList<User> usersNew = new ArrayList<User>();

	boolean found = false;

	for (int i = 0; i < usersOld.size(); i++) {
	    User curr = usersOld.get(i);
	    if (curr.equals(user)) {
		found = true;
		continue;
	    }

	    usersNew.add(curr);
	}

	saveUsers(usersNew);
	return found;
    }

    private ArrayList<User> loadUsers() {
	String c = storage.read();

	String [] lines = c.split("\n");

	ArrayList<User> res = new ArrayList<User>();
	for (int i = 0; i < lines.length; i++) {
	    String [] parts = lines[i].split(DELIMITER);
	    if (parts.length != 2) {
		continue;
	    }
	    res.add(new User(parts[0], parts[1]));
	}

	return res;
    }

    private void saveUsers(ArrayList<User> users) {
	String s = "";
	for (int i = 0; i < users.size(); i++) {
	    s = s + buildLine(users.get(i)) + "\n";
	}
	storage.write(s);
    }

    private String buildLine(User user) {
	String line = user.getName() + DELIMITER + user.getHash();
	return line;
    }
}
