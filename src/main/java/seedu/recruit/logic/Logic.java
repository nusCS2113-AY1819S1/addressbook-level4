package seedu.recruit.logic;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javafx.collections.ObservableList;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText)
            throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of candidates */
    ObservableList<Candidate> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of companies */
    ObservableList<Company> getFilteredCompanyList();

    /** Returns an unmodifiable view of the filtered list of jobs offers */
    ObservableList<JobOffer> getFilteredCompanyJobList();

    /** Returns an unmodifiable view of the master list of candidates */
    ObservableList<Candidate> getMasterCandidateList();

    /** Returns an unmodifiable view of the master list of jobs offers */
    ObservableList<JobOffer> getMasterJobList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
