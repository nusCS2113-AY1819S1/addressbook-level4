package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NoteDeleteCommand object
 */
public class NoteDeleteCommandParser implements Parser<NoteDeleteCommand> {
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

        Set<Integer> indexListWithoutDuplicates = new HashSet<>(indexList);

        if (indexListWithoutDuplicates.size() < indexList.size()) {
            throw new ParseException(NoteDeleteCommand.MESSAGE_DUPLICATE_INDEX_FOUND);
        } else {
            indexList.sort(Collections.reverseOrder());
        }

        for (Integer i : indexList) {
            System.out.println(i.toString());
        }

        return new NoteDeleteCommand(indexList);
    }
}
