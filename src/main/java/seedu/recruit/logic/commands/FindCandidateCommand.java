package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.AddressContainsKeywordsPredicate;
import seedu.recruit.model.candidate.EmailContainsKeywordsPredicate;
import seedu.recruit.model.candidate.NameContainsKeywordsPredicate;
import seedu.recruit.model.candidate.PhoneContainsKeywordsPredicate;


/**
 * Finds and lists all persons in recruit book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCandidateCommand extends Command {

    public static final String COMMAND_WORD = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " alice bob charlie";

    private final AddressContainsKeywordsPredicate addressPredicate;
    private final EmailContainsKeywordsPredicate emailPredicate;
    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;


    public FindCandidateCommand(NameContainsKeywordsPredicate namePredicate,
                                AddressContainsKeywordsPredicate addressPredicate,
                                EmailContainsKeywordsPredicate emailPredicate,
                                PhoneContainsKeywordsPredicate phonePredicate) {
        this.namePredicate = namePredicate;
        this.addressPredicate = addressPredicate;
        this.emailPredicate = emailPredicate;
        this.phonePredicate = phonePredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCandidateList(namePredicate);
        model.updateFilteredCandidateList(addressPredicate);
        model.updateFilteredCandidateList(emailPredicate);
        model.updateFilteredCandidateList(phonePredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredCandidateList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCandidateCommand // instanceof handles nulls
                && namePredicate.equals(((FindCandidateCommand) other).namePredicate)) // state check
                || (other instanceof FindCandidateCommand
                && addressPredicate.equals(((FindCandidateCommand) other).addressPredicate))
                || (other instanceof FindCandidateCommand
                && emailPredicate.equals(((FindCandidateCommand) other).emailPredicate))
                || (other instanceof FindCandidateCommand
                && phonePredicate.equals(((FindCandidateCommand) other).phonePredicate));
    }
}
