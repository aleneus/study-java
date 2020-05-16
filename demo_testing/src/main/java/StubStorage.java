package security;

/**
 * Stub storage imitates the real one.
 @author Aleksandr Popov
*/
public class StubStorage {
    /** Field data contains all writed data */
    String data;

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
