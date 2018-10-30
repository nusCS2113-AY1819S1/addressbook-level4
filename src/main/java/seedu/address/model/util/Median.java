package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;
/**
 * this is the method to find median scores in certain test in GradeSummaryCommand
 */
public class Median {
    /**
     * this is the method to find median scores in certain test in GradeSummaryCommand
     */
    public double calculateMedian(ObservableList<Person> personList, String testName) {
        //calculate the mean score of a test
        double median = 0;
        ArrayList<PersonTest> sortedList =
                new ArrayList<PersonTest>(SortMarks.sortingFromLowestToHighest(personList, testName));

        int size = sortedList.size();
        int index = (size / 2) - 1;
        if (size % 2 == 0) {
            median = (Double.parseDouble(sortedList.get(index).getMarks().toString())
                    + Double.parseDouble(sortedList.get(index + 1).getMarks().toString())) / 2.0;
        } else {
            median = Double.parseDouble(sortedList.get(index + 1).getMarks().toString());
        }
        System.out.println(median);
        return median;
    }
}
