package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.CheckedInTime;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Person;
import seedu.address.session.SessionManager;

//@@author pinjuen

/**
 * Check in or out to work.
 */
public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks in/out to/from work. "
        + "\nParameters: "
        + PREFIX_MODE + "MODE"
        + "\nExample: " + COMMAND_WORD + " "
        + PREFIX_MODE + "in ";
    public static final String MESSAGE_DUPLICATE = "User has already checked %1$s to work!";
    public static final String MESSAGE_NOT_LOGIN = "User has not logged in yet!";
    public static final String MESSAGE_CHECKED_IN = "Successfully checked in to work!\n"
        + "Date: %1$s Time: %2$s";
    public static final String MESSAGE_CHECKED_OUT = "Successfully checked out from work!\n"
        + "Date: %1$s Time: %2$s\n"
        + "Worked for: %3$.2f hours Salary per day: $%4$.2f";

    private double checkedInHour;
    private double currHour;
    private String currentTime = currentTime();
    private String messageSucess;
    private Mode mode;
    private CheckedInTime checkedInTime;
    private Person personLoggedIn;
    private double hoursWorked;
    private double salaryPerDay;

    public CheckCommand(Mode mode) {
        requireAllNonNull(mode);
        double currSecond;
        double currMinute;
        String[] currTimeArray = splitTime(currentTime);
        currSecond = Double.parseDouble(currTimeArray[2]);
        currMinute = Double.parseDouble(currTimeArray[1]);

        this.mode = mode;
        this.currHour = Double.parseDouble(currTimeArray[0]) + currMinute / 60 + currSecond / 3600;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        SessionManager sessionManager = SessionManager.getInstance(model);

        if (!sessionManager.isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        } else {
            personLoggedIn = sessionManager.getLoggedInPersonDetails();

            if (personLoggedIn.getMode().equals(mode)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE, mode));
            }

            if (mode.equals(new Mode("in"))) {
                checkedInTime = new CheckedInTime(currentTime);
                messageSucess = MESSAGE_CHECKED_IN;
            } else {
                hoursWorked = calculateHoursWorked(personLoggedIn.getCheckedInTime().toString());
                salaryPerDay = hoursWorked * (Double.parseDouble(personLoggedIn.getWorkingRate().toString()));
                checkedInTime = new CheckedInTime(currentTime);
                messageSucess = MESSAGE_CHECKED_OUT;
            }

            Person editedPerson = new Person(personLoggedIn.getName(), personLoggedIn.getNric(),
                personLoggedIn.getPassword(), personLoggedIn.getPhone(), personLoggedIn.getEmail(),
                personLoggedIn.getDepartment(), personLoggedIn.getPriorityLevel(), personLoggedIn.getAddress(),
                mode, personLoggedIn.getWorkingRate(), checkedInTime, personLoggedIn.getTags(),
                personLoggedIn.getSchedule());

            model.updatePerson(personLoggedIn, editedPerson);
            //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();

            return new CommandResult(String.format(messageSucess, currentDate(), currentTime,
                hoursWorked, salaryPerDay));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheckCommand)) {
            return false;
        }

        // state check
        CheckCommand e = (CheckCommand) other;
        return mode.equals(e.mode);
    }

    /**
     * Returns current date.
     */
    public static String currentDate() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dateNow = LocalDate.now();

        return date.format(dateNow);
    }

    /**
     * Returns current time.
     */
    public static String currentTime() {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime timeNow = LocalTime.now();

        return time.format(timeNow);
    }

    /**
     * Returns hours worked.
     */
    private double calculateHoursWorked(String checkedInTime) {
        String[] checkedInTimeArray = splitTime(checkedInTime);

        double checkedInSecond = Double.parseDouble(checkedInTimeArray[2]);
        double checkedInMinute = Double.parseDouble(checkedInTimeArray[1]);
        checkedInHour = Double.parseDouble(checkedInTimeArray[0]) + checkedInMinute / 60 + checkedInSecond / 3600;
        return currHour - checkedInHour;
    }

    /**
     * Returns array contains hours, minutes and seconds separately.
     */
    public static String[] splitTime(String time) {
        String[] timeTokens = time.split("\\:");
        String[] timeArray = new String[3];

        for (int i = 0; i < timeTokens.length; i++) {
            timeArray[i] = timeTokens[i];
        }

        return timeArray;
    }

}
