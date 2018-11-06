package seedu.address.model.distribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.Gender.VALID_GENDER_FEMALE;
import static seedu.address.model.person.Gender.VALID_GENDER_MALE;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DistributeUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Person;

/**
 * DistributeAlgorithm class contains all the algorithm that is based on distinto command flags
 * This will contain 4 different types of Algorithm.
 * First - normalDistribution
 * Second - genderDistribution
 * Third - nationalityDistribution
 * Forth - Gender & Nationality Distribution
 */
public class DistributeAlgorithm {

    public static final String MESSAGE_INVALID_SIZE = "Number of Groups should not be more than Number of Persons";
    public static final String MESSAGE_TO_BE_IMPLEMENTED = "Currently under development. Stay tuned in v2.0";
    public static final String MESSAGE_FLAG_ERROR = "Gender and Nationality flags only accept "
            + "'1' or '0' or \"true\" or \"false\"";
    private static final Logger logger = LogsCenter.getLogger(DistributeAlgorithm.class);
    private DistributeUtil distUtil = new DistributeUtil();

    public DistributeAlgorithm(Model model, Distribute distribute) throws CommandException {
        requireNonNull(distribute);
        distUtil.setModel(model);

        int numOfGroups = distribute.getIndex();
        String groupName = distribute.getGroupName().toString();
        boolean isGenderFlagSet = distribute.getGender();
        boolean isNationalityFlagSet = distribute.getNationality();
        ArrayList<ArrayList<Person>> groupArrayList = new ArrayList<>();

        // Get all person data via ObservableList
        ObservableList<Person> allPerson = model.getFilteredPersonList();
        requireNonNull(allPerson);

        //Checks if numOfGroups intended to be created is > number of people
        if (allPerson.size() < numOfGroups) {
            throw new CommandException(MESSAGE_INVALID_SIZE);
        }
        distUtil.doesGroupNameExist(numOfGroups, groupName, model);
        //Convert ObservableList into ArrayList to use Randomizer via Collections
        LinkedList<Person> personLinkedList = new LinkedList<>(allPerson);
        //Randomizer function
        Instant instant = Instant.now();
        distUtil.shuffle(personLinkedList, new Random(instant.getEpochSecond()));
        if (!isGenderFlagSet && !isNationalityFlagSet) {
            logger.log(Level.INFO, "Normal Distribution will be executed.");
            normalDistribution(numOfGroups, groupArrayList, personLinkedList, groupName);
        } else if (!isGenderFlagSet && isNationalityFlagSet) {
            logger.log(Level.INFO, "Distribution by Nationality will be executed.");
            nationalityDistribution(numOfGroups, groupArrayList, personLinkedList, groupName);
        } else if (isGenderFlagSet && !isNationalityFlagSet) {
            logger.log(Level.INFO, "Distribution by Gender will be executed.");
            genderDistribution(numOfGroups, groupArrayList, personLinkedList, groupName);
        } else {
            logger.log(Level.INFO, "Distribution by Nationality and Gender will be executed.");
            strictDistribution(numOfGroups, groupArrayList, personLinkedList, groupName);
        }
        logger.log(Level.INFO, "Distribution has been completed.");
    }

    /**
     * This Method distribute all students into n number of groups
     * Distribution is random.
     */
    private void normalDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> personLinkedList,
                                    String groupName) throws CommandException {
        for (int i = index; i > 0; i--) { //number of groups to add into the groupArrayList
            ArrayList<Person> addPerson = new ArrayList<>();
            int paxInAGroup = personLinkedList.size() / i;
            while (paxInAGroup > 0) {
                addPerson.add(personLinkedList.getLast());
                personLinkedList.removeLast();
                paxInAGroup--;
            }
            groupArrayList.add(addPerson);
        }
        distUtil.distributeProcess(groupArrayList, groupName);
        groupArrayList.clear();
    }

    /**
     * This Method distribute all students into n number of groups.
     * Distribution will try to achieve an balance number of gender in a group.
     * Distribution is Random
     */
    private void genderDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> allPersonLinkList, String groupName)
            throws CommandException {
        LinkedList<Person> maleLinkList = new LinkedList<>();
        LinkedList<Person> femaleLinkList = new LinkedList<>();
        int loopCounter = 0;
        int num = 0;

        distUtil.filterGender(allPersonLinkList, maleLinkList, VALID_GENDER_MALE);
        distUtil.filterGender(allPersonLinkList, femaleLinkList, VALID_GENDER_FEMALE);

        while (maleLinkList.size() != 0 || femaleLinkList.size() != 0) {
            if (loopCounter % index == 0) {
                num = 0;
            }
            while (num < index) {
                if (maleLinkList.size() == 0 && femaleLinkList.size() == 0) {
                    break;
                }
                ArrayList<Person> temp = new ArrayList<>();
                if (maleLinkList.size() != 0) {
                    distUtil.selectiveDistributionByGender(index, groupArrayList, maleLinkList, loopCounter, num, temp);
                    maleLinkList.removeLast();

                } else if (femaleLinkList.size() != 0) {
                    distUtil.selectiveDistributionByGender(index, groupArrayList, femaleLinkList, loopCounter, num,
                            temp);
                    femaleLinkList.removeLast();
                }
                num++;
                loopCounter++;
            }
        }
        distUtil.distributeProcess(groupArrayList, groupName);
        groupArrayList.clear();
    }

    /**
     * This Method distribute all students into n number of groups.
     * Distribution will try to achieve multi-national students in a group.
     * Distribution is Random.
     */
    private void nationalityDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                         LinkedList<Person> personLinkedList, String groupName)
            throws CommandException {
        Map<Nationality, Integer> nationalityMap = distUtil.createNationalityMap(personLinkedList);
        int loopCounter = 0;
        for (Map.Entry<Nationality, Integer> entry : nationalityMap.entrySet()) {
            for (int x = 0; x < entry.getValue(); x++) {
                ArrayList<Person> personArrayList = new ArrayList<>();
                Person p = distUtil.findPerson(entry.getKey(), personLinkedList);
                distUtil.selectiveDistributionByNationality(index, groupArrayList, personArrayList, loopCounter, p);
                loopCounter++;
            }
        }
        distUtil.distributeProcess(groupArrayList, groupName);
        groupArrayList.clear();
    }

    /**
     * This Method distribute all students into n number of groups
     * Distribution will try to include balanced gender and include multi-national students.
     * To be completed in v2.0
     */
    private void strictDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> personLinkedList, String groupName)
            throws CommandException {
        logger.log(Level.WARNING, "Distribution by Nationality and Gender will be implemented in v2.0");
        throw new CommandException(MESSAGE_TO_BE_IMPLEMENTED);
    }

}
