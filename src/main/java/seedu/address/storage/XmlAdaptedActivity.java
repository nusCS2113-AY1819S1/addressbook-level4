package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.Activity;

public class XmlAdaptedActivity {
	public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";

	@XmlElement
	private Date date;
	@XmlElement
	private String activity;

	public XmlAdaptedActivity() {
	}

	public XmlAdaptedActivity(Date date, String activity) {
		this.date = date;
		this.activity = activity;
	}

	public XmlAdaptedActivity(Activity activity) {
		this.date = activity.getDate();
		this.activity = activity.getActivity();
	}

	public Activity toModelType() throws IllegalValueException {
		if (date == null) {
			throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, "date")));
		}
		if (activity == null) {
			throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, "name")));
		}

		return new Activity(date, activity);

	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof XmlAdaptedActivity)) {
			return false;
		}

		XmlAdaptedActivity otherActivity = (XmlAdaptedActivity) other;
		return Objects.equals(date, otherActivity.date)
				&& Objects.equals(activity, otherActivity.activity);
	}
}
