package seedu.address.model.util;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;

public class Highest {


    public double findHighest(ObservableList<Person> personList, String testName) {
        ArrayList<PersonTest> list = SortMarks.sortingFromLowestToHighest(personList, testName);

        return Double.parseDouble(list.get(list.size() - 1).getMarks().value);
    }
}
