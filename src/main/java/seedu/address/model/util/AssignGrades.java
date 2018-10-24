package seedu.address.model.util;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

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
    public String assignGradeByMarks(String testName, String marks, Person person, List<Person> personList) {

        double meanVal = Mean.calculateMean(personList, testName);
        double SD = StandardDeviation.calculateStandardDeviation(personList, testName);
        int marksVal = Integer.valueOf(marks);

        if (((meanVal - 2 * SD) > marksVal) && (marksVal >= (meanVal - 3 * SD))) {
            return "F";
        } else if (((meanVal - SD) > marksVal) && (marksVal >= (meanVal - 2 * SD))) {
            return "D";
        } else if ((meanVal > marksVal) && (marksVal >= (meanVal - SD))) {
            return "C";
        } else if (((meanVal + SD) > marksVal) && (marksVal >= meanVal)) {
            return "C";
        } else if (((meanVal + 2 * SD) > marksVal) && (marksVal >= (meanVal + SD))) {
            return "B";
        } else if (((meanVal + 3 * SD) > marksVal) && (marksVal >= (meanVal + 2 * SD))) {
            return "A";
        }
        return "A";
    }

    /**
     * A method to assign grade to students
     */
    public Command updateGradeOfPersonList(Person person, String testName, String marks, String grade) {
        List<String> name = new ArrayList<>();
        //put name into predicate list
        String[] splited = person.getName().fullName.split("\\s+");
        for (String s: splited) {
            name.add(s);
        }
        return new EditTestMarksCommand(new NameContainsKeywordsPredicate(name), testName, marks, grade);

    }
}
