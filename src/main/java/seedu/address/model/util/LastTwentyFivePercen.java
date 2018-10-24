package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

public class LastTwentyFivePercen {
    /**
     * find the last 25 percent student's list.
     */
    public ArrayList<Person> findLastTwentyFive(ObservableList<Person> personList, String testName) {
        ArrayList<Person> sortedList =
                new ArrayList<Person>(SortMarks.sortingFromLowestToHighest(personList, testName));

        ArrayList<Person> theList = new ArrayList<Person>();
        int getListSize = sortedList.size();
        double getLast = getListSize * 0.25;
        int personLast = (int) Math.floor(getLast);

        for (int i = 0; i < personLast; i++) {
            theList.add(sortedList.get(i));

        }

        return theList;
    }
}
