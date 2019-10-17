package services.uiService;


public  class InputCheck {

    public static boolean isDouble(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("Invalid data format");
            return false;
        }
        return true;
    }

    public static boolean isInt(String strNum) {
        try {
          int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("Invalid data format");
            return false;
        }
        return true;
    }

    public static boolean isBool(String strNum) {
        try {
            Boolean d = Boolean.parseBoolean(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("Invalid data format");
            return false;
        }
        return true;
    }
}
