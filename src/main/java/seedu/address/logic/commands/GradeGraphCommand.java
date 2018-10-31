package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;
import seedu.address.ui.GradeGraphDisplay;

/**
 * Shows grades of student in a graph form in Trajectory.
 */
public class GradeGraphCommand extends Command {
    public static final String COMMAND_WORD = "grade graph";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a graph to show scores of all students"
            + " for one grade component.\n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";
    private static final String MESSAGE_GRAPH_SUCCESS = "\nSuccessfully Shown!";

    private final Grades toGraphGrade;

    public GradeGraphCommand (Grades grade) {
        this.toGraphGrade = grade;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        GradesManager gradesManager = new GradesManager();
        GradeGraphDisplay gradeGraphDisplay = new GradeGraphDisplay();
        gradeGraphDisplay.configureLineChart();
        gradesManager.createGraph(gradeGraphDisplay.getLineChart(), toGraphGrade);
        gradeGraphDisplay.initGraph();
        gradeGraphDisplay.showStage();

        return new CommandResult(MESSAGE_GRAPH_SUCCESS);
    }

}
