package seedu.address.logic.parser;
import seedu.address.logic.parser.exceptions.*;
import seedu.address.logic.commands.DownloadAllCommand;

import java.util.stream.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

public class DownloadAllCommandParser implements Parser {

    @Override
    public DownloadAllCommand parse(String args) throws ParseException{

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,PREFIX_USERNAME,PREFIX_PASSWORD,PREFIX_MODULECODE);

         if (!arePrefixesPresent(argMultimap,PREFIX_PASSWORD,PREFIX_USERNAME,PREFIX_MODULECODE)){
             throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadAllCommand.MESSAGE_USAGE));
        }

        String username = argMultimap.getValue(PREFIX_USERNAME).get();
        String password = argMultimap.getValue(PREFIX_PASSWORD).get();
        String moduleCode = argMultimap.getValue(PREFIX_MODULECODE).get();

        return new DownloadAllCommand( username, password ,moduleCode);
    }
     private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
            return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
        }
}
