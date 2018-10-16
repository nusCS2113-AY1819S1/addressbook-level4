package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDistributorsCommand;
import seedu.address.logic.commands.EditDistributorsCommand.EditDistributorDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditDistributorsCommandParser implements Parser<EditDistributorsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDistributorsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIST_NAME, PREFIX_DIST_PHONE);


        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditDistributorsCommand.MESSAGE_USAGE), pe);
        }

        EditDistributorDescriptor editDistributorDescriptor = new EditDistributorDescriptor();
        if (argMultimap.getValue(PREFIX_DIST_NAME).isPresent()) {
            editDistributorDescriptor.setDistName(ParserUtil.parseDistName(argMultimap.getValue(PREFIX_DIST_NAME)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_DIST_PHONE).isPresent()) {
            editDistributorDescriptor.setDistPhone(ParserUtil.parseDistPhone(argMultimap.getValue(PREFIX_DIST_PHONE)
                    .get()));
        }

        return new EditDistributorsCommand(index, editDistributorDescriptor);
    }
}


/*
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.

    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
*/
