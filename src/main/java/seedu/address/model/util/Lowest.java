package seedu.address.model.util;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

public class Lowest {
    public double findLowest(ObservableList<Person> personList, String testName) {
        double minimum = 0;
        ArrayList<Person> list = new ArrayList<>(); // this is the list contains persons that take certain test(user defined)
        for (int i = 0; i < personList.size(); i++) {
            Iterator it = personList.get(i).getTests().iterator();
            if (it.next().equals(testName)) {
                list.add(personList.get(i));
            }
        }
        for (int k = 0; k < list.size(); k++) {
            double currentVal = Double.parseDouble(list.get(k).getTests().iterator().next().getMarks().toString());
            minimum = min(currentVal, minimum);
        }
        return minimum;
    }
}
