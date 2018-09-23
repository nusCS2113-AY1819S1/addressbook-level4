package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collection;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagsContainsKeywords;


/**
 * Parses the arguments of tag command and returns as a Tag object
 */
public class TagCommandParser implements Parser<TagCommand> {

    public TagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        Collection<String> C = Arrays.asList(tagKeywords); // Passing it a collection

        return new TagCommand(new TagsContainsKeywords(ParserUtil.parseTags(C)));
    }

}
