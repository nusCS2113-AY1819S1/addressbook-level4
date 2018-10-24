package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

public class TopTwentyFivePercen {
    /**
     * find the top 25 percent student's list
     */
    public ArrayList<Person> findTopTwentyFive(ObservableList<Person> personList, String testName) {
        ArrayList<Person> sortedList =
                new ArrayList<Person>(SortMarks.sortingFromLowestToHighest(personList, testName));

        ArrayList<Person> theTopList = new ArrayList<Person>();
        int getListSize = sortedList.size();
        double getTop = getListSize * 0.25;
        int personTop = (int) Math.floor(getTop);

        for (int i = 0; i < personTop; i++) {
            theTopList.add(sortedList.get(i));

        }

        return theTopList;
    }
}
