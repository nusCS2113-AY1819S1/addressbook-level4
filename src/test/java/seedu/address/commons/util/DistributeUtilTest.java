package seedu.address.commons.util;

import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonNationalityMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

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

    private ObservableList<Person> setUpObservableListStub() {
        AddressBook stubAddressBook = getTypicalAddressBook();
        UserPrefs stubUserPrefs = new UserPrefs();
        Model model = new ModelManager(stubAddressBook, stubUserPrefs);
        ObservableList<Person> allPersonStub = model.getFilteredPersonList();
        return allPersonStub;
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
        ObservableList<Person> allPersonsStub = setUpObservableListStub();
        LinkedList<Person> allPersonLlStub = new LinkedList<>(allPersonsStub);
        Nationality sgNationality = new Nationality("SG");
        Person actualPerson = distUtil.findPerson(sgNationality, allPersonLlStub);

        // testing if it return the first person it found with SG nationality
        assertEquals(ALICE, actualPerson);

        // testing that it should not be equal since daniel is the 2nd person with SG
        assertNotEquals(DANIEL, actualPerson);

        //remove ALICE and check if it is able to find DANIEL now.
        allPersonLlStub.removeFirst();
        actualPerson = distUtil.findPerson(sgNationality, allPersonLlStub);
        assertEquals(DANIEL, actualPerson);

        // testing if there is no person with SK nationality is not found
        Nationality skNationality = new Nationality("SK");
        actualPerson = distUtil.findPerson(skNationality, allPersonLlStub);
        assertNull(actualPerson);
    }

    @Test
    public void numberOfDifferentNationalityTest() {
        ObservableList<Person> allPersonsStub = setUpObservableListStub();
        LinkedList<Person> allPersonLlStub = new LinkedList<>(allPersonsStub);
        Map<Nationality, Long> actualMap = distUtil.numberOfDifferentNationality(allPersonLlStub);
        Map<Nationality, Long> expectedMap = getTypicalPersonNationalityMap();

        //Comparing both object are the same.
        assertEquals(expectedMap, actualMap);

        //reduce SG nationality counter by 1.
        Map<Nationality, Long> unexpectedMap = expectedMap;
        unexpectedMap.replace(new Nationality("SG"), Long.parseLong("3"));
        assertNotEquals(unexpectedMap, actualMap);

        //Comparing Map value is different when there is and extra person.
        unexpectedMap.replace(new Nationality("SG"), Long.parseLong("4"));
        unexpectedMap.put(new Nationality("IN"), Long.parseLong("1"));
        assertNotEquals(unexpectedMap, actualMap);

        //empty person list which creates a empty map
        allPersonLlStub.clear();
        actualMap = distUtil.numberOfDifferentNationality(allPersonLlStub);
        expectedMap.clear();
        assertEquals(expectedMap, actualMap);

    }

    @Test
    public void paxPerNationalityTest() {
        ObservableList<Person> allPersonsStub = setUpObservableListStub();
        LinkedList<Person> allPersonLlStub = new LinkedList<>(allPersonsStub);
        Map<Nationality, Long> differentNationalityMap = distUtil.numberOfDifferentNationality(allPersonLlStub);
        Map<Nationality, Long> actualSortedMap = distUtil.paxPerNationality(differentNationalityMap);
        String expectedResult = "{SG=4, US=2, MY=1}";

        //expects the map to be sorted in value order.
        assertEquals(expectedResult, actualSortedMap.toString());

        //Comparing the 2 map which are sorted by value. actual is sorted by value. unexpected is not sorted.
        Map<Nationality, Long> unexpectedMap = getTypicalPersonNationalityMap();
        assertNotEquals(unexpectedMap.toString(), actualSortedMap.toString());

        //empty person list
        allPersonLlStub.clear();
        differentNationalityMap = distUtil.numberOfDifferentNationality(allPersonLlStub);
        Map<Nationality, Long> actualEmptyMap = distUtil.paxPerNationality(differentNationalityMap);
        expectedResult = "{}";
        assertEquals(expectedResult, actualEmptyMap.toString());
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
