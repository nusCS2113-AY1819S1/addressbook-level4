package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEAN;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.util.Highest;
import seedu.address.model.util.LastTwentyFivePercen;
import seedu.address.model.util.Lowest;
import seedu.address.model.util.Mean;
import seedu.address.model.util.Median;
import seedu.address.model.util.TopTwentyFivePercen;
import seedu.address.ui.DisplayGrade;




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
        Highest highest = new Highest();
        Lowest lowest = new Lowest();
        Mean mean = new Mean();
        LastTwentyFivePercen lastTwentyFivePercen = new LastTwentyFivePercen();
        Median median = new Median();
        TopTwentyFivePercen topTwentyFivePercen = new TopTwentyFivePercen();



        DisplayGrade gradeDisplay;
        if ("25".equals(commandType)) {
            ArrayList<Person> studentList = topTwentyFivePercen.findTopTwentyFive(model.getFilteredPersonList(), null);


            if (studentList.isEmpty()) {
                throw new CommandException(MESSAGE_ERROR);
            }

            gradeDisplay = new DisplayGrade(studentList);
            gradeDisplay.generateGradeList();

        }
        if ("75".equals(commandType)) {
            ArrayList<Person> studentList = lastTwentyFivePercen.findLastTwentyFive(model.getFilteredPersonList(), null);


            if (studentList.isEmpty()) {
                throw new CommandException(MESSAGE_ERROR);
            }

            gradeDisplay = new DisplayGrade(studentList);
            gradeDisplay.generateGradeList();

        }
        if ("H".equals(commandType)) {
            double highestScore = highest.findHighest(model.getFilteredPersonList(), null);
            return new CommandResult(String.format("Highest Score in class is " + highestScore));
        }
        if ("MEAN".equals(commandType)) {
            double meanVal = mean.calculateMean(model.getFilteredPersonList(), null);
            return new CommandResult(String.format("The mean is " + mean));
        }
        if ("MEDIAN".equals(commandType)) {
            double medianVal = median.calculateMedian(model.getFilteredPersonList(), null);
            return new CommandResult(String.format("Median in class is " + median));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }

}

