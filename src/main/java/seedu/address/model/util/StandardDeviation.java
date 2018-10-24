package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
/**
 * calculate the Standard Deviation values of student's scores.
 */
public class StandardDeviation {
    /**
     * calculate the Standard Deviation values of student's scores.
     */
    public static double calculateStandardDeviation(List<Person> personList, String testName)
    {
        double sum = 0.0;
        double standardDeviation = 0.0;
        ArrayList<Person> list = new ArrayList<>(); // this is the list contains persons that take certain test(user defined)
        for (int i = 0; i < personList.size(); i++) {
            Iterator it = personList.get(i).getTests().iterator();
            if (it.next().equals(testName)) {
                list.add(personList.get(i));
            }
        }
        int size = list.size();
        double mean = Mean.calculateMean(personList, testName);

        for(int k = 0; k < personList.size(); k++) {
            standardDeviation += Math.pow(Double.parseDouble(list.get(k).getTests()
                    .iterator().next().getMarks().toString()) - mean, 2);
        }
        double SD = Math.sqrt(standardDeviation / size);
        return SD;
    }
}
