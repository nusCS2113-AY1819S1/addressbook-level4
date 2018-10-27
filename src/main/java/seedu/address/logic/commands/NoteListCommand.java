package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteManager;
import seedu.address.ui.HtmlCardProcessor;

/**
 * Lists notes based on given predicate.
 */
public class NoteListCommand extends Command {

    public static final String COMMAND_WORD = "note list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists notes. "
            + "Parameters: "
            + "[" + PREFIX_MODULE_CODE + "MODULE_CODE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113";

    public static final String MESSAGE_SUCCESS = "Listed %1$s note(s).";

    public static final String MESSAGE_NOT_FOUND = "No notes were found.";

    private final String moduleCode;

    public NoteListCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        NoteManager noteManager = NoteManager.getInstance();

        noteManager.setFilteredNotes(moduleCode);

        if (noteManager.getFilteredNotes().size() == 0) {
            noteManager.setFilteredNotes(noteManager.getCurrentFilter());
            return new CommandResult(String.format(MESSAGE_NOT_FOUND));
        }

        StringBuilder sb = new StringBuilder();

        int listId = 1;
        int size = noteManager.getFilteredNotes().size();

        for (Note note: noteManager.getFilteredNotes()) {
            sb.append(HtmlCardProcessor.getCardStartV2());
            sb.append(HtmlCardProcessor.renderCardHeader(
                    "h4", "#" + listId++ + "&nbsp;&nbsp;" + note.getTitle()));
            sb.append(HtmlCardProcessor.getCardBodyStart());
            sb.append(HtmlCardProcessor.renderCardTitle(note.getModuleCode()));
            sb.append(HtmlCardProcessor.renderCardSubtitle(
                    "From " + note.getStartDate() + " " + note.getStartTime()
                            + " to " + note.getEndDate() + " " + note.getEndTime()));
            sb.append(HtmlCardProcessor.renderCardSubtitle(note.getLocation()));
            sb.append(HtmlCardProcessor.renderCardText(note.getNoteText()
                    .replaceAll(" ", "&nbsp;")
                    .replaceAll("\n", "<br>")
                    .replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")));
            sb.append(HtmlCardProcessor.getDivEndTag());
            sb.append(HtmlCardProcessor.getDivEndTag());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS + "\n", size), sb.toString());
    }
}
