package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.schedule.Activity;

import static java.util.Objects.requireNonNull;

public class ScheduleAddCommand extends ScheduleCommand{

	private final Activity toAdd;

	/**
	 * Creates an AddCommand to add the specified {@code Person}
	 */
	public ScheduleAddCommand(Activity activity) {
		requireNonNull(activity);
		toAdd = activity;
	}

	@Override
	public CommandResult updateSchedule(Model model) {
		model.addActivity(toAdd);
		return new CommandResult("success in adding scheudle");
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ScheduleAddCommand// instanceof handles nulls
				&& toAdd.equals(((ScheduleAddCommand) other).toAdd));
	}
}
