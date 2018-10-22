package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.schedule.Activity;

import java.util.Date;

import static java.util.Objects.requireNonNull;

public class ScheduleDeleteCommand extends ScheduleCommand{

	private final Date date;
	private final Index index;
	/**
	 * Creates an AddCommand to add the specified {@code Person}
	 */
	public ScheduleDeleteCommand(Date date, Index index) {
		requireNonNull(date);
		requireNonNull(index);
		this.date = date;
		this.index = index;
	}

	@Override
	public CommandResult updateSchedule(Model model) {
		model.deleteActivity(date, index);
		return new CommandResult("success in adding scheudle");
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ScheduleDeleteCommand// instanceof handles nulls
				&& date.equals(((ScheduleDeleteCommand) other).date)
				&& index.equals(((ScheduleDeleteCommand) other).index));
	}
}
