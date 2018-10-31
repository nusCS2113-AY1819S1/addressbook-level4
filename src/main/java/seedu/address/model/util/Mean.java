package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;
/**
 * this is the class for calculate mean value of certain test
 */
public class Mean {
    /**
     * Calculate the mean score of a test, the mean value should be double type
     */
    public static double calculateMean(ObservableList<Person> personList, String testName) {
        double sum = 0;

        ArrayList<PersonTest> list = SortMarks.sortingFromLowestToHighest(personList, testName);

        for (int k = 0; k < list.size(); k++) {

            sum += Double.parseDouble(list.get(k).getMarks().toString());
        }
        double mean = sum / list.size() * 1.0;
        return mean;
    }
}
