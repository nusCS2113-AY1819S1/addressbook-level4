package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;

//@Author: driedmelon

/**
 * Checks the unavailable timeslots of the selected persons and prints out the common available times.
 */

public class MatchScheduleCommand extends Command {
    public static final String COMMAND_WORD = "matchSchedule";
    public static final String COMMAND_ALIAS = "ms";


    public static final String COMMAND_PARAMETERS = "Parameters: "
            + "[" + PREFIX_DATE + "DATE(DDMMYYYY) ] "
            + "[" + PREFIX_START_TIME + "TIME(24HRS) ] "
            + "[" + PREFIX_END_TIME + "TIME(24HRS) ] "
            + "[" + PREFIX_INDEX + "INDEX ] ...\n";

    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "08112018 "
            + PREFIX_START_TIME + "0800 "
            + PREFIX_END_TIME + "1700 "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "2 "
            + PREFIX_INDEX + "3 ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds available slots for persons selected \n"
            + COMMAND_PARAMETERS
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Common time slots found!\n";
    public static final String MESSAGE_FAILURE = "No common time slots Found!";

    private List<Schedule> matchScheduleCompare;
    private List<Time> startTimeList;
    private List<Time> endTimeList;
    private int[] startEndTimeBlock;
    private List<String> availableSlots;
    private String slots;

    private final List<Index> index;
    private final TheDate date;
    private final Time startTime;
    private final Time endTime;


    public MatchScheduleCommand(TheDate date, Time startTime, Time endTime,
                                List<Index> matchScheduleList) {
        requireAllNonNull(date, startTime, endTime, matchScheduleList);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.index = matchScheduleList;
        this.matchScheduleCompare = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index indexIter : this.index) {
            if (indexIter.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        this.startTimeList = new ArrayList<>();
        this.endTimeList = new ArrayList<>();
        this.startEndTimeBlock = new int[1440];

        //to bring up schedules
        for (Index indexIter : this.index) {
            Person personToMatchSchedule = lastShownList.get(indexIter.getZeroBased());
            Set<Schedule> temp = personToMatchSchedule.getSchedules();
            matchScheduleCompare.addAll(temp);
        }

        //to store start and end time as Time type
        for (Schedule matchScheduleIter : this.matchScheduleCompare) {
            if (matchScheduleIter.getDate().equals(this.date)) {
                this.startTimeList.add(matchScheduleIter.getStartTime());
                this.endTimeList.add(matchScheduleIter.getEndTime());
            }
        }

        //to store busy time slots in array and sets available slots
        for (int i = 0; i < this.startTimeList.size(); i++) {
            for (int j = this.startTimeList.get(i).timeToMinutesInDay();
                 j <= this.endTimeList.get(i).timeToMinutesInDay(); j++) {
                this.startEndTimeBlock[j] = 1;
            }
        }

        int inValidRange = 0;
        int validRangePresent = 0;
        this.availableSlots = new ArrayList<>();

        for (int i = this.startTime.timeToMinutesInDay(); i <= this.endTime.timeToMinutesInDay(); i++) {
            if (startEndTimeBlock[i] == 0 && inValidRange == 0) {
                String paddedHrs = String.format("%02d", i / 60);
                String paddedMins = String.format("%02d", i % 60);
                String toHrsStart = paddedHrs + paddedMins;
                this.availableSlots.add(toHrsStart);
                inValidRange = 1;
                validRangePresent = 1;
            }
            if (((startEndTimeBlock[i] == 1) || (this.endTime.timeToMinutesInDay() == i))
                    && inValidRange == 1) {
                String paddedHrs = String.format("%02d", i / 60);
                String paddedMins = String.format("%02d", i % 60);
                String toHrsEnd = paddedHrs + paddedMins;
                this.availableSlots.add(toHrsEnd);
                inValidRange = 0;
            }
        }

        slots = "";
        //format string slots to print
        for (int i = 0; i < this.availableSlots.size(); i++) {
            if (i % 2 == 0) {
                slots = slots + ("Start:" + this.availableSlots.get(i) + " ");
            } else {
                slots = slots + ("End:" + this.availableSlots.get(i) + "\n");
            }
        }

        if (validRangePresent == 1) {
            return new CommandResult(MESSAGE_SUCCESS + slots);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchScheduleCommand // instanceof handles nulls
                && index.equals(((MatchScheduleCommand) other).index)); // state check
    }

}
