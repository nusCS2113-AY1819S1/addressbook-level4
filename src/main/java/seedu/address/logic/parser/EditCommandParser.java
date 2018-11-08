package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRIBUTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMAINING_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditProductCommand;
import seedu.address.logic.commands.EditProductCommand.EditProductDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditProductCommand object
 */
public class EditCommandParser implements Parser<EditProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditProductCommand
     * and returns an EditProductCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditProductCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SERIAL_NR, PREFIX_DISTRIBUTOR,
                         PREFIX_PRODUCT_INFO, PREFIX_REMAINING_ITEMS, PREFIX_TAG);


        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format
                    (MESSAGE_INVALID_COMMAND_FORMAT, EditProductCommand.MESSAGE_USAGE), pe);
        }

        EditProductDescriptor editProductDescriptor = new EditProductDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editProductDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_SERIAL_NR).isPresent()) {
            editProductDescriptor.setSerialNumber(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_SERIAL_NR).get()));
        }
        if (argMultimap.getValue(PREFIX_DISTRIBUTOR).isPresent()) {
            editProductDescriptor.setEmail(ParserUtil.parseDistName(argMultimap.getValue(PREFIX_DISTRIBUTOR).get()));
        }
        if (argMultimap.getValue(PREFIX_PRODUCT_INFO).isPresent()) {
            editProductDescriptor
                    .setProductInfo(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_PRODUCT_INFO).get()));
        }
        if (argMultimap.getValue(PREFIX_REMAINING_ITEMS).isPresent()) {
            editProductDescriptor.setRemainingItems(ParserUtil.parseRemainingItems
                    (argMultimap.getValue(PREFIX_REMAINING_ITEMS).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editProductDescriptor::setTags);

        if (!editProductDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditProductCommand.MESSAGE_NOT_EDITED);
        }

        return new EditProductCommand(index, editProductDescriptor);
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

