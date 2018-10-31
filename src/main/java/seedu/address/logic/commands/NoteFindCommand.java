package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_KEY_WORD;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.NoteManager;

/**
 * Finds notes containing a set of keywords.
 */
public class NoteFindCommand extends Command {

    public static final String COMMAND_WORD = "note find";

    public static final String MESSAGE_INVALID_KEYWORD = "Invalid keyword!\n"
            + "A keyword should not have spaces in between and it should not be blank.";

    public static final String MESSAGE_NOT_FOUND = "No notes were found.";

    public static final String MESSAGE_SUCCESS = "Found %1$s note(s).";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds notes. "
            + "Parameters: "
            + PREFIX_NOTE_KEY_WORD + "KEYWORD "
            + "[" + PREFIX_NOTE_KEY_WORD + "MORE_KEYWORDS]..\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTE_KEY_WORD + "git "
            + PREFIX_NOTE_KEY_WORD + "OOP";

    private static final String REGEX_KEYWORD_OPEN = "(?=.*";
    private static final String REGEX_KEYWORD_CLOSE = ")";
    private static final String REGEX_STARTER = "(?i)^";
    private static final String REGEX_CLOSER = ".*$";

    private final List<String> keywords;

    public NoteFindCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        NoteManager noteManager = NoteManager.getInstance();

        String keywordsMatcherRegEx = buildKeywordsMatcherRegEx(keywords);
        noteManager.setFilteredNotesByKeyword(keywordsMatcherRegEx);

        if (noteManager.getFilteredNotes().size() == 0) {
            noteManager.refreshFilteredNotes();
            return new CommandResult(MESSAGE_NOT_FOUND);
        }

        int size = noteManager.getFilteredNotes().size();
        String noteList = noteManager.getHtmlNoteList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, size), noteList);
    }

    /**
     * Builds a regex string that can check if all
     *
     * @param keywords list containing all the required keywords
     * @return a working regex string that searches for all keywords
     */
    private String buildKeywordsMatcherRegEx(List<String> keywords) {
        StringBuilder sb = new StringBuilder();

        sb.append(REGEX_STARTER);
        for (String keyword : keywords) {
            sb.append(REGEX_KEYWORD_OPEN);
            sb.append(keyword);
            sb.append(REGEX_KEYWORD_CLOSE);
        }
        sb.append(REGEX_CLOSER);

        System.out.println(sb.toString());
        return sb.toString();
    }
}
