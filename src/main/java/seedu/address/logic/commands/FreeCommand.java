package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.enrolledModule.EnrolledModule;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREE;


/**
 * Merges the timetables of multiple people
 */

public class FreeCommand extends Command {

    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the next available timeslot for persons "
            + "listed by their index number.\n"
            + "Parameters: INDEX (must be positive integer )"
            + PREFIX_FREE + "[INDEX]"
            + "for all contacts you want to compare.\n"
            + "Example: " + COMMAND_WORD + " " +  PREFIX_FREE + "1 " + PREFIX_FREE + "2 ";

    public static final String MESSAGE_FREE_TIMETABLE_SUCCESS = "Found free Slot";
    public static final String MESSAGE_NOT_FREED = "Unable to find free slot";

    private final List<String> indices;
    private final String[] days = {"mon", "tue", "wed", "thu", "fri"};

    public FreeCommand(List<String> indices) {
        requireNonNull(indices);

        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        String outputToUser = "The next available timeslot for";
        List<Person> lastShownList = model.getFilteredPersonList();
        lastShownList = ((ObservableList<Person>) lastShownList).filtered(new IsNotSelfOrMergedPredicate());

        for (String index : indices) {
            if (Integer.parseInt(index) >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        Person personFirst = lastShownList.get(Integer.parseInt(indices.get(0)) - 1);

        outputToUser += " " + personFirst.getName() + ",";



        //remove the first person from the list as we have reference to him in personFirst
        indices.remove(0);

        //if trying to find free slots for more than 1 person, use the merge algorithm to create a merged timetable to
        //find a common free slot(s)
        if (indices.size() > 0) {
            for (String x : indices) {
                personFirst = mergeTimetables(personFirst, lastShownList.get(Integer.parseInt(x) - 1));
                outputToUser += " " + lastShownList.get(Integer.parseInt(x) - 1).getName() + ",";
            }
        } else {
            //only finding 1 person free slot, so use the merge algorithm to compare both the same person
            //to change the timeslots to free or busy tag
            personFirst = mergeTimetables(personFirst, personFirst);
        }

        outputToUser = outputToUser.substring(0,outputToUser.length()-1);
        outputToUser = outputToUser + " is : ";

        int dayToCheck;
        boolean isToday = true;


        //if today is saturday or sunday, loop back to monday
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY || LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
            dayToCheck = 0;
            isToday = false;
        }else if (LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY) {
            dayToCheck = 0;
        } else if (LocalDate.now().getDayOfWeek() == DayOfWeek.TUESDAY) {
            dayToCheck = 1;
        } else if (LocalDate.now().getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            dayToCheck = 2;
        } else if (LocalDate.now().getDayOfWeek() == DayOfWeek.THURSDAY) {
            dayToCheck = 3;
        } else {
            dayToCheck = 4;
        }


        Calendar rightNow = Calendar.getInstance();
        int hourNow = rightNow.get(Calendar.HOUR_OF_DAY);
        if (hourNow >= 20) {
            //after 8pm, loop to next day
            dayToCheck++;
            dayToCheck %= 5;
            hourNow = 8;
            isToday = false;
        }

        List<Integer> listFoundSlot = new ArrayList<>();

        //loop for 6 days, the 6th day is to look for time that is on this day but hours before current hour
        for (int i = 0; i < 6; i++) {

            int timeSlotIndex = 0;
            int currentHourIndex = hourNow - 8;
            listFoundSlot.clear();
            boolean found = false;
            int prevIndex = -1;
            List<TimeSlots> timeslotToCheck = personFirst.getTimeSlots().get(days[dayToCheck]);

            for (TimeSlots x : timeslotToCheck) {
                if (x.toString().equalsIgnoreCase("free")) {

                    //don't add hours that are before current time if it is today
                    if ((isToday && (!(timeSlotIndex < currentHourIndex))) || (!isToday)) {

                        //finding consecutive slots
                        if (found == false) {
                            found = true;
                            prevIndex = timeSlotIndex;
                        } else {
                            if (timeSlotIndex - prevIndex != 1) {
                                break;
                            } else {
                                prevIndex = timeSlotIndex;
                            }
                        }
                        listFoundSlot.add(timeSlotIndex);

                    }

                }
                timeSlotIndex++;
            }



            String timeFrom;
            String timeTo;
            DateFormat sdf = new SimpleDateFormat("EEE hh:mm aa");
            if (listFoundSlot.size() > 0) {
                //found a free slot
                int foundHour = listFoundSlot.get(0) + 8;
                int endHour = (listFoundSlot.get(listFoundSlot.size()-1) + 9);

                if (!(isToday && (foundHour == hourNow))) {
                    isToday = false;
                }
                if (isToday) {
                    timeFrom = sdf.format(rightNow.getTime());
                    timeTo = getTimeFormatted(endHour);
                    outputToUser += timeFrom + " - " + timeTo;
                    return new CommandResult(outputToUser);
                } else {
                    timeFrom = getTimeFormatted(foundHour);
                    timeTo = getTimeFormatted(endHour);
                    outputToUser += days[dayToCheck] + " " + timeFrom + " - " + timeTo;
            }
                return new CommandResult(outputToUser);
            }


            dayToCheck++;
            dayToCheck %= 5;
            hourNow = 8;
            isToday = false;
        }



        return new CommandResult(MESSAGE_NOT_FREED);

    }

    private String getTimeFormatted(int hours) {
        String amPm = "AM";
        if (hours > 12) {
            hours %= 12;
            amPm = "PM";
        } else if (hours == 12) {
            amPm = "PM";
        }
        return hours + ":00 " + amPm;
    }


    private Person mergeTimetables(Person person1, Person person2) {
        Name mergedName = new Name("temp");
        Phone phone = new Phone("99999999");
        Email email = new Email("notimportant@no");
        Address address = new Address("123");

        Set<Tag> mergedTags = new HashSet<>();
        mergedTags.add(new Tag("merged"));
        Map<String, List<TimeSlots>> mergedSlots = mergeTimeSlots(person1.getTimeSlots(), person2.getTimeSlots());
        Map<String, EnrolledModule> enrolledClassMap = new TreeMap<>();


        return new Person(mergedName, phone, email, address, mergedTags, enrolledClassMap,
                mergedSlots);


    }

    private Map<String, List<TimeSlots>> mergeTimeSlots(Map<String, List<TimeSlots>> slots1,
                                                        Map<String, List<TimeSlots>> slots2) {
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


        for (int i = 0; i < 12; i++) {
            if (mon1[i].toString().equalsIgnoreCase("free")) {
                if (mon2[i].toString().charAt(5) == 'm' || mon2[i].toString().charAt(5) == 'a') {
                    finalMon.add(new TimeSlots("free"));
                }
            } else if (mon1[i].toString().equalsIgnoreCase("busy")) {
                finalMon.add(new TimeSlots("busy"));
            } else if ((mon1[i].toString().charAt(5) == 'm' || mon1[i].toString().charAt(5) == 'a')
                    && (mon2[i].toString().charAt(5) == 'm' || mon2[i].toString().charAt(5) == 'a')) {
                finalMon.add(new TimeSlots("free"));
            } else {
                finalMon.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i < 12; i++) {
            if (tue1[i].toString().equalsIgnoreCase("free")) {
                if (tue2[i].toString().charAt(5) == 'm' || tue2[i].toString().charAt(5) == 'a') {
                    finalTue.add(new TimeSlots("free"));
                }
            } else if (tue1[i].toString().equalsIgnoreCase("busy")) {
                finalTue.add(new TimeSlots("busy"));
            } else if ((tue1[i].toString().charAt(5) == 'm' || tue1[i].toString().charAt(5) == 'a'
                    || tue1[i].toString().equalsIgnoreCase("free"))
                    && (tue2[i].toString().charAt(5) == 'm' || tue2[i].toString().charAt(5) == 'a'
                    || tue2[i].toString().equalsIgnoreCase("free"))) {
                finalTue.add(new TimeSlots("free"));
            } else {
                finalTue.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i < 12; i++) {
            if (wed1[i].toString().equalsIgnoreCase("free")) {
                if (wed2[i].toString().charAt(5) == 'm' || wed2[i].toString().charAt(5) == 'a') {
                    finalWed.add(new TimeSlots("free"));
                }
            } else if (wed1[i].toString().equalsIgnoreCase("busy")) {
                finalWed.add(new TimeSlots("busy"));
            } else if ((wed1[i].toString().charAt(5) == 'm' || wed1[i].toString().charAt(5) == 'a'
                    || wed1[i].toString().equalsIgnoreCase("free"))
                    && (wed2[i].toString().charAt(5) == 'm' || wed2[i].toString().charAt(5) == 'a'
                    || wed2[i].toString().equalsIgnoreCase("free"))) {
                finalWed.add(new TimeSlots("free"));
            } else {
                finalWed.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i < 12; i++) {
            if (thu1[i].toString().equalsIgnoreCase("free")) {
                if (thu2[i].toString().charAt(5) == 'm' || thu2[i].toString().charAt(5) == 'a') {
                    finalThu.add(new TimeSlots("free"));
                }
            } else if (thu1[i].toString().equalsIgnoreCase("busy")) {
                finalThu.add(new TimeSlots("busy"));
            } else if ((thu1[i].toString().charAt(5) == 'm' || thu1[i].toString().charAt(5) == 'a'
                    || thu1[i].toString().equalsIgnoreCase("free"))
                    && (thu2[i].toString().charAt(5) == 'm' || thu2[i].toString().charAt(5) == 'a'
                    || thu2[i].toString().equalsIgnoreCase("free"))) {
                finalThu.add(new TimeSlots("free"));
            } else {
                finalThu.add(new TimeSlots("busy"));
            }
        }
        for (int i = 0; i < 12; i++) {
            if (fri1[i].toString().equalsIgnoreCase("free")) {
                if (fri2[i].toString().charAt(5) == 'm' || fri2[i].toString().charAt(5) == 'a') {
                    finalFri.add(new TimeSlots("free"));
                }
            } else if (fri1[i].toString().equalsIgnoreCase("busy")) {
                finalFri.add(new TimeSlots("busy"));
            } else if ((fri1[i].toString().charAt(5) == 'm' || fri1[i].toString().charAt(5) == 'a'
                    || fri1[i].toString().equalsIgnoreCase("free"))
                    && (mon2[i].toString().charAt(5) == 'm' || mon2[i].toString().charAt(5) == 'a'
                    || fri2[i].toString().equalsIgnoreCase("free"))) {
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



