package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;

public class Role {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_ACCOUNTANT = "accountant";
    public static final String ROLE_STUDENT = "student";

    private final String role;
    private CommandList commandList = new CommandList();
    private static ArrayList<String> legalCommands;




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

    public static void checkAccess(String commandWord) throws IllegalAccessException {
        for(String cmd : legalCommands) {
            if(commandWord.equals(cmd)) {
                return;
            }
        }
        throw new IllegalAccessException();
    }
}
