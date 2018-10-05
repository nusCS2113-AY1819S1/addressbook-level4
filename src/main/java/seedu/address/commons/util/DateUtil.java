package seedu.address.commons.util;

/**
 * Contains helper methods to determine whether a date is logical or not
 */
public class DateUtil {

    private static final int JAN = 1;
    private static final int FEB = 2;
    private static final int MAR = 3;
    private static final int APR = 4;
    private static final int MAY = 5;
    private static final int JUN = 6;
    private static final int JUL = 7;
    private static final int AUG = 8;
    private static final int SEP = 9;
    private static final int OCT = 10;
    private static final int NOV = 11;
    private static final int DEC = 12;

    private static final int TWENTYEIGHTDAYS = 28;
    private static final int THIRTYDAYS = 30;
    private static final int THIRTYONEDAYS = 31;

    /**
     * This function checks whether the given day and month falls within the constraints of modern calendars
     * @param day
     * @param month
     * @return true if valid and false if otherwise
     */
    public static boolean isValidDate(int day, int month) {
        boolean isValid;
        switch(month) {
        case JAN:
        case MAR:
        case MAY:
        case JUL:
        case AUG:
        case OCT:
        case DEC:
            isValid = day <= THIRTYONEDAYS;
            break;
        case FEB:
            isValid = day <= TWENTYEIGHTDAYS;
            break;
        case APR:
        case JUN:
        case SEP:
        case NOV:
            isValid = day <= THIRTYDAYS;
            break;
        default:
            isValid = false;
        }
        return isValid;
    }
}
