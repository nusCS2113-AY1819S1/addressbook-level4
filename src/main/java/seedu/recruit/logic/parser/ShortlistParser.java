package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.FilterCandidateCommand;
import seedu.recruit.logic.commands.FilterCompanyCommand;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.FindJobOfferCommand;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.ShortlistCandidateCommand;
import seedu.recruit.logic.commands.ShortlistCandidateInitializationCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShortlistCandidateInitializationCommand object
 */
public class ShortlistParser {

    /**
     * Constructor to parse commands if the logic state is within shortlist process
     * @param commandWord name of the command taken from RecruitBookParser
     * @param arguments arguments of the command taken from RecruitBookParser
     * @param state logic state
     * @return the shortlist command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String commandWord, String arguments, LogicState state)
            throws ParseException {
        if (state.nextCommand.equals(ShortlistCandidateCommand.COMMAND_LOGIC_STATE)) {
            switch (commandWord) {
            case ShortlistCandidateCommand.COMMAND_WORD:
                // prevents invalid arguments as confirm should not be accompanied with further arguments
                if (!arguments.isEmpty()) {
                    throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                            + ShortlistCandidateCommand.MESSAGE_USAGE);
                }
                return new ShortlistCandidateCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + ShortlistCandidateCommand.MESSAGE_USAGE);
            }

        } else if (state.nextCommand.equals(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST)) {
            switch (commandWord) {

            case FindCompanyCommand.COMMAND_WORD:
                return new FindCompanyCommandParser().parse(arguments);

            case FilterCompanyCommand.COMMAND_WORD:
                return new FilterCompanyCommandParser().parse(arguments);

            case SelectCompanyCommand.COMMAND_WORD:
                Index index = ParserUtil.parseIndex(arguments);
                return new SelectCompanyCommand(index);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + ShortlistCandidateInitializationCommand.MESSAGE_NEXT_STEP
                        + SelectCompanyCommand.MESSAGE_USAGE);
            }

        } else if (state.nextCommand.equals(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST)) {
            switch (commandWord) {

            case SelectJobCommand.COMMAND_WORD:
                Index index = ParserUtil.parseIndex(arguments);
                return new SelectJobCommand(index);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                        + SelectJobCommand.MESSAGE_USAGE);
            }

        } else if (state.nextCommand.equals(SelectCandidateCommand.COMMAND_LOGIC_STATE)) {
            switch (commandWord) {

            case FindCandidateCommand.COMMAND_WORD:
                return new FindCandidateCommandParser().parse(arguments);

            case FilterCandidateCommand.COMMAND_WORD:
                return new FilterCandidateCommandParser().parse(arguments);

            case SelectCandidateCommand.COMMAND_WORD:
                Index index = ParserUtil.parseIndex(arguments);
                return new SelectCandidateCommand(index);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST
                        + SelectCandidateCommand.MESSAGE_USAGE);
            }
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE);
        }
    }
}
