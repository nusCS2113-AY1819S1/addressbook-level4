package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.schedule.Activity;

import java.util.Date;

import static java.util.Objects.requireNonNull;

public class ScheduleEditCommand extends ScheduleCommand{

	private final Activity toAdd;
	private final Date date;
	private final Index index;

	/**
	 * Creates an AddCommand to add the specified {@code Person}
	 */
	public ScheduleEditCommand(Date date, Index index, Activity activity) {
		requireNonNull(activity);
		toAdd = activity;
		this.date = date;
		this.index = index;
	}

	@Override
	public CommandResult updateSchedule(Model model) {
		model.deleteActivity(date, index);
		model.addActivity(toAdd);
		return new CommandResult("success in adding scheudle");
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ScheduleEditCommand// instanceof handles nulls
				&& toAdd.equals(((ScheduleEditCommand) other).toAdd)
				&& date.equals(((ScheduleEditCommand) other).date)
				&& index.equals(((ScheduleEditCommand) other).index));
	}
}
