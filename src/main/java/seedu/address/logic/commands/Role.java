package seedu.address.logic.commands;

import java.util.ArrayList;
/**
 * Determines the role for the user
 */
public class Role {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_ACCOUNTANT = "accountant";
    public static final String ROLE_STUDENT = "student";
    private static ArrayList<String> legalCommands;

    private final String role;
    private CommandList commandList = new CommandList();



    public Role(String role) {
        this.role = role;
        switch(role) {
        case ROLE_STUDENT:
            legalCommands = commandList.getAccountantCommands();
            break;
        case ROLE_ACCOUNTANT:
            legalCommands = commandList.getStudentCommands();
            break;
        case ROLE_ADMIN:
            legalCommands = commandList.getCommandList();
            break;
        default:
            System.exit(-1);
        }
    }
    /**
     * Method to check access of the user and
     * @throws IllegalAccessException
     * if not valid
     */
    public static void checkAccess(String commandWord) throws IllegalAccessException {
        for (String cmd : legalCommands) {
            if (commandWord.equals(cmd)) {
                return;
            }
        }
        throw new IllegalAccessException();
    }
}
