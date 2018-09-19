package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Command keywords */
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_EDIT = "edit";
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_HISTORY = "history";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_SELECT = "select";
    private static final String COMMAND_UNDO = "undo";
}
