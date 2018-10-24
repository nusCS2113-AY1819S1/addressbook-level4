package seedu.address.testutil;

import seedu.address.logic.commands.AddCommand;

/**
 *  This class contains the necessary values for testing of ScriptCommand.
 */
public class ScriptUtil {
    public static final String VALID_TEXT_FILE = "ValidAddScriptCommand";
    public static final String INVALID_TEXT_FILE = "/studentlist";

    public static final String VALID_COMMAND_TYPE = AddCommand.COMMAND_WORD;
    public static final String INVALID_COMMAND_TYPE = "abc";
}