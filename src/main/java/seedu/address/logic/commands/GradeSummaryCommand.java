package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEAN;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.util.Highest;
import seedu.address.model.util.LastTwentyFivePercen;
import seedu.address.model.util.Lowest;
import seedu.address.model.util.Mean;
import seedu.address.model.util.Median;
import seedu.address.model.util.TopTwentyFivePercen;
import seedu.address.ui.DisplayGrade;


/**
 * GradeSummary Command for Students, to display the highest, lowest, mean, median of certain test
 * also it can display top25 percentage ,last 25 percentage students for the lecture.
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
    public static final String MESSAGE_ERROR_COMMAND = "Invalid Command "
            + "or invalid test name, please check again and re input";
    private final String commandType;
    private final String testName;

    public GradeSummaryCommand (String command, String test) {
        requireNonNull(command, test);
        commandType = command;
        testName = test;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Highest highest = new Highest();
        Lowest lowest = new Lowest();
        Mean mean = new Mean();
        Median median = new Median();

        TopTwentyFivePercen topTwentyFivePercen = new TopTwentyFivePercen();
        LastTwentyFivePercen lastTwentyFivePercen = new LastTwentyFivePercen();

        DisplayGrade gradeDisplay;

        if ("TTF".equals(commandType)) {
            ArrayList<PersonTest> studentList = topTwentyFivePercen.findTopTwentyFive(model
                    .getFilteredPersonList(), testName);


            if (studentList.isEmpty()) {
                throw new CommandException(MESSAGE_ERROR);
            }

            gradeDisplay = new DisplayGrade(studentList);
            gradeDisplay.generateGradeList();

        }
        if ("LTF".equals(commandType)) {
            ArrayList<PersonTest> studentList = lastTwentyFivePercen.findLastTwentyFive(model
                    .getFilteredPersonList(), testName);


            if (studentList.isEmpty()) {
                throw new CommandException(MESSAGE_ERROR);
            }

            gradeDisplay = new DisplayGrade(studentList);
            gradeDisplay.generateGradeList();

        }
        if ("H".equals(commandType)) {
            double highestScore = highest.findHighest(model.getFilteredPersonList(), testName);
            return new CommandResult(String.format("Highest Score of this test is " + highestScore));
        }
        if ("L".equals(commandType)) {
            double highestScore = lowest.findLowest(model.getFilteredPersonList(), testName);
            return new CommandResult(String.format("Lowest Score of this test is " + highestScore));
        }
        if ("MEAN".equals(commandType)) {
            double meanVal = mean.calculateMean(model.getFilteredPersonList(), testName);
            return new CommandResult(String.format("The mean of this test is " + meanVal));
        }
        if ("MEDIAN".equals(commandType)) {
            double medianVal = median.calculateMedian(model.getFilteredPersonList(), testName);
            return new CommandResult(String.format("Median of this test is " + medianVal));
        }

        return new CommandResult(String.format(MESSAGE_ERROR_COMMAND));

    }

}

