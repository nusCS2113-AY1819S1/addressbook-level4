//@@author gaoqikai
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AddTagCommand.AddTagDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parsers the given (@code String) of arguments in the context of the AddTagCommand and
     * returns an AddTagCommand object for execution.
     * @Throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;

        AddTagDescriptor addTagDescriptor = new AddTagCommand.AddTagDescriptor();

        parseTagsToAdd(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(addTagDescriptor::setTags);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE), pe);
        }

        if (!addTagDescriptor.haveTag()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_NO_TAG));
        }

        return new AddTagCommand(index, addTagDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsToAdd(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
