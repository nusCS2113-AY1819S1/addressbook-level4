package seedu.address.model.util;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;

import seedu.address.logic.commands.Command;

import seedu.address.logic.commands.EditTestMarksCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
/**
 * A class to grading students
 */
public class AssignGrades {

    private final Logger logger = LogsCenter.getLogger(getClass());
    /**
     * A method to calculate student grade base on their marks
     */
    public String assignGradeByMarks(String testName, String marks, Person person, ObservableList<Person> personList) {

        double meanVal = Mean.calculateMean(personList, testName);
        double sd = StandardDeviation.calculateStandardDeviation(personList, testName);
        double marksVal = Double.valueOf(marks);
        if (marksVal == 0) {
            return "F";
        } else if (((meanVal - 2 * sd) > marksVal) && (marksVal >= (meanVal - 3 * sd))) {
            return "F";
        } else if (((meanVal - sd) > marksVal) && (marksVal >= (meanVal - 2 * sd))) {
            return "D";
        } else if ((meanVal > marksVal) && (marksVal >= (meanVal - sd))) {
            return "C";
        } else if (((meanVal + sd) > marksVal) && (marksVal >= meanVal)) {
            return "C";
        } else if (((meanVal + 2 * sd) > marksVal) && (marksVal >= (meanVal + sd))) {
            return "B";
        } else if (((meanVal + 3 * sd) > marksVal) && (marksVal >= (meanVal + 2 * sd))) {
            return "A";
        }
        return "C";
    }

    /**
     * This method is to update the grade we calculated above in
     */
    public Command updateGradeOfPersonList(Person person, String testName, String marks, String grade) {
        List<String> name = new ArrayList<>();
        //put name into predicate list
        String[] splited = person.getName().fullName.split("\\s+");
        for (String s: splited) {
            name.add(s);
        }
        return new EditTestMarksCommand(new NameContainsKeywordsPredicate(name), testName, marks, grade, name);

    }
}
