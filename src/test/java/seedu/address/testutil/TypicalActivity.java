package seedu.address.testutil;

import seedu.address.model.schedule.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TypicalActivity {
	public static final Date DATE_1 = Activity.toDate(01, 01, 2018);
	public static final Date DATE_2= Activity.toDate(02, 01, 2018);
	public static final Date DATE_3 = Activity.toDate(03, 01, 2018);

	public static final Activity ACTIVITY_TASK_1 = new Activity(DATE_1,
			"Write a report.");

	public static final Activity ACTIVITY_TASK_2 = new Activity(DATE_2,
			"Send a reminder to Bob.");

	public static final Activity ACTIVITY_TASK_3 = new Activity(DATE_3,
			"Give Alexia a raise.");

	public static List<Activity> getTypicalActivities() {
		return new ArrayList<>(Arrays.asList(ACTIVITY_TASK_1, ACTIVITY_TASK_2, ACTIVITY_TASK_3));
	}
}
