package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grade.Test;
import seedu.address.model.person.Person;
import seedu.address.model.util.AssignGrades;

public class AssignGradeCommand extends Command {
    public static final String COMMAND_WORD = "AssignGrade";
    public static final String COMMAND_WORD_2 = "ag";

    public static final String MESSAGE_GENERATE_GRADE_LIST = "grade of the test has been assign to student";
    public static final String MESSAGE_EMPTY_LIST = "The list is currently empty";

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        AssignGrades ag = new AssignGrades();


        for (Person person: model.getFilteredPersonList()) {
            for (Test test : person.getTests()) {
                String testGrade = ag.assignGradeByMarks(test.getTestName().testName, test.getMarks().value, person, model.getFilteredPersonList());
                Command run = ag.updateGradeOfPersonList(person, test.getTestName().testName, test.getMarks().value, testGrade);
                run.execute(model, history);
            }
        }
        return new CommandResult(String.format(MESSAGE_GENERATE_GRADE_LIST));
    }
}
