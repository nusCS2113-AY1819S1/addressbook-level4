package seedu.address.model.tag;

import seedu.address.model.record.Date;
import seedu.address.model.record.MoneyFlow;
import seedu.address.model.record.Name;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TagData {

    private final Name name;

    // Data fields
    private final Date date;
    private final MoneyFlow moneyFlow;
    private final Set<Tag> tags = new HashSet<>();

    public TagData(Name name, Date date, MoneyFlow moneyFlow, Set<Tag> tags) {
        requireAllNonNull(name, date, moneyFlow, tags);
        this.name = name;
        this.date = date;
        this.moneyFlow = moneyFlow;
        this.tags.addAll(tags);
    }


}
