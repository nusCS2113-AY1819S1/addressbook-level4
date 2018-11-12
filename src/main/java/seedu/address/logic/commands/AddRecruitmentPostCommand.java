package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_EXPERIENCE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITMENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruitment.Recruitment;

/**
 * Adds a recruitment post on the address book.
 */
public class AddRecruitmentPostCommand extends Command {

    public static final String COMMAND_WORD = "addRecruitmentPost";
    public static final String COMMAND_ALIAS = "arp";

    public static final String MESSAGE_USAGE2 = COMMAND_WORD + ": Available Jobs. "
            + "\nParameters: "
            + PREFIX_JOB_POSITION + "[Job Position:] "
            + PREFIX_MINIMUM_EXPERIENCE + "[min working experience(Integer):] "
            + PREFIX_JOB_DESCRIPTION + "[Job Description:]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_JOB_POSITION
            + "IT Manager " + PREFIX_MINIMUM_EXPERIENCE + "3 "
            + PREFIX_JOB_DESCRIPTION + "To maintain the network server in company";


    public static final String MESSAGE_SUCCESS = "New recruitment post is added: %1$s";
    public static final String MESSAGE_DUPLICATE_POST = "This recruitment post already exists in the address book";

    private static boolean isPostDuplicated = false;
    private static boolean isWorkExpDuplicated = false;
    private static boolean isJobDescriptionDuplicated = false;
    private final Recruitment toAddRecruitment;

    /**
     * Creates an AddRecruitmentPostCommand to add the specified {@code Post}
     */
    public AddRecruitmentPostCommand(Recruitment recruitment) {
        requireAllNonNull(recruitment);
        this.toAddRecruitment = recruitment;
    }

    public static void setIsPostDuplicated(boolean verifyPostDuplication) {
        isPostDuplicated = verifyPostDuplication;
    }

    public static void setIsWorkExpDuplicated(boolean verifyWorkExpDuplication) {
        isWorkExpDuplicated = verifyWorkExpDuplication;
    }

    public static void setIsJobDescriptionDuplicated(boolean verifyJobDescriptionDuplication) {
        isJobDescriptionDuplicated = verifyJobDescriptionDuplication;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.hasRecruitment(toAddRecruitment)) {
            throw new CommandException(MESSAGE_DUPLICATE_POST);
        }

        model.addRecruitment(toAddRecruitment);
        model.commitRecruitmentPostList();
        model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        model.updateFilteredRecruitmentList(PREDICATE_SHOW_ALL_RECRUITMENT);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddRecruitment));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecruitmentPostCommand // instanceof handles nulls
                && toAddRecruitment.equals(((AddRecruitmentPostCommand) other).toAddRecruitment));
    }

}
