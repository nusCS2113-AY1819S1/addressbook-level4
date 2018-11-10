package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateCommand;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateInitializationCommand;
import seedu.recruit.logic.commands.FilterCompanyCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.SortCompanyCommand;
import seedu.recruit.logic.commands.SortJobOfferCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteShortlistedCandidateInitializationCommand object
 */
public class DeleteShortlistedCandidateParser {

    /**
     * Constructor to parse commands if the logic state is within delete process
     * @param commandWord name of the command taken from RecruitBookParser
     * @param arguments arguments of the command taken from RecruitBookParser
     * @param state logic state
     * @return the delete command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String commandWord, String arguments, LogicState state)
            throws ParseException {
        String userInput = commandWord + arguments;
        if (state.nextCommand.equals(DeleteShortlistedCandidateCommand.COMMAND_LOGIC_STATE)) {
            switch (commandWord) {

            case DeleteShortlistedCandidateCommand.COMMAND_WORD:
                Index index = ParserUtil.parseIndex(arguments);
                return new DeleteShortlistedCandidateCommand(index);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + DeleteShortlistedCandidateCommand.MESSAGE_USAGE);
            }

        } else if (state.nextCommand.equals(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE)) {
            switch (commandWord) {

            case SortCompanyCommand.COMMAND_WORD:
                return new SortCompanyCommandParser().parse(arguments);

            case FindCompanyCommand.COMMAND_WORD:
                return new FindCompanyCommandParser(userInput).parse(arguments);

            case FilterCompanyCommand.COMMAND_WORD:
                return new FilterCompanyCommandParser(userInput).parse(arguments);

            case SelectCompanyCommand.COMMAND_WORD:
                Index index = ParserUtil.parseIndex(arguments);
                return new SelectCompanyCommand(index);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + DeleteShortlistedCandidateInitializationCommand.MESSAGE_NEXT_STEP
                        + SelectCompanyCommand.MESSAGE_USAGE);
            }

        } else if (state.nextCommand.equals(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE)) {
            switch (commandWord) {

            case SortJobOfferCommand.COMMAND_WORD:
                return new SortJobOfferCommandParser().parse(arguments);

            case SelectJobCommand.COMMAND_WORD:
                Index index = ParserUtil.parseIndex(arguments);
                return new SelectJobCommand(index);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                        + SelectJobCommand.MESSAGE_USAGE);
            }

        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE);
        }
    }
}
