package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_MARITAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_OCCUPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_WEIGHT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddInfoCommand;
import seedu.address.logic.commands.AddInfoCommand.AddInfoPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddInfoCommand} object
 */
public class AddInfoCommandParser implements Parser<AddInfoCommand> {
    /**
     *
     * Parse the given {@code String} of arguments in the context of {@code AddInfoCommand}
     * and returns a {@code AddInfoCommand} object for execution.
     * @throws ParseException if the user input does not conform to expected format
     */
    public AddInfoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_INFO_NRIC, PREFIX_ADD_INFO_DOB,
                PREFIX_ADD_INFO_HEIGHT, PREFIX_ADD_INFO_WEIGHT, PREFIX_ADD_INFO_GENDER, PREFIX_ADD_INFO_BLOODTYPE,
                PREFIX_ADD_INFO_OCCUPATION, PREFIX_ADD_INFO_MARITAL);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInfoCommand.MESSAGE_USAGE), ive);
        }

        AddInfoPersonDescriptor addInfoPersonDescriptor = new AddInfoPersonDescriptor();
        if (argMultiMap.getValue(PREFIX_ADD_INFO_NRIC).isPresent()) {
            addInfoPersonDescriptor.setNric(ParserUtil.parseNric(argMultiMap.getValue(PREFIX_ADD_INFO_NRIC).get()));
        }
        if (argMultiMap.getValue(PREFIX_ADD_INFO_DOB).isPresent()) {
            addInfoPersonDescriptor.setDateOfBirth(ParserUtil.parseDateOfBirth(
                    argMultiMap.getValue(PREFIX_ADD_INFO_DOB).get()));
        }
        if (argMultiMap.getValue(PREFIX_ADD_INFO_HEIGHT).isPresent()) {
            addInfoPersonDescriptor.setHeight(ParserUtil.parseHeight(
                    argMultiMap.getValue(PREFIX_ADD_INFO_HEIGHT).get()));
        }
        if (argMultiMap.getValue(PREFIX_ADD_INFO_WEIGHT).isPresent()) {
            addInfoPersonDescriptor.setWeight(ParserUtil.parseWeight(
                    argMultiMap.getValue(PREFIX_ADD_INFO_WEIGHT).get()));
        }
        if (argMultiMap.getValue(PREFIX_ADD_INFO_GENDER).isPresent()) {
            addInfoPersonDescriptor.setGender(ParserUtil.parseGender(
                    argMultiMap.getValue(PREFIX_ADD_INFO_GENDER).get()));
        }
        if (argMultiMap.getValue(PREFIX_ADD_INFO_BLOODTYPE).isPresent()) {
            addInfoPersonDescriptor.setBloodType(ParserUtil.parseBloodType(
                    argMultiMap.getValue(PREFIX_ADD_INFO_BLOODTYPE).get()));
        }
        if (argMultiMap.getValue(PREFIX_ADD_INFO_OCCUPATION).isPresent()) {
            addInfoPersonDescriptor.setOccupation(ParserUtil.parseOccupation(
                    argMultiMap.getValue(PREFIX_ADD_INFO_OCCUPATION).get()));
        }
        if (argMultiMap.getValue(PREFIX_ADD_INFO_MARITAL).isPresent()) {
            addInfoPersonDescriptor.setMaritalStatus(ParserUtil.parseMaritalStatus(
                    argMultiMap.getValue(PREFIX_ADD_INFO_MARITAL).get()));
        }


        if (!addInfoPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AddInfoCommand.MESSAGE_NOT_EDITED);
        }

        return new AddInfoCommand(index, addInfoPersonDescriptor);
    }
}
