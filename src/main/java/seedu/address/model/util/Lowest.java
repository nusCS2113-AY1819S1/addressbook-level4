package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;
/**
 * this is the method to find lowest scores in certain test in GradeSummaryCommand
 */
public class Lowest {
    /**
     * this is the method to find lowest scores in certain test in GradeSummaryCommand
     */
    public double findLowest(ObservableList<Person> personList, String testName) {
        double minimum = 0;
        ArrayList<PersonTest> list = SortMarks.sortingFromLowestToHighest(personList, testName);

        return Double.parseDouble(list.get(0).getMarks().value);

    }
}
