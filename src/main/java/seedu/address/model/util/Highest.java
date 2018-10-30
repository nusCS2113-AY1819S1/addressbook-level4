package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;
/**
 * this is the method to find highest scores in certain test in GradeSummaryCommand
 */
public class Highest {

    /**
     * this is the method to find highest scores in certain test in GradeSummaryCommand
     */
    public double findHighest(ObservableList<Person> personList, String testName) {
        ArrayList<PersonTest> list = SortMarks.sortingFromLowestToHighest(personList, testName);

        return Double.parseDouble(list.get(list.size() - 1).getMarks().value);
    }
}
