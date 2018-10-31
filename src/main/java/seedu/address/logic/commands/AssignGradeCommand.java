package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grade.Test;
import seedu.address.model.person.Person;
import seedu.address.model.util.AssignGrades;
/**
 * AssignGrade Command for Students, to assign grades to each student who has token certain
 * test(use bell_curve calculate method)
 */
public class AssignGradeCommand extends Command {
    public static final String COMMAND_WORD = "AssignGradePerTest";
    public static final String COMMAND_WORD_2 = "agpt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": assign grade to all students who has taken certain test\n"
            + "the correct format should be.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD_2 + " " + PREFIX_TEST_NAME + "cs2113quiz1 ";

    public static final String MESSAGE_TEST_NOT_EXISTS = "Test does not exists in all students";
    public static final String MESSAGE_GENERATE_GRADE_LIST = "grade of the test has been assign to student";



    private final String testName;
    public AssignGradeCommand(String test) {
        this.testName = test;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        AssignGrades ag = new AssignGrades();

        boolean checkExist = false;
        for (Person person: model.getFilteredPersonList()) {
            for (Test test : person.getTests()) {
                if (testName.equals(test.getTestName().testName)) {
                    checkExist = true;
                    String testGrade = ag.assignGradeByMarks(test.getTestName().testName, test.getMarks().value,
                            person, model.getFilteredPersonList());
                    Command run = ag.updateGradeOfPersonList(person, test.getTestName().testName,
                            test.getMarks().value, testGrade);
                    run.execute(model, history);
                }
            }
        }
        if (!checkExist) {
            throw new CommandException(MESSAGE_TEST_NOT_EXISTS);
        }
        return new CommandResult(String.format(MESSAGE_GENERATE_GRADE_LIST));
    }
}
