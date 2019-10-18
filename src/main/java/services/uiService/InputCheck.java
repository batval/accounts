package services.uiService;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public  class InputCheck {

    private static final Logger log = LogManager.getLogger(InputCheck.class.getName());

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
