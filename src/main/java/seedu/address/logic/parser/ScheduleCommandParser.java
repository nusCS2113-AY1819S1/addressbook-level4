package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleAddCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.ScheduleDeleteCommand;
import seedu.address.logic.commands.ScheduleEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Activity;

import java.util.Date;
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
		args = args.trim();
		String[] input = args.split(" ", 2);
		try {
			switch (input[0].trim()) {
				case ScheduleCommand.COMMAND_WORD_ADD:
					return parseAdd(input[1]);
				case ScheduleCommand.COMMAND_WORD_DELETE:
					return parseDelete(input[1]);
				case ScheduleCommand.COMMAND_WORD_EDIT:
					return parseEdit(input[1]);
				default:
					throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
							ScheduleCommand.MESSAGE_USAGE));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
				throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
		}
	}

	private ScheduleCommand parseAdd(String args) throws ParseException{
		ArgumentMultimap argMultimap =
				ArgumentTokenizer.tokenize(
						" " + args,
						PREFIX_DATE,
						PREFIX_ACTIVITY);
		if (!arePrefixesPresent(argMultimap,PREFIX_DATE, PREFIX_ACTIVITY) || !argMultimap.getPreamble().isEmpty()) {
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
		}
		Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
		String task = argMultimap.getValue(PREFIX_ACTIVITY).get();
		Activity activity = new Activity(date, task);
		return new ScheduleAddCommand(activity);
	}

	private ScheduleCommand parseDelete(String args) throws ParseException{

		Index index;
		try {
			index = Index.fromOneBased(Integer.parseInt(args));
		} catch (NumberFormatException e){
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
		}
		return new ScheduleDeleteCommand(index);
	}

	private ScheduleCommand parseEdit(String args) throws ParseException{
		ArgumentMultimap argMultimap =
				ArgumentTokenizer.tokenize(
						" " + args,
						PREFIX_ACTIVITY);
		if (!arePrefixesPresent(argMultimap, PREFIX_ACTIVITY)
				|| !argMultimap.getPreamble().isEmpty()) {
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
		}

		Index index;
		try {
			index = Index.fromOneBased(Integer.parseInt(argMultimap.getPreamble()));
		} catch (NumberFormatException e){
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
		}
		String task = argMultimap.getValue(PREFIX_ACTIVITY).get();
		return new ScheduleEditCommand(index, task);
	}

	/**
	 * Returns true if none of the prefixes contains empty {@code Optional} values in the given
	 * {@code ArgumentMultimap}.
	 */
	private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
		return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
	}
}
