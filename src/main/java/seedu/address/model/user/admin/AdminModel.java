package seedu.address.model.user.admin;

import seedu.address.model.Model;
import seedu.address.model.user.accountant.AccountantModel;
import seedu.address.model.user.manager.ManagerModel;
import seedu.address.model.user.stocktaker.StockTakerModel;

/**
 * The API of the Model component.
 */
public interface AdminModel extends Model, StockTakerModel, AccountantModel, ManagerModel {
    boolean isValid();

}
