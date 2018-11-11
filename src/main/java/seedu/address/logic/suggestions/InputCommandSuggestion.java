package seedu.address.logic.suggestions;

import java.util.ArrayList;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportAllCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FinishTodoCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author elstonayx
//TODO: write test cases
/**
 * Checks whether input is valid in the commands after each key press
 */
public class InputCommandSuggestion {
    public static final String NO_REQUIRED_PARAMETERS = "This command does not require any parameters.\n";
    public static final String INVALID_COMMAND_PARAMETERS = "There are no available parameters!\n";

    private static Trie commandList;
    private static int wrongCharOverflow = 0;

    static {
        commandList = new Trie();
        commandList.insert(AddCommand.COMMAND_WORD);
        commandList.insert(ClearCommand.COMMAND_WORD);
        commandList.insert(DeleteCommand.COMMAND_WORD);
        commandList.insert(EditCommand.COMMAND_WORD);
        commandList.insert(ExitCommand.COMMAND_WORD);
        commandList.insert(ExportAllCommand.COMMAND_WORD);
        commandList.insert(FindCommand.COMMAND_WORD);
        commandList.insert(FinishTodoCommand.COMMAND_WORD);
        commandList.insert(HelpCommand.COMMAND_WORD);
        commandList.insert(HistoryCommand.COMMAND_WORD);
        commandList.insert(ListCommand.COMMAND_WORD);
        commandList.insert(RedoCommand.COMMAND_WORD);
        commandList.insert(ScheduleCommand.COMMAND_WORD);
        commandList.insert(SelectCommand.COMMAND_WORD);
        commandList.insert(TodoCommand.COMMAND_WORD);
        commandList.insert(UndoCommand.COMMAND_WORD);
        commandList.insert(ReminderCommand.COMMAND_WORD);
    }

    /**
     * Checks whether current character input is valid.
     * @param key current key input in
     * @return true if current word is valid and there are no overflows of wrong characters
     *         false if current word typed is invalid
     */
    public boolean checkValidCharacter(char key) {
        boolean isValid = commandList.search(key);
        if (Character.isLetter(key) && !isValid) {
            wrongCharOverflow++;
        }
        return isValid && (wrongCharOverflow == 0);
    }

    /**
     * Checks whether string input is valid
     * @param key string to check input
     * @return true if the key is in the Trie
     */
    public boolean checkValidString(String key) {
        return commandList.search(key);
    }

    /**
     * Moves search crawler to its parent if word is valid
     * else, it removes the overflow character count.
     * @return true when current substring is valid, false if current substring is invalid
     */
    public boolean removeSearchCharacter() {
        if (wrongCharOverflow > 1) {
            wrongCharOverflow--;
            return false;
        } else if (wrongCharOverflow == 1) {
            wrongCharOverflow--;
            return true;
        } else {
            commandList.moveSearchCrawlerToParent();
            return true;
        }
    }

    /**
     * Resets the search crawler to the root node
     */
    public void resetSearchCrawler() {
        commandList.resetSearchCrawlerToRoot();
    }

    /**
     * Checks if current word typed is the end of a word
     * @return True it is the end of a word
     */
    public boolean getIsEndOfWord() {
        return commandList.getIsEndOfWord();
    }

    /**
     * Gets a list of suggested commands
     * @param userInput the current string to check for suggested commands
     * @return ArrayList of possible commands
     */
    public ArrayList<String> getSuggestedCommands(String userInput) {
        String command = userInput.split(" ")[0];
        return commandList.getListOfWords(command);
    }

    /**
     * Gets the respective command parameters from input command.
     * @param command the command to get parameters
     * @return command parameters
     */
    public String getCommandParameters(String command) {
        switch (command) {
        case AddCommand.COMMAND_WORD:
            return AddCommand.COMMAND_PARAMETERS;

        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.COMMAND_PARAMETERS;

        case EditCommand.COMMAND_WORD:
            return EditCommand.COMMAND_PARAMETERS;

        case ExportAllCommand.COMMAND_WORD:
            return ExportAllCommand.COMMAND_PARAMETERS;

        case ExportCommand.COMMAND_WORD:
            return ExportCommand.COMMAND_PARAMETERS;

        case FindCommand.COMMAND_WORD:
            return FindCommand.COMMAND_PARAMETERS;

        case FinishTodoCommand.COMMAND_WORD:
            return FindCommand.COMMAND_PARAMETERS;

        case ScheduleCommand.COMMAND_WORD:
            return ScheduleCommand.COMMAND_PARAMETERS;

        case SelectCommand.COMMAND_WORD:
            return SelectCommand.COMMAND_PARAMETERS;

        case TodoCommand.COMMAND_WORD:
            return TodoCommand.COMMAND_PARAMETERS;

        case ReminderCommand.COMMAND_WORD:
            return ReminderCommand.COMMAND_PARAMETERS;

        case ClearCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
            return NO_REQUIRED_PARAMETERS;

        default:
            return INVALID_COMMAND_PARAMETERS;
        }
    }
}
