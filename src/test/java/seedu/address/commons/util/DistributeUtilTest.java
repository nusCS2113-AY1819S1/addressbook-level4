package seedu.address.commons.util;

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
        DistributeUtil testPerson = new DistributeUtil();
        Random testValue = new Random(96259561);
        LinkedList<Person> allPersonList = new LinkedList<>(allPersons);
        Collections.shuffle(allPersonList, testValue);
        assertTrue(testPerson.shuffle(allPersonList, testValue).equals(allPersonList));
    }

    //TODO junit testing for distutil
    @Test
    public void createNationalityMapTest() {
        //create a stub
    }

    @Test
    public void findPersonTest() {
        // put a stub typical book
        // find for Alice
        // assert equal Alice object with actual object

    }

    @Test
    public void numberOfDifferentNationalityTest() {

    }

    @Test
    public void paxPerNationalityTest() {

    }

    @Test
    public void selectiveDistributionByGenderTest() {

    }

    @Test
    public void selectiveDistributionByNationalityTest() {

    }

    @Test
    public void filterGenderTest() {

    }

    @Test
    public void groupNameConcatenationTest() {

    }

    @Test
    public void existDuplicateGroupTest() {

    }

    @Test
    public void doesGroupNameExistTest() {

    }

    @Test
    public void groupBuilderTest() {

    }

    @Test
    public void createGroupTest() {

    }

    @Test
    public void addPersonIntoGroupTest() {

    }

    @Test
    public void returnGroupIndexTest() {

    }

    @Test
    public void distributeProcessTest() {
    }
}
