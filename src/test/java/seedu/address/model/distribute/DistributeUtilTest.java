package seedu.address.model.distribute;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class DistributeUtilTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ObservableList<Person> allPersons = model.getFilteredPersonList();

    @Test
    public void shuffleTest() {
        DistributeAlgorithm testPerson = new DistributeAlgorithm();
        Random testValue = new Random(96259561);
        LinkedList<Person> allPersonList = new LinkedList<>(allPersons);
        Collections.shuffle(allPersonList, testValue);
        assertTrue(testPerson.shuffle(allPersonList, testValue).equals(allPersonList));
    }
}
