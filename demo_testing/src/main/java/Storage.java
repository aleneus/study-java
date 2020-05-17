package security;

/**
 * Storage provides access to data.
*/
interface Storage {
    /**
     * Writes string data to the storage
     */
    public void write(String s);

    /**
     * Reads data from storage
     */
    public String read();
}
