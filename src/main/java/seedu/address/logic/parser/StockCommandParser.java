package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StockCommand;
import seedu.address.logic.commands.StockCommand.StockBookDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new StockCommand object
 */
public class StockCommandParser implements Parser<StockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StockCommand
     * and returns an StockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StockCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ISBN, PREFIX_PRICE,
                        PREFIX_COST, PREFIX_QUANTITY, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockCommand.MESSAGE_USAGE), pe);
        }

        StockCommand.StockBookDescriptor stockBookDescriptor = new StockBookDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            stockBookDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ISBN).isPresent()) {
            stockBookDescriptor.setIsbn(ParserUtil.parseIsbn(argMultimap.getValue(PREFIX_ISBN).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            stockBookDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            stockBookDescriptor.setCost(ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            stockBookDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        parseTagsForStock(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(stockBookDescriptor::setTags);

        if (!stockBookDescriptor.isAnyFieldStocked()) {
            throw new ParseException(StockCommand.MESSAGE_NOT_STOCKED);
        }

        return new StockCommand(index, stockBookDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForStock(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
