package seedu.address.logic;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportAllCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Suggests a command with the closest match to the inputted string.
 */
public class CommandSuggestion {
    public static final String SUGGESTION_HEADER = "Did you mean: %1$s?";
    public static final String TEMPORARY_SUGGESTION = "Suggestion not implemented";
    public static final String NO_SUGGESTION = "No suggestion";
    public static final String TEMPORARY_COMPARISON_COMMAND = "test";
    public static final int ARRAY_PADDING = 1;
    public static final int DELETION_COST = 1;
    public static final int ADDITION_COST = 1;
    public static final int SUBSTITUTION_COST = 1;

    private static final String[] CommandList;

    // Initialising the CommandList Array
    static {
        CommandList = new String[] {
                AddCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD,
                EditCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                ExportAllCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                HistoryCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                RedoCommand.COMMAND_WORD,
                ScheduleCommand.COMMAND_WORD,
                SelectCommand.COMMAND_WORD,
                UndoCommand.COMMAND_WORD
        };
    }

    /**
     * Parses the command input and passes it to the getNearestCommand for comparison of commands.
     * Returns formatted string of the suggestion header and closest matched command, else returns nothing.
     * @param userCommand A {@code String}
     * @return A {@code String} object containing the suggestion header and suggested similar command.
     */
    public String getSuggestion(String userCommand) {
        return String.format(SUGGESTION_HEADER, getNearestCommand(userCommand));
    }

    private String getNearestCommand(String userCommand) {
        return Integer.toString(editDistance(userCommand, TEMPORARY_COMPARISON_COMMAND));
        /*
        if(editDistance(userCommand, TEMPORARY_COMPARISON_COMMAND) == 1) {
            return TEMPORARY_SUGGESTION;
        } else {
            return NO_SUGGESTION;
        }
        */
    }

    private int editDistance(String userCommand, String commandToCheck) {
        int[][] distanceArray = new int[userCommand.length() + ARRAY_PADDING][commandToCheck.length() + ARRAY_PADDING];

        for(int i = 0; i < userCommand.length() + ARRAY_PADDING; i++) {
            distanceArray[i][0] = i;
        }

        for(int j = 0; j < commandToCheck.length() + ARRAY_PADDING; j++) {
            distanceArray[0][j] = j;
        }

        for(int i = 0; i < userCommand.length(); i++) {
            for(int j = 0; j < commandToCheck.length(); j++) {
                if(userCommand.charAt(i) == commandToCheck.charAt(j)) {
                    distanceArray[i + ARRAY_PADDING][j + ARRAY_PADDING] = distanceArray[i][j];
                } else {
                    distanceArray[i + ARRAY_PADDING][j + ARRAY_PADDING] = minimum(
                            distanceArray[i + ARRAY_PADDING][j] + DELETION_COST,
                            distanceArray[i][j + ARRAY_PADDING] + ADDITION_COST,
                            distanceArray[i][j] + SUBSTITUTION_COST
                    );
                }
            }
        }

        return distanceArray[userCommand.length()][commandToCheck.length()];
    }

    private int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

}
