package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.StudentManager;
import seedu.address.ui.HtmlTableProcessor;

/**
 * This class is designed for debug use only.
 */
public class DebugCommand extends Command {
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        StringBuilder sb = new StringBuilder();
        sb.append(StudentManager.getInstance().retrieveStudentByMatricNo("A01").getEmail());
        return new CommandResult("DEBUG COMMAND EXECUTED. ",
                HtmlTableProcessor.getH1Representation(sb.toString()));
    }
}
