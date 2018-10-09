package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.recruit.logic.commands.AddCompanyCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.UniqueJobList;


/**
 * Parses input arguments and creates a new AddCompanyCommand object
 */

public class AddCompanyCommandParser implements Parser<AddCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCompanyCommand
     * and returns an AddCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCompanyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMPANY_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCompanyCommand.MESSAGE_USAGE));

        }

        CompanyName companyName = ParserUtil.parseCompanyName(argMultimap.getValue(PREFIX_COMPANY_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());

        Company company = new Company(companyName, address, email, phone, new UniqueJobList());
        return new AddCompanyCommand(company);
    }



    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
