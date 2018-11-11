package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
import seedu.address.model.matchSchedule.MatchSchedule;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;


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

    public static final String MESSAGE_SUCCESS = "Match found!";
    public static final String MESSAGE_FAILURE = "No Matches Found!";

    private MatchSchedule toSchedule;

    private List<Schedule> matchScheduleCompare;
    private List<Time> startTimeList;
    private List<Time> endTimeList;
    private int[] startEndTimeBlock;
    private List<String> availableSlots;

    private final List<Index> index;
    private final TheDate date;
    private final Time startTime;
    private final Time endTime;


    public MatchScheduleCommand(MatchSchedule matchSchedule, TheDate date, Time startTime, Time endTime,
                                List<Index> matchScheduleList) {
        requireNonNull(matchSchedule);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.index = matchScheduleList;
        this.toSchedule = matchSchedule;
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
                if (matchScheduleIter.getDate().equals(this.date)){
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
            for (int i = this.startTime.timeToMinutesInDay() ; i <= this.endTime.timeToMinutesInDay(); i++) {
                if (startEndTimeBlock[i] == 0 && inValidRange == 0){
                    String paddedHrs = String.format("%02d", i/60);
                    String paddedMins = String.format("%02d", i%60);
                    String toHrsStart = paddedHrs + paddedMins;

                    inValidRange = 1;
                }
                if (((startEndTimeBlock[i] == 1) || ( this.endTime.timeToMinutesInDay() == i))
                        && inValidRange == 1) {
                    String paddedHrs = String.format("%02d", i/60);
                    String paddedMins = String.format("%02d", i%60);
                    String toHrsEnd = paddedHrs + paddedMins + "\n";
                    inValidRange = 0;
                }
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS) + "\n");
// matchSchedule d/08112018 st/1230 et/1400 i/1 i/2
        //matchSchedule d/01012018 st/1000 et/1600 i/1
    }



}
