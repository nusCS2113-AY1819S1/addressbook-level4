package seedu.address.model.expenditureInfo;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ExpenditureInfo {
    private final Money money;
    private final Date date;

    public ExpenditureInfo(Money money, Date date){
        requireAllNonNull(money, date);
        this.money = money;
        this.date = date;
    }

    public Money getMoney() { return money; }
    public Date getDate() { return date; }

    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(money, date);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getMoney())
                .append(" Money ")
                .append(getDate())
                .append(" Date: ");
        return builder.toString();
    }
}
