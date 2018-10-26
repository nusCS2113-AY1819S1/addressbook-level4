package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Person;

public class DistributeUtilTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ObservableList<Person> allPersons = model.getFilteredPersonList();
    private DistributeUtil distUtil = new DistributeUtil();

    private Model setUpModelStub() {
        AddressBook stubAddressBook = getTypicalAddressBook();
        UserPrefs stubUserPrefs = new UserPrefs();
        return new ModelManager(stubAddressBook, stubUserPrefs);
    }

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
        Model model = setUpModelStub();
        ObservableList<Person> allPersonStub = model.getFilteredPersonList();
        LinkedList<Person> allPersonLLStub = new LinkedList<>(allPersonStub);

        Nationality sgNationality = new Nationality("SG");
        Person actualPerson = distUtil.findPerson(sgNationality, allPersonLLStub);

        // testing if it return the first person it found with SG nationality
        assertEquals(ALICE, actualPerson);

        // testing that it should not be equal since daniel is the 2nd person with SG
        assertNotEquals(DANIEL, actualPerson);

        //remove ALICE and check if it is able to find DANIEL now.
        allPersonLLStub.removeFirst();
        actualPerson = distUtil.findPerson(sgNationality, allPersonLLStub);
        assertEquals(DANIEL, actualPerson);

        // testing if there is no person with SK nationality is not found
        Nationality skNationality = new Nationality("SK");
        actualPerson = distUtil.findPerson(skNationality, allPersonLLStub);
        assertNull(actualPerson);
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
