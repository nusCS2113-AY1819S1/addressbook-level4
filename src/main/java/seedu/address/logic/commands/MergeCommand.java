package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MERGE;

import java.util.*;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.enrolledClass.EnrolledClass;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.person.TimeSlots;


/**
 * Merges the timetables of multiple people
 */

public class MergeCommand extends Command {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Merges the timetables of selected people"
            + "by the index number used in the last person listing.\n "
            + "Parameters: INDEX (must be positive integer )"
            + PREFIX_MERGE + "[INDEX]"
            + "for all timetables you want to merge.\n"
            + "Example: " + "COMMAND_WORD" + "1" + "COMMAND_WORD" + "2";

    public static final String MESSAGE_MERGE_TIMETABLE_SUCCESS = "Timetables Merged";
    public static final String MESSAGE_NOT_MERGED = "At least two people to merge must be provided";

    private final List<String> indices;

    public MergeCommand(List<String> indices) {
        requireNonNull(indices);

        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();


        Person[] personsToMerge = new Person[lastShownList.size()];


        for (String index : indices) {
            if (Integer.parseInt(index) >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        int i = 0;
        for (String it : indices) {
            int index;
            try {
                index = Integer.parseInt(it) - 1;
            } catch (NumberFormatException nfe) {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MergeCommand.MESSAGE_USAGE), nfe);
            }
            personsToMerge[i] = lastShownList.get(index);
            i++;
        }
        for (int j = 0; j < i - 1; j++) {
            personsToMerge[j + 1] = mergeTimetables(personsToMerge[j], personsToMerge[j + 1]);
        }
        model.addPerson(personsToMerge[i - 1]);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_MERGE_TIMETABLE_SUCCESS);

    }


    private Person mergeTimetables(Person person1, Person person2) {
        Name mergedName = new Name(person1.getName().toString() + " and " + person2.getName().toString());
        Phone phone = new Phone("999");
        Email email = new Email("notimportant@no");
        Address address = new Address("here");
        Set<Tag> mergedTags= new HashSet<>();
        Map<String, List<TimeSlots>> mergedSlots = mergeTimeSlots(person1.getTimeSlots(), person2.getTimeSlots());
        Map<String, EnrolledClass> enrolledClassMap = new TreeMap<>();


        return new Person(mergedName, phone, email, address, mergedTags, enrolledClassMap,
                mergedSlots);


    }

    private Map<String, List<TimeSlots>> mergeTimeSlots(Map<String,List<TimeSlots>>slots1,
                                                  Map<String,List<TimeSlots>>slots2) {
        TimeSlots[] mon1 = slots1.get("mon").toArray(new TimeSlots[0]);
        TimeSlots[] mon2 = slots2.get("mon").toArray(new TimeSlots[0]);
        TimeSlots[] tue1 = slots1.get("tue").toArray(new TimeSlots[0]);
        TimeSlots[] tue2 = slots2.get("tue").toArray(new TimeSlots[0]);
        TimeSlots[] wed1 = slots1.get("wed").toArray(new TimeSlots[0]);
        TimeSlots[] wed2 = slots2.get("wed").toArray(new TimeSlots[0]);
        TimeSlots[] thu1 = slots1.get("thu").toArray(new TimeSlots[0]);
        TimeSlots[] thu2 = slots2.get("thu").toArray(new TimeSlots[0]);
        TimeSlots[] fri1 = slots1.get("fri").toArray(new TimeSlots[0]);
        TimeSlots[] fri2 = slots2.get("fri").toArray(new TimeSlots[0]);
        List<TimeSlots> finalMon = new ArrayList<>();
        List<TimeSlots> finalTue = new ArrayList<>();
        List<TimeSlots> finalWed = new ArrayList<>();
        List<TimeSlots> finalThu = new ArrayList<>();
        List<TimeSlots> finalFri = new ArrayList<>();
        Map<String, List<TimeSlots>> finalSlots = new HashMap<>();

        for (int i = 0; i <12; i++) {
            if(mon1[i].toString().equalsIgnoreCase("free")){
                if(mon2[i].toString().charAt(5) == 'm' || mon2[i].toString().charAt(5) == 'a'){
                    finalMon.add(new TimeSlots("free"));
                }
            }
            else if(mon1[i].toString().equalsIgnoreCase("busy")){
                finalMon.add(new TimeSlots("busy"));
            }
            else if ((mon1[i].toString().charAt(5) == 'm' || mon1[i].toString().charAt(5) == 'a')
                    && (mon2[i].toString().charAt(5) == 'm' || mon2[i].toString().charAt(5) == 'a')) {
                finalMon.add(new TimeSlots("free"));
            } else {
                finalMon.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i <12; i++) {
            if(tue1[i].toString().equalsIgnoreCase("free")){
                if(tue2[i].toString().charAt(5) == 'm' || tue2[i].toString().charAt(5) == 'a'){
                    finalTue.add(new TimeSlots("free"));
                }
            }
            else if(tue1[i].toString().equalsIgnoreCase("busy")){
                finalTue.add(new TimeSlots("busy"));
            }
            else if ((tue1[i].toString().charAt(5) == 'm' || tue1[i].toString().charAt(5) == 'a'
                    ||tue1[i].toString().equalsIgnoreCase("free"))
                    && (tue2[i].toString().charAt(5) == 'm' || tue2[i].toString().charAt(5) == 'a'
                    ||tue2[i].toString().equalsIgnoreCase("free"))) {
                finalTue.add(new TimeSlots("free"));
            } else {
                finalTue.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i <12; i++) {
            if(wed1[i].toString().equalsIgnoreCase("free")){
                if(wed2[i].toString().charAt(5) == 'm' || wed2[i].toString().charAt(5) == 'a'){
                    finalWed.add(new TimeSlots("free"));
                }
            }
            else if(wed1[i].toString().equalsIgnoreCase("busy")){
                finalWed.add(new TimeSlots("busy"));
            }
            else if ((wed1[i].toString().charAt(5) == 'm' || wed1[i].toString().charAt(5) == 'a'
                    ||wed1[i].toString().equalsIgnoreCase("free"))
                    && (wed2[i].toString().charAt(5) == 'm' || wed2[i].toString().charAt(5) == 'a'
                    ||wed2[i].toString().equalsIgnoreCase("free"))) {
                finalWed.add(new TimeSlots("free"));
            } else {
                finalWed.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i <12; i++) {
            if(thu1[i].toString().equalsIgnoreCase("free")){
                if(thu2[i].toString().charAt(5) == 'm' || thu2[i].toString().charAt(5) == 'a'){
                    finalThu.add(new TimeSlots("free"));
                }
            }
            else if(thu1[i].toString().equalsIgnoreCase("busy")){
                finalThu.add(new TimeSlots("busy"));
            }

            else if ((thu1[i].toString().charAt(5) == 'm' || thu1[i].toString().charAt(5) == 'a'
                    ||thu1[i].toString().equalsIgnoreCase("free"))
                    && (thu2[i].toString().charAt(5) == 'm' || thu2[i].toString().charAt(5) == 'a'
                    || thu2[i].toString().equalsIgnoreCase("free"))) {
                finalThu.add(new TimeSlots("free"));
            } else {
                finalThu.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i <12; i++) {
            if(fri1[i].toString().equalsIgnoreCase("free")){
                if(fri2[i].toString().charAt(5) == 'm' || fri2[i].toString().charAt(5) == 'a'){
                    finalFri.add(new TimeSlots("free"));
                }
            }
            else if(fri1[i].toString().equalsIgnoreCase("busy")){
                finalFri.add(new TimeSlots("busy"));
            }
            else if ((fri1[i].toString().charAt(5) == 'm' || fri1[i].toString().charAt(5) == 'a'
                    ||fri1[i].toString().equalsIgnoreCase("free"))
                    && (mon2[i].toString().charAt(5) == 'm' || mon2[i].toString().charAt(5) == 'a'
                    ||fri2[i].toString().equalsIgnoreCase("free"))) {
                finalFri.add(new TimeSlots("free"));
            } else {
                finalFri.add(new TimeSlots("busy"));
            }
        }
        finalSlots.put("mon", finalMon);
        finalSlots.put("tue", finalTue);
        finalSlots.put("wed", finalWed);
        finalSlots.put("thu", finalThu);
        finalSlots.put("fri", finalFri);
        return finalSlots;
    }

}



