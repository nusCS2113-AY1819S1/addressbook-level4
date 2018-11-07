package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * Sorts all the candidates in the CandidateBook
 */
public class SortCandidateCommand extends Command {

    public static final String COMMAND_WORD = "sortc";

    public static final String MESSAGE_SUCCESS = "Sorted all candidates.\n";

    public static final String MESSAGE_TAG_USAGE = "Please sort by using one of the available tags: "
            + "Name " + PREFIX_NAME
            + ", Age " + PREFIX_AGE
            + ", Email " + PREFIX_EMAIL
            + ", Job " + PREFIX_JOB
            + ", Education " + PREFIX_EDUCATION
            + ", Salary " + PREFIX_SALARY
            + " or sort the current order in reverse with r/ \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME;

    private static Prefix prefixToSort;

    public SortCandidateCommand(Prefix prefix) {
        this.prefixToSort = prefix;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        model.sortCandidates(prefixToSort);
        model.commitCandidateBook();

        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            LogicManager.setLogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE);
            return new CommandResult(MESSAGE_SUCCESS
                    + SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST
                    + SelectCandidateCommand.MESSAGE_USAGE);
        }

        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
