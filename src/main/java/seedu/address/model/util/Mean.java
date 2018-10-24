package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
/**
 * somthing
 */
public class Mean {
    /**
     * something
     */
    public static double calculateMean(List<Person> personList, String testName) { //calculate the mean score of a test
        double sum = 0;

        ArrayList<Person> list = FormStudentListForGrade.formStudentlistForCalculation(personList, testName);

        for (int k = 0; k < list.size(); k++) {

            sum += Double.parseDouble(list.get(k).getTests().iterator().next().getMarks().toString()); //string to integer then calculate
        }
        double mean = sum / personList.size() * 1.0;
        return mean;
    }
}
