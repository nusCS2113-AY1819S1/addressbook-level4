package seedu.address.model.util;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;

public class Lowest {
    public double findLowest(ObservableList<Person> personList, String testName) {
        double minimum = 0;
        ArrayList<PersonTest> list = SortMarks.sortingFromLowestToHighest(personList, testName);

        return Double.parseDouble(list.get(0).getMarks().value);

    }
}
