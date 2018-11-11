//package seedu.address.logic.commands;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.CommandHistory;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.person.Address;
//import seedu.address.model.person.Email;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.Person;
//import seedu.address.model.person.Phone;
//import seedu.address.model.person.Schedule;
//import seedu.address.model.tag.Tag;
//
//public class MatchSchedule extends Command {
//    public static final String COMMAND_WORD = "matchSchedule";
//    public static final String COMMAND_ALIAS = "ms";
//
//
//    public static final String COMMAND_PARAMETERS = "Parameters: "
//            + "[" + PREFIX_DATE + "DATE(DDMMYYYY) ] "
//            + "[" + PREFIX_START_TIME + "TIME(24HRS) ] "
//            + "[" + PREFIX_END_TIME + "TIME(24HRS) ] "
//            + "[" + PREFIX_INDEX + "INDEX ] ...";
//
//    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " "
//            + PREFIX_DATE + "08112018 "
//            + PREFIX_START_TIME + "0800 "
//            + PREFIX_END_TIME + "1700 "
//            + PREFIX_INDEX + "1 "
//            + PREFIX_INDEX + "2 "
//            + PREFIX_INDEX + "3 ";
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds available slots for persons selected \n"
//            + COMMAND_PARAMETERS
//            + COMMAND_EXAMPLE;
//
//    public static final String MESSAGE_SUCCESS = "Match found!";
//    public static final String MESSAGE_FAILURE = "No Matches Found!";
//
//    private Schedule toSchedule;
//
//    private final Index index;
//
//    public ScheduleCommand(Schedule schedule, Index index) {
//        requireNonNull(schedule);
//        this.index = index;
//        this.toSchedule = schedule;
//    }
//
//    @Override
//    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
//        requireNonNull(model);
//        List<Person> lastShownList = model.getFilteredPersonList();
//
//        if (index.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//
//        Person personToAddSchedule = lastShownList.get(index.getZeroBased());
//
//        model.updatePerson(personToAddSchedule, scheduledPerson);
//        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
//        model.commitAddressBook();
//        return new CommandResult(String.format(MESSAGE_SUCCESS, scheduledPerson));
//
//    }
//
//    /**
//     * Creates and returns a {@code Person} with the details of {@code personToEdit}
//     * edited with {@code editPersonDescriptor}.
//     */
////    private static Person addScheduleToPerson(Person personToAddSchedule, Schedule schedule) {
////        assert personToAddSchedule != null;
////
////        Name updatedName = personToAddSchedule.getName();
////        Phone updatedPhone = personToAddSchedule.getPhone();
////        Email updatedEmail = personToAddSchedule.getEmail();
////        Address updatedAddress = personToAddSchedule.getAddress();
////        Set<Tag> updatedTags = personToAddSchedule.getTags();
////        Set<Schedule> oldSchedule = personToAddSchedule.getSchedules();
////        Set<Schedule> updatedSchedule = new HashSet<>(oldSchedule);
////        updatedSchedule.add(schedule);
////        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedSchedule);
////    }
//
//}
