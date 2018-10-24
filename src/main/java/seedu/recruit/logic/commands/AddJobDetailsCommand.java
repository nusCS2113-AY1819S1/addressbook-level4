package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Intermediate Command of AddJobCommand
 * Adds a job offer to the CompanyBook with the relevant fields
 */

public class AddJobDetailsCommand extends Command {

    public static final String COMMAND_WORD = "AddJobDetails";

    public static final String MESSAGE_USAGE = "Enter the following details of the job in the format:\n"
            + PREFIX_COMPANY_NAME + "COMPANY "
            + PREFIX_JOB + "JOB_TITLE "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_AGE_RANGE + "AGE_RANGE "
            + PREFIX_EDUCATION + "EDUCATION "
            + PREFIX_SALARY + "SALARY\n"
            + "(Enter 'cancel' to stop adding jobs)\n"
            + "Example: "
            + PREFIX_COMPANY_NAME + "McDonalds "
            + PREFIX_JOB + "cashier "
            + PREFIX_GENDER + "M "
            + PREFIX_AGE_RANGE + "20-30 "
            + PREFIX_EDUCATION + "O levels "
            + PREFIX_SALARY + "1200\n";



    public static final String MESSAGE_SUCCESS = "New added job offer: %1$s";
    public static final String MESSAGE_DUPLICATE_JOB_OFFER = "This job offer already exists in the Company";
    public static final String MESSAGE_COMPANY_NOT_FOUND = "Company not found in CompanyBook.\n"
                                                          + "Please add the company to CompanyBook first";
    private final JobOffer toAdd;


    public AddJobDetailsCommand(JobOffer jobOffer) {
        requireNonNull(jobOffer);
        toAdd = jobOffer;
    };

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        int companyIndex = model.getCompanyIndexFromName(toAdd.getCompanyName());
        if (companyIndex == -1) {
            throw new CommandException(MESSAGE_COMPANY_NOT_FOUND);
        }
        if (model.getCompanyFromIndex(companyIndex).getUniqueJobList().contains(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB_OFFER);
        }
        model.addJobOffer(toAdd.getCompanyName(), toAdd);
        model.commitCompanyBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    };
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddJobDetailsCommand // instanceof handles nulls
                && toAdd.equals(((AddJobDetailsCommand) other).toAdd));
    }
}
