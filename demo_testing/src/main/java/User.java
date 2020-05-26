package security;

/**
 * User represents the single user
 */
public class User {
    private String name;
    private String hash;

    /**
     * Constructor
     */
    public User(String name, String passwd) {
	this.name = name;
	this.hash = calcHash(passwd);
    }

    /**
     * Returns the name of user
     */
    public String getName() {
	return name;
    }

    /**
     * Returns the hash of password
     */
    public String getHash() {
	return hash;
    }

    public boolean equals(User obj) {
	if (obj == this) {
	    return true;
	}

	if (obj == null || obj.getClass() != this.getClass()) {
	    return false;
	}

	User user = (User) obj;

	if (!name.equals(user.getName())) {
	    return false;
	}

	if (!hash.equals(user.getHash())) {
	    return false;
	}

	return true;
    }

    private String calcHash(String passwd) {
	return passwd;
    }
}
