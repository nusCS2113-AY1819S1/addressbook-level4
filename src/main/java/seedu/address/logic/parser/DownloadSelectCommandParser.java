package seedu.address.logic.parser;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.*;

import java.util.*;
import java.util.stream.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELECT_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

public class DownloadSelectCommandParser implements Parser {

    @Override
    public DownloadSelectCommand parse(String args) throws ParseException{

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,PREFIX_USERNAME,PREFIX_PASSWORD,PREFIX_MODULECODE,PREFIX_SELECT_FILE);

        if (!arePrefixesPresent(argMultimap,PREFIX_PASSWORD,PREFIX_USERNAME,PREFIX_MODULECODE)){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadSelectCommand.MESSAGE_USAGE));
        }

        String username = argMultimap.getValue(PREFIX_USERNAME).get();
        String password = argMultimap.getValue(PREFIX_PASSWORD).get();
        String moduleCode = argMultimap.getValue(PREFIX_MODULECODE).get();

        if(argMultimap.getValue(PREFIX_SELECT_FILE).isPresent()){
            String fileSelect = argMultimap.getValue(PREFIX_SELECT_FILE).get();
            if(fileSelect.isEmpty()){
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadSelectCommand.NO_FILES_SELECTED_MESSAGE));

            }
            return new DownloadSelectCommand( username, password, moduleCode,fileSelect);
        }
        else{
            return new DownloadSelectCommand( username, password, moduleCode);
        }

    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
