package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NoteDeleteCommand object
 */
public class NoteDeleteCommandParser implements Parser<NoteDeleteCommand> {

    private static final Pattern NUMBER_RANGE_REGEX = Pattern.compile("^(?<from>-?[0-9]+)-(?<to>-?[0-9]+)$");
    /**
     * Parses the given {@code String} of arguments in the context of the NoteDeleteCommand
     * and returns a NoteDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NoteDeleteCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteDeleteCommand.MESSAGE_USAGE));
        }

        String[] indexListString = args.trim().split("\\s+");

        List<Integer> indexList = new ArrayList<>();

        for (String indexAsString : indexListString) {
            Matcher matcher = NUMBER_RANGE_REGEX.matcher(indexAsString);
            if (matcher.matches()) {
                int lowerIndex = Integer.parseInt(matcher.group("from"));
                int upperIndex = Integer.parseInt(matcher.group("to"));

                if (lowerIndex >= upperIndex || lowerIndex <= 0) {
                    throw new ParseException(NoteDeleteCommand.MESSAGE_PARSE_INDEX_ERROR);
                }

                for (int i = lowerIndex; i <= upperIndex; i++) {
                    indexList.add(i);
                }
            } else {
                try {
                    Integer num = Integer.parseInt(indexAsString.trim());
                    if (num > 0) {
                        indexList.add(num);
                    } else {
                        throw new ParseException(NoteDeleteCommand.MESSAGE_PARSE_INDEX_ERROR);
                    }
                } catch (NumberFormatException e) {
                    throw new ParseException(NoteDeleteCommand.MESSAGE_PARSE_INDEX_ERROR);
                }
            }
        }

        Set<Integer> indexListWithoutDuplicates = new HashSet<>(indexList);

        if (indexListWithoutDuplicates.size() < indexList.size()) {
            throw new ParseException(NoteDeleteCommand.MESSAGE_DUPLICATE_INDEX_FOUND);
        } else {
            indexList.sort(Collections.reverseOrder());
        }

        return new NoteDeleteCommand(indexList);
    }
}
