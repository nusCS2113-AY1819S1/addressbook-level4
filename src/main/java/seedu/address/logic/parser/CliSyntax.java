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
    public static final Prefix PREFIX_NOTE = new Prefix("d/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_KPI = new Prefix("k/");
    public static final Prefix PREFIX_POSITION = new Prefix("r/");

    /* Command keywords */
    public static final String COMMAND_ADD = "add";
    public static final String COMMAND_CLEAR = "clear";
    public static final String COMMAND_DELETE = "delete";
    public static final String COMMAND_EDIT = "edit";
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_FIND = "find";
    public static final String COMMAND_HELP = "help";
    public static final String COMMAND_HISTORY = "history";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_REDO = "redo";
    public static final String COMMAND_SELECT = "select";
    public static final String COMMAND_UNDO = "undo";
    public static final String COMMAND_PASSWORD = "password";
    public static final String COMMAND_BACKUP = "backup";
    public static final String COMMAND_RESTORE = "restore";
    public static final String COMMAND_IMPORT = "import";
    public static final String COMMAND_EXPORT = "export";
}
