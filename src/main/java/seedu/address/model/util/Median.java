package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

public class Median {
    public double calculateMedian(ObservableList<Person> personList, String testName) { //calculate the mean score of a test
        double median = 0;
        ArrayList<Person> sortedList =
                new ArrayList<Person>(SortMarks.sortingFromLowestToHighest(personList, testName));

        int size = sortedList.size();
        int index = (size / 2) - 1;
        if (size % 2 == 0) {
            median = (Double.parseDouble(sortedList.get(index).getTests().iterator().next().getMarks().toString())
                    + Double.parseDouble(sortedList.get(index + 1).getTests().iterator().next().getMarks().toString())) / 2.0;
        } else {
            median = Double.parseDouble(sortedList.get(index + 1).getTests().iterator().next().getMarks().toString());
        }
        System.out.println(median);
        return median;
    }
}
