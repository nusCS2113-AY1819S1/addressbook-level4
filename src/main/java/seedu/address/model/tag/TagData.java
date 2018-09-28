package seedu.address.model.tag;

import seedu.address.model.record.Date;
import seedu.address.model.record.MoneyFlow;
import seedu.address.model.record.Name;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TagData {

    private final Name name;
    private final Date date;
    private final MoneyFlow moneyFlow;

    public TagData(Name name, Date date, MoneyFlow moneyFlow) {
        requireAllNonNull(name, date, moneyFlow);
        this.name = name;
        this.date = date;
        this.moneyFlow = moneyFlow;
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public MoneyFlow getMoneyFlow() {
        return moneyFlow;
    }

}
