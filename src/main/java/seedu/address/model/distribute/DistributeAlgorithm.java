package seedu.address.model.distribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.Gender.VALID_GENDER_FEMALE;
import static seedu.address.model.person.Gender.VALID_GENDER_MALE;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javafx.collections.ObservableList;
import seedu.address.commons.util.DistributeUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
    public static final String MESSAGE_FLAG_ERROR = "Gender and Nationality flags only accept "
            + "'1' or '0' or \"true\" or \"false\"";

    private DistributeUtil distUtil = new DistributeUtil();

    public DistributeAlgorithm(Model model, Distribute dist) throws CommandException {
        requireNonNull(dist);
        distUtil.setModel(model);

        int numOfGroups = dist.getIndex();
        String groupName = dist.getGroupName().toString();
        boolean genderFlag = dist.getGender();
        boolean nationalityFlag = dist.getNationality();
        ArrayList<ArrayList<Person>> groupArrayList = new ArrayList<>();

        // Get all person data via ObservableList
        ObservableList<Person> allPerson = model.getFilteredPersonList();
        requireNonNull(allPerson);

        //Checks if numOfGroups intended to be created is > number of people
        if (allPerson.size() < numOfGroups) {
            throw new CommandException(MESSAGE_INVALID_SIZE);
        }
        distUtil.doesGroupNameExist(numOfGroups, groupName);

        //Convert ObservableList into ArrayList to use Randomizer via Collections
        LinkedList<Person> randomAllPersonArrayList = new LinkedList<>(allPerson);

        //Randomizer function
        Instant instant = Instant.now();
        distUtil.shuffle(randomAllPersonArrayList, new Random(instant.getEpochSecond()));

        if (!genderFlag && !nationalityFlag) {
            normalDistribution(numOfGroups, groupArrayList, randomAllPersonArrayList, groupName);
        } else if (!genderFlag && nationalityFlag) {
            nationalityDistribution(numOfGroups, groupArrayList, randomAllPersonArrayList, groupName);
        } else if (genderFlag && !nationalityFlag) {
            genderDistribution(numOfGroups, groupArrayList, randomAllPersonArrayList, groupName);
        } else {
            strictDistribution(numOfGroups, groupArrayList, randomAllPersonArrayList, groupName);
        }
    }

    /**
     * This Method distribute all students into n number of groups
     * Distribution is random.
     */
    private void normalDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> randomAllPersonArrayList,
                                    String groupName) throws CommandException {
        for (int i = index; i > 0; i--) { //number of groups to add into the groupArrayList
            ArrayList<Person> addPerson = new ArrayList<>();
            int paxInAGroup = randomAllPersonArrayList.size() / i;
            while (paxInAGroup > 0) {
                addPerson.add(randomAllPersonArrayList.getLast());
                randomAllPersonArrayList.removeLast();
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
                                    LinkedList<Person> randomAllPersonArrayList, String groupName)
            throws CommandException {
        LinkedList<Person> maleLinkList = new LinkedList<>();
        LinkedList<Person> femaleLinkList = new LinkedList<>();
        int loopCounter = 0;
        int num = 0;

        distUtil.filterGender(randomAllPersonArrayList, maleLinkList, VALID_GENDER_MALE);
        distUtil.filterGender(randomAllPersonArrayList, femaleLinkList, VALID_GENDER_FEMALE);

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
                    distUtil.genderDistributionCheck(index, groupArrayList, maleLinkList, loopCounter, num, temp);
                    maleLinkList.removeLast();

                } else if (femaleLinkList.size() != 0) {
                    distUtil.genderDistributionCheck(index, groupArrayList, femaleLinkList, loopCounter, num, temp);
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
                                         LinkedList<Person> randomAllPersonArrayList, String groupName)
            throws CommandException {
        System.out.println("Nationality Distribution");

    }

    /**
     * This Method distribute all students into n number of groups
     * Distribution will try to include balanced gender and include multi-national students.
     */
    private void strictDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> randomAllPersonArrayList, String groupName)
            throws CommandException {
        System.out.println("Gender & Nationality Distribution");
    }

}
