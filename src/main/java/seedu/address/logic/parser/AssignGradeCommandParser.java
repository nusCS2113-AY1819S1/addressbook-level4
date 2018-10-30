package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;

import seedu.address.logic.commands.AssignGradeCommand;

import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments (test name)
 */
public class AssignGradeCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignGradeCommand
     * and returns an AssignGradeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
        public AssignGradeCommand parse(String args) throws ParseException {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignGradeCommand.MESSAGE_USAGE));
            }
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_TEST_NAME);

            String testName = "";


            if (argMultimap.getValue(PREFIX_TEST_NAME).isPresent()) {
                if (argMultimap.getValue(PREFIX_TEST_NAME).get().isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignGradeCommand.MESSAGE_USAGE));
                } else {
                    testName = argMultimap.getValue(PREFIX_TEST_NAME).get();
                }
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignGradeCommand.MESSAGE_USAGE));
            }

            return new AssignGradeCommand (testName);

        }

    }
