package seedu.address.model.prioritylevel;

//@@author jylee-git
/**
 * Priority level by descending order.
 * Administrator highest priority, followed by I.T. Unit, followed by manager...
 */
public enum PriorityLevelEnum {
    ADMINISTRATOR(0),
    IT_UNIT(1),
    MANAGER(2),
    BASIC(3);

    private final int priorityLevelCode;

    PriorityLevelEnum(int priorityLevelCode) {
        this.priorityLevelCode = priorityLevelCode;
    }

    public int getPriorityLevelCode() {
        return this.priorityLevelCode;
    }

    /**
     * Returns true if the priorityLevel value is within the defined enum range as stated above.
     */
    public static boolean isValidPriorityLevel(int test) {
        if (test >= ADMINISTRATOR.getPriorityLevelCode() && test <= BASIC.getPriorityLevelCode()) {
            return true;
        }
        return false;
    }
}
