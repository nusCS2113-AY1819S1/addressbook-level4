package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.util.Calculator;

import seedu.address.ui.DisplayGrade;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEAN;


/**
 * GradeSummary Command for Student
 */
public class GradeSummaryCommand extends Command {

    public static final String COMMAND_WORD = "display";
    public static final String COMMAND_WORD_2 = "disp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": display student grades summary "
            + "Parameters: "
            + PREFIX_MEAN
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEAN;

    public static final String MESSAGE_SUCCESS = "Success Showing List";
    public static final String MESSAGE_ERROR = "ERROR showing List";

    private final String commandType;

    public GradeSummaryCommand (String command) {
        requireNonNull(command);
        commandType = command;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Calculator calc = new Calculator();
        DisplayGrade gradeDisplay;
        if ("25".equals(commandType)) {
            ArrayList<Person> studentList = calc.find25th(model.getFilteredPersonList());


            if (studentList.isEmpty()) {
                throw new CommandException(MESSAGE_ERROR);
            }

            gradeDisplay = new DisplayGrade(studentList);
            gradeDisplay.generateGradeList();

        }
        if ("75".equals(commandType)) {
            ArrayList<Person> studentList = calc.find75th(model.getFilteredPersonList());


            if (studentList.isEmpty()) {
                throw new CommandException(MESSAGE_ERROR);
            }

            gradeDisplay = new DisplayGrade(studentList);
            gradeDisplay.generateGradeList();

        }
        if ("H".equals(commandType)) {
            int highestScore = calc.findHighest(model.getFilteredPersonList());
            return new CommandResult(String.format("Highest Score in class is " + highestScore));
        }
        if ("MEAN".equals(commandType)) {
            int mean = calc.calculateMean(model.getFilteredPersonList());
            return new CommandResult(String.format("The mean is " + mean));
        }
        if("MEDIAN".equals(commandType)){
            int median = calc.calculateMedian(model.getFilteredPersonList());
            return new CommandResult(String.format("Median in class is " + median));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}