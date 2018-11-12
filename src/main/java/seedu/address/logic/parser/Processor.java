package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author junweiljw
/**
 * Parser class to extract keywords from user input
 * Searches the args[] string for keywords and copies them in different strings
 */
public class Processor {

    /*
    String userInput;

    public Processor(String userInput) {
        this.userInput = userInput;
   }
    */

    /*
    /**
     * Adds the string tokens in sortedArray into a single string, ignoring null entries in the array
     *
     * @param s the sorted string array to be concatenated
     * @param glue the space to be added in between each array index
     * @return a concatenated string
     */
    /* Leftovers from Processor v1.0 and v2.0, might be used
    private String combine(String[] s, String glue) {
        int k = s.length;
        if (k == 0) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        out.append(s[0]);
        for (int i = 1; i < k; i++) {
            if (s[i] != null) {
                out.append(glue).append(s[i]);
            }
        }
        return out.toString();
    }
    */

    /**
     * Boolean function that checks if a given String is an integer
     *
     * @param s the string to be checked
     * @return true if String is integer, false if otherwise
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Sorts the tokens in args[] into the correct sequence and concatenates them into a string
     * @param userInput the line input by user
     * @return the sorted string
     */
    public String commandParser(String userInput) {

        // splits userInput by whitespaces and saves the tokens in args[]
        String[] args = userInput.split("\\s+");
        String newInput;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
            // "COMMAND_WORD + PREFIX + DATA" argument pattern
            case AddCommand.COMMAND_WORD:
            case AddCommand.COMMAND_ALIAS:
            // "COMMAND_WORD + KEYWORD" argument pattern
            case FindCommand.COMMAND_WORD:
            case FindCommand.COMMAND_ALIAS:
            // fallthrough, similar "COMMAND_WORD" argument pattern
            case ClearCommand.COMMAND_WORD:
            case ClearCommand.COMMAND_ALIAS:
            case ListCommand.COMMAND_WORD:
            case ListCommand.COMMAND_ALIAS:
            case HistoryCommand.COMMAND_WORD:
            case HistoryCommand.COMMAND_ALIAS:
            case UndoCommand.COMMAND_WORD:
            case UndoCommand.COMMAND_ALIAS:
            case RedoCommand.COMMAND_WORD:
            case RedoCommand.COMMAND_ALIAS:
            case HelpCommand.COMMAND_WORD:
            case HelpCommand.COMMAND_ALIAS:
            case ExitCommand.COMMAND_WORD:
            case ExitCommand.COMMAND_ALIAS:
                userInput = userInput.replace(args[i], "");
                newInput = args[i] + userInput;
                return newInput;

            // "COMMAND_WORD + INDEX + PREFIX + DATA" argument pattern
            case EditCommand.COMMAND_WORD:
            case EditCommand.COMMAND_ALIAS:
                //fallthrough, similar "COMMAND_WORD + INDEX" argument pattern
            case SelectCommand.COMMAND_WORD:
            case SelectCommand.COMMAND_ALIAS:
            case DeleteCommand.COMMAND_WORD:
            case DeleteCommand.COMMAND_ALIAS:
                userInput = userInput.replace(args[i], "");
                if (isInteger(args[i + 1])) {
                    userInput = userInput.replace(args[i + 1], "");
                }
                newInput = args[i] + args[i + 1] + userInput;
                return newInput;

            default:
            }
        }
        // if this statement is reached, there is no COMMAND_WORD within the userInput string
        return userInput;
    }
}
