package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.joboffer.JobOffer;

public class AddJobCommand extends Command {
    public static final String COMMAND_WORD = "addj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a job offer to the RecruitBook. ";

    private final JobOffer toAdd;

    /**
     * Creates an AddJobCommand to add the specified {@code Candidate}
     */

    public AddJobCommand(JobOffer jobOffer){
        requireNonNull(jobOffer);
        toAdd = jobOffer;
    }

    public CommandResult execute(Model model, CommandHistory history) throws CommandException{
        requireNonNull(model);
        return new CommandResult("Add Job");
    };

}
