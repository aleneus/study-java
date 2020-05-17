package security;

/**
 * Stub storage imitates the real one.
 @author Aleksandr Popov
*/
public class StubStorage implements Storage {
    /** Field data contains all writed data */
    String data;

    /**
     * Constructor
     */
    public StubStorage() {
	data = "";
    }

    /**
     * Writes string data to the storage
     */
    public void write(String s) {
	data = s;
    }

    /**
     * Reads data from storage
     */
    public String read() {
	return data;
    }
}
