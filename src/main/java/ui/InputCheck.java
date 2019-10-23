package ui;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for checking input values
 *
 * @author Baturo Valery
 * @version 1.0
 */
public  class InputCheck {

    /**
     * Event Logger for class AccountPaymentDAO {@value}.
     */
    private static final Logger log = LogManager.getLogger(InputCheck.class.getName());

    /**
     * Check if the string is double
     *
     * @param strNum user entered string
     * @return true if strNum is double and false otherwise
     * @throws NumberFormatException wrong format
     * @throws NullPointerException null pointer
     */
    public static boolean isDouble(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("Invalid data format");
            log.error(nfe);
            return false;
        }
        return true;
    }

    /**
     * Check if the string is int
     *
     * @param strNum user entered string
     * @return true if strNum is int and false otherwise
     * @throws NumberFormatException wrong format
     * @throws NullPointerException null pointer
     */
    public static boolean isInt(String strNum) {
        try {
          int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("Invalid data format");
            log.error(nfe);
            return false;
        }
        return true;
    }

    /**
     * Check if the string is boolean
     *
     * @param strNum user entered string
     * @return true if strNum is boolean and false otherwise
     * @throws NumberFormatException wrong format
     * @throws NullPointerException null pointer
     */
    public static boolean isBool(String strNum) {
        try {
            Boolean d = Boolean.parseBoolean(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("Invalid data format");
            log.error(nfe);
            return false;
        }
        return true;
    }
}
