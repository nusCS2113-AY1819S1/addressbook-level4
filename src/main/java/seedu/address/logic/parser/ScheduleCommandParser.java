package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Activity;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

public class ScheduleCommandParser implements Parser<ScheduleCommand> {
	/**
	 * Parses the given {@code String} of arguments in the context of the AddCommand
	 * and returns an AddCommand object for execution.
	 * @throws ParseException if the user input does not conform the expected format
	 */
	public ScheduleCommand parse(String args) throws ParseException {
		ArgumentMultimap argMultimap =
				ArgumentTokenizer.tokenize(
						args,
						PREFIX_DATE,
						PREFIX_ACTIVITY);

		if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_ACTIVITY)
				|| !argMultimap.getPreamble().isEmpty()) {
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
		}
		Date date = new Date();
		ParserUtil.parseName(argMultimap.getValue(PREFIX_DATE).get());

		Activity name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
		Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
		Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
		Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());

		//@@author LowGinWee
		/**
		 * Checks if note has been specified
		 */
		Note note = new Note();
		if (arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
			note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
		}

		/**
		 * Checks if position has been specified
		 */
		Position position = new Position();
		if (arePrefixesPresent(argMultimap, PREFIX_POSITION)) {
			position = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
		}

		/**
		 * Checks if Kpi has been specified
		 */
		Kpi kpi = new Kpi();
		if (arePrefixesPresent(argMultimap, PREFIX_KPI)) {
			kpi = ParserUtil.parseKpi(argMultimap.getValue(PREFIX_KPI).get());
		}
		//@@author

		Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

		Person person = new Person(name, phone, email, address, position, kpi, note, tagList);

		return new AddCommand(person);
	}

	/**
	 * Returns true if none of the prefixes contains empty {@code Optional} values in the given
	 * {@code ArgumentMultimap}.
	 */
	private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
		return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
	}
}
