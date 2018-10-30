package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.Model;

/**
 * Sorts all the companies in the CompanyBook
 */
public class SortCompanyCommand extends Command {

    public static final String COMMAND_WORD = "sortC";

    public static final String MESSAGE_SUCCESS = "Sorted all companies";

    public static final String MESSAGE_TAG_USAGE = "Please sort by using one of the available tags: "
            + "Company Name " + PREFIX_COMPANY_NAME
            + ", Email " + PREFIX_EMAIL
            + " or sort the current order in reverse with r/ \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COMPANY_NAME;

    private static Prefix prefixToSort;

    public SortCompanyCommand(Prefix prefix) {
        this.prefixToSort = prefix;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortCompanies(prefixToSort);
        model.commitCompanyBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
