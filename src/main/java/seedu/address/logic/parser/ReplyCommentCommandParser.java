package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReplyCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Name;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ReplyCommentCommandParser implements Parser<ReplyCommentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReplyCommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LINE, PREFIX_COMMENT, PREFIX_NAME);

        int line;
        String comment;
        Name name;
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_LINE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE));
        }
        line = ParserUtil.parseLine(argMultimap.getValue(PREFIX_LINE).get());

        if (!argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE));
        }
        comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).get());


        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplyCommentCommand.MESSAGE));
        }
        //editCommentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        return new ReplyCommentCommand(index , line , comment, name);
    }
}
