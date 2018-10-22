package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 *
 */
public class DistributeUtil {

    public static final String MESSAGE_DUPLICATE_GROUP = "There exist another group with the same name.";
    private static final String MESSAGE_MISSING_GROUP = "Group is not found.";
    private static final String MESSAGE_SHUFFLE_ERROR = "There is a problem shuffling the people in the address book.";
    private static final String GROUP_LOCATION = "UNKNOWN";
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * @param allPerson
     * @return
     */
    public LinkedList<Person> createNationalityList(LinkedList<Person> allPerson) {
        //number of different nationality
        Map<Nationality, Long> counts = numberOfDifferentNationality(allPerson);
        Map<Nationality, Long> sortedCount = paxPerNationality(counts);
        LinkedList<Person> nationalityLinkList = new LinkedList<>();

        System.out.println(counts.size());
        System.out.println(sortedCount);

        for (int i = 0; i < counts.size(); i++) {
            String[] parseValue = sortedCount.entrySet().toArray()[i].toString().split("=");
            int value = Integer.parseInt(parseValue[1]);
            System.out.print(sortedCount.keySet().toArray()[i] + " ");
            System.out.println(value);
        }
        return nationalityLinkList;
    }

    /**
     * This function shuffles all the person inside the LinkedList, with a specific seed.
     * @param person
     * @param seed
     * @return
     */
    public LinkedList<Person> shuffle(LinkedList<Person> person, Random seed) {
        requireNonNull(person);
        try {
            Collections.shuffle(person, seed);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException(MESSAGE_SHUFFLE_ERROR);
        }
        return person;
    }


    /**
     * This function takes in the list of all person and calculate the total number of different nationalities.
     * Returns a integer value which represent the number of different nationalities
     */
    private Map<Nationality, Long> numberOfDifferentNationality(LinkedList<Person> allPerson) {
        return allPerson.stream().collect(Collectors.groupingBy(e -> e.getNationality(), Collectors.counting()));
    }

    /**
     * This function will sort the map that holds the number of people with different nationalities.
     */
    private Map<Nationality, Long> paxPerNationality(Map<Nationality, Long> numberOfNationality) {
        return numberOfNationality.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new));
    }

    /**
     * This function conducts the checks to add Persons into the group.
     */
    public void genderDistributionCheck(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                         LinkedList<Person> genderLinkList,
                                         int loopCounter, int num, ArrayList<Person> temp) {
        if (loopCounter >= index) {
            temp = groupArrayList.get(num);
            temp.add(genderLinkList.getLast());
            groupArrayList.remove(num);
            groupArrayList.add(num, temp);
        } else {
            temp.add(genderLinkList.getLast());
            groupArrayList.add(num, temp);
        }
    }

    /**
     * This function runs through the allPerson list and add the specific gender required into an LinkedList
     * @param allPerson the list of allPerson in the addressbook
     */
    public void filterGender(LinkedList<Person> allPerson,
                             LinkedList<Person> filteredGender, String gender) {
        for (Person p : allPerson) {
            if (p.getGender().toString().equals(gender)) {
                filteredGender.add(p);
            }
        }
    }

    /**
     * This function concatenates the group index count behind the given group name.
     * Index shown to user will start from 1.
     */
    private String groupNameConcatenation (int index, String groupName) throws CommandException {
        index = index + 1;
        groupName = groupName + String.valueOf(index);
        if (existDuplicateGroup(groupName)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        return groupName;
    }

    /**
     * This function checks if there is any other groups that have the same name.
     * @param groupName check if groupName exist
     * @return false if there is no existing group.
     */
    private boolean existDuplicateGroup (String groupName) {
        ObservableList<Group> allGroups = model.getFilteredGroupList();
        requireNonNull(allGroups);
        for (Group gN : allGroups) {
            if (gN.getGroupName().toString().equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function will check all n number of groupName with the existing address book for existing groups.
     * @param index : number of groups the user desire.
     * @param groupName : the name of the group the user desire
     * @throws CommandException if there exist a duplicate groupName
     */
    public void doesGroupNameExist(int index, String groupName) throws CommandException {
        for (int i = index; i > 0; i--) {
            groupNameConcatenation(i, groupName);
        }
    }

    /**
     * This method will creates a new group with a given groupname.
     * @param toCreateGroupName the groupName that has been concatenated with index
     * @return returns the group object that has been created.
     */
    private Group groupBuilder(String toCreateGroupName) {
        GroupName parseGroupName = new GroupName(toCreateGroupName);
        GroupLocation parseGroupLocation = new GroupLocation(GROUP_LOCATION);
        Set<Tag> tags = new HashSet<>();
        return new Group(parseGroupName, parseGroupLocation, tags);
    }

    public void createGroup(Group group) throws CommandException {
        new CreateGroupCommand(group).execute(model, commandHistory);
    }

    private void addPersonIntoGroup(AddGroup addGroup) throws CommandException {
        new AddGroupCommand(addGroup).execute(model, commandHistory);
    }

    /**
     * This method returns the index of the particular group in the address book.
     * @param group The group to search for.
     * @return Return the Index value of the group.
     */
    private Index returnGroupIndex(Group group) {
        ObservableList<Group> allGroups = model.getFilteredGroupList();
        for (int i = 0; i < allGroups.size(); i++) {
            if (group.isSameGroup(allGroups.get(i))) {
                return Index.fromZeroBased(i);
            }
        }
        return Index.fromZeroBased(0);
    }


    /**
     * This Method Create the require group by the user and add the specific person into the group.
     * Works for multiple groups.
     * @param groupArrayList Total number of groups
     * @param groupName Groupname set by the User
     * @throws CommandException if Index of a group was return as 0.
     */
    public void distributeProcess(ArrayList<ArrayList<Person>> groupArrayList,
                                   String groupName) throws CommandException {
        ObservableList<Person> allPerson = model.getFilteredPersonList();
        for (int i = 0; i < groupArrayList.size(); i++) {
            String toCreateGroupName = groupNameConcatenation(i, groupName);
            Group newGroup = groupBuilder(toCreateGroupName);
            createGroup(newGroup);
            Index groupIndex = returnGroupIndex(newGroup);
            if (groupIndex.getOneBased() == 0) {
                throw new CommandException(MESSAGE_MISSING_GROUP);
            }
            System.out.println(groupIndex.getOneBased());
            System.out.println(toCreateGroupName);
            for (int j = 0; j < groupArrayList.get(i).size(); j++) {
                System.out.println(groupArrayList.get(i).get(j));
                for (int k = 0; k < allPerson.size(); k++) {
                    if (allPerson.get(k).equals(groupArrayList.get(i).get(j))) {
                        Set<Index> personIndices = new HashSet<>();
                        personIndices.add(Index.fromZeroBased(k));
                        AddGroup addSinglePersonIntoGroup = new AddGroup(groupIndex, personIndices);
                        addPersonIntoGroup(addSinglePersonIntoGroup);
                    }
                }
            }
        }
    }

}
