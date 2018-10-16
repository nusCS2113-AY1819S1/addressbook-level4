package seedu.address.model.distribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

public class DistributeAlgorithmTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ObservableList<Person> allPersons = model.getFilteredPersonList();
    private Distribute distribute = new Distribute(5, new GroupName("TUT-"), false, false);

    @Test
    public void shuffleTest() {
        DistributeAlgorithm testPerson = new DistributeAlgorithm();
        Random testValue = new Random(96259561);
        LinkedList<Person> allPersonTemp = new LinkedList<>(allPersons);
        Collections.shuffle(allPersonTemp, testValue);
        assertEquals(testPerson.shuffle(allPersonTemp, testValue), allPersonTemp);
    }



}
