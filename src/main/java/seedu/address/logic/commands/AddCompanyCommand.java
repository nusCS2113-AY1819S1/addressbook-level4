package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.Company;

/**
 * Adds a company to the CompanyBook.
 */

public class AddCompanyCommand  extends Command {

    public static final String COMMAND_WORD = "addC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a company to the RecruitBook. "
            + "Parameters: "
            + PREFIX_COMPANY_NAME + "COMPANY NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_PHONE + "PHONE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COMPANY_NAME + "KFC "
            + PREFIX_ADDRESS + "101, Clementi Ave 2, #01-01 "
            + PREFIX_EMAIL + "kfc@gmail.com "
            + PREFIX_PHONE + "61231232 ";

    public static final String MESSAGE_SUCCESS = "New added: %1$s";
    public static final String MESSAGE_DUPLICATE_COMPANY = "This company already exists in the CompanyBook";

    private final Company toAdd;

    /**
     * Creates an AddCompanyCommand to add the specified {@code Company}
     */

    public AddCompanyCommand(Company company) {
        requireNonNull(company);
        toAdd = company;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCompany(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPANY);
        }

        model.addCompany(toAdd);
        model.commitCompanyBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCompanyCommand // instanceof handles nulls
                && toAdd.equals(((AddCompanyCommand) other).toAdd));
    }

}
