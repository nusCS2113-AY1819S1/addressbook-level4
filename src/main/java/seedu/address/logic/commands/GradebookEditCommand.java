package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MAXMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_WEIGHTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

public class GradebookEditCommand extends Command {
    public static final String COMMAND_WORD = "gradebook edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a gradebook component to Trajectory. "
            + "\nParameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE  "
            + PREFIX_GRADEBOOK_ITEM + "COMPONENT NAME "
            + PREFIX_GRADEBOOK_ITEM_EDIT + "[EDITED COMPONENT NAME]"
            + PREFIX_GRADEBOOK_MAXMARKS + "[EDITED MAX MARKS] "
            + PREFIX_GRADEBOOK_WEIGHTAGE + "[EDITED WEIGHTAGE]"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1 "
            + PREFIX_GRADEBOOK_ITEM_EDIT + "Finals "
            + PREFIX_GRADEBOOK_MAXMARKS + "60 "
            + PREFIX_GRADEBOOK_WEIGHTAGE + "50";

    public static final String MESSAGE_EDIT_GRADEBOOK_SUCCESS = "Edited Gradebook Component Name: %1$s,"
            + "Maximum Marks: %2$s, Weightage: %3$s";
    private static final String MESSAGE_FIND_FAIL = "Unsuccessful find";

    private final Gradebook toEditGradebookItem;
    public GradebookEditCommand (Gradebook gradebookComponent) {
        toEditGradebookItem = gradebookComponent;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        GradebookManager gradebookManager = new GradebookManager();
        Gradebook gradebook = gradebookManager.findGradebookComponent(
                toEditGradebookItem.getModuleCode(),
                toEditGradebookItem.getGradeComponentName());
        if (gradebook == null) {
            return new CommandResult(MESSAGE_FIND_FAIL);
        }

        gradebookManager.editGradebookComponent(gradebook, );








        return new CommandResult(result);
    }

    public void setGradebookComponentName(String gradebookComponentName) {
        this.gradebookComponentName = gradebookComponentName;
    }
    public void setGradebookMaxMarks(String gradebookMaxMarks) {
        this.gradebookMaxMarks = gradebookMaxMarks;
    }
    public void setGradebookWeightage(String gradebookWeightage) {
        this.gradebookWeightage = gradebookWeightage;
    }
}
