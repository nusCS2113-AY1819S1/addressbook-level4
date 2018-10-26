package seedu.address.model.util;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;
/**
 * this is the class for calculate mean value of certain test
 */
public class Mean {
    /**
     * Calculate the mean score of a test, the mean value should be double type
     */
    public static double calculateMean(List<Person> personList, String testName) {
        double sum = 0;

        ArrayList<Person> list = FormStudentListForGrade.formStudentListForCalculation(personList, testName);

        for (int k = 0; k < list.size(); k++) {

            sum += Double.parseDouble(list.get(k).getTests().iterator().next().getMarks().toString());
        }
        double mean = sum / list.size() * 1.0;
        return mean;
    }
}
