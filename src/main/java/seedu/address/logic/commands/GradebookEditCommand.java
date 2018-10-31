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

/**
 * Edits a gradebook component in Trajectory.
 */
public class GradebookEditCommand extends Command {
    public static final String COMMAND_WORD = "gradebook edit";
    public static final String MESSAGE_EDIT_GRADEBOOK_SUCCESS = "Edit success! \nNo of grade component: ";
    public static final String MESSAGE_FIND_FAIL = "Gradebook component does not exist!";
    public static final String MESSAGE_DUPLICATE_COMPONENT_NAME = "Edited component name already exist";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a gradebook component to Trajectory. "
            + "\nParameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE  "
            + PREFIX_GRADEBOOK_ITEM + "COMPONENT NAME ["
            + PREFIX_GRADEBOOK_ITEM_EDIT + "EDITED COMPONENT NAME] ["
            + PREFIX_GRADEBOOK_MAXMARKS + "EDITED MAX MARKS] ["
            + PREFIX_GRADEBOOK_WEIGHTAGE + "EDITED WEIGHTAGE]"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1 ["
            + PREFIX_GRADEBOOK_ITEM_EDIT + "Finals] ["
            + PREFIX_GRADEBOOK_MAXMARKS + "60] ["
            + PREFIX_GRADEBOOK_WEIGHTAGE + "50]";
    private static final String MESSAGE_INVALID_ENTRY = "You need to fill in at least one optional parameter!";
    private final Gradebook toEditGradebookItem;
    public GradebookEditCommand (Gradebook gradebookComponent) {
        toEditGradebookItem = gradebookComponent;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String newGradeComponentName = toEditGradebookItem.getgradebookNewComponentName();
        int newMaxMarks = toEditGradebookItem.getGradeComponentMaxMarks();
        int newWeightage = toEditGradebookItem.getGradeComponentWeightage();

        GradebookManager gradebookManager = new GradebookManager();
        Gradebook gradebook = gradebookManager.findGradebookComponent(
                toEditGradebookItem.getModuleCode(),
                toEditGradebookItem.getGradeComponentName());
        Gradebook checkIfComponentNameExist = gradebookManager.findGradebookComponent(
                toEditGradebookItem.getModuleCode(),
                toEditGradebookItem.getgradebookNewComponentName());
        if (gradebook == null) {
            return new CommandResult(MESSAGE_FIND_FAIL);
        }
        if (newGradeComponentName.equals("") && newMaxMarks == 0 && newWeightage == 0) {
            return new CommandResult(MESSAGE_INVALID_ENTRY);
        }
        if (!toEditGradebookItem.getgradebookNewComponentName().equals("")) {
            if (checkIfComponentNameExist == null) {
                gradebook.setGradeComponentName(toEditGradebookItem.getgradebookNewComponentName());
            } else {
                return new CommandResult(MESSAGE_DUPLICATE_COMPONENT_NAME);
            }
        }
        if (toEditGradebookItem.getGradeComponentMaxMarks() != 0) {
            gradebook.setgradebookMaxMarks(toEditGradebookItem.getGradeComponentMaxMarks());
        }
        if (toEditGradebookItem.getGradeComponentWeightage() != 0) {
            gradebook.setgradebookWeightage(toEditGradebookItem.getGradeComponentWeightage());
        }
        gradebookManager.saveGradebookList();
        String gradebookList = gradebookManager.listGradebookComponent();
        int size = gradebookManager.getGradebookSize();
        return new CommandResult(MESSAGE_EDIT_GRADEBOOK_SUCCESS + size + "\n" + "", gradebookList);
    }
}
