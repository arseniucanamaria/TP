package config;


public class PersistenceProperties {

    private static String ITEMS_FILE_NAME = "items.txt";
    private static String ORDERS_FILE_NAME = "orders.txt";

    /**
     * Citeste din fisierul de configurari dupa o cheie si returneaza valoarea
     */
    public static String getProperty(String key) {
        if (key.equals("ITEMS_FILE_NAME")) {
            return ITEMS_FILE_NAME;
        } else if (key.equals("ORDERS_FILE_NAME")) {
            return ORDERS_FILE_NAME;
        }

        return null;
    }
}