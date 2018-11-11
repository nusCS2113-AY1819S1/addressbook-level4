package seedu.address.commons;

/**
 * Containing Commands return type with their string representation, and method to check whether its allowed
 */
public enum AuthReturn {
    COMMAND_ALLOWED, COMMAND_LOGOUTFIRST, COMMAND_LOGINFIRST, COMMAND_ERROR;
}
