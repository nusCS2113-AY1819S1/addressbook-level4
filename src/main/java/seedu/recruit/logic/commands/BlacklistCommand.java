package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.tag.Tag;

/**
 * Blacklists a candidate in the CandidateBook.
 */
public class BlacklistCommand extends Command {

    public static final String COMMAND_WORD = "blacklist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Blacklists a candidate in the RecruitBook. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Blacklisted candidate: %1$s";
    public static final String MESSAGE_ALREADY_BLACKLISTED = "This candidate has already been blacklisted!";
    public static final String MESSAGE_WARNING_BLACKLISTED_PERSON = "The selected candidate has been blacklisted!\n"
            + "You can remove the blacklist with [ blacklist rm <INDEX>]";
    private final Index index;

    /**
     * @param index of the candidate in the filtered candidate list to blacklist
     */

    public BlacklistCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());

        List<Candidate> lastShownList = model.getFilteredCandidateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Candidate candidateToBlacklist = lastShownList.get(index.getZeroBased());
        Tag blacklistedTag = new Tag("BLACKLISTED");
        if (candidateToBlacklist.getTags().contains(blacklistedTag)) {
            throw new CommandException(MESSAGE_ALREADY_BLACKLISTED);
        }

        Candidate blacklistedCandidate = insertBlacklistTag(candidateToBlacklist);
        model.updateCandidate(candidateToBlacklist, blacklistedCandidate);
        model.commitCandidateBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, blacklistedCandidate));
    }

    /**
     * Returns the original candidate but the tags have been changed to "BLACKLISTED"
     */
    Candidate insertBlacklistTag(Candidate blacklistee) {
        assert blacklistee != null;

        Set<Tag> Tags = new HashSet<Tag>();
        Tag blacklistedTag = new Tag("BLACKLISTED");
        Tags.add(blacklistedTag);
        return new Candidate(blacklistee.getName(), blacklistee.getGender(), blacklistee.getAge(), blacklistee.getPhone(),
                blacklistee.getEmail(), blacklistee.getAddress(), blacklistee.getJob(), blacklistee.getEducation(),
                blacklistee.getSalary(), Tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BlacklistCommand // instanceof handles nulls
                && index.equals(((BlacklistCommand) other).index)); // state check
    }
}
