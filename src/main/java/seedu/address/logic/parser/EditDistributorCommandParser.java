package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDistributorCommand;
import seedu.address.logic.commands.EditDistributorCommand.EditDistributorDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new Command object
 */
public class EditDistributorCommandParser implements Parser<EditDistributorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditProductCommand
     * and returns an EditProductCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDistributorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIST_NAME, PREFIX_DIST_PHONE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditDistributorCommand.MESSAGE_USAGE), pe);
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

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editDistributorDescriptor::setTags);

        if (!editDistributorDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDistributorCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDistributorCommand(index, editDistributorDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */

    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

