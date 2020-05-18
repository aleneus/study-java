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
}
