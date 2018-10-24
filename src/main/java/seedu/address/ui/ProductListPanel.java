package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ProductPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.product.Product;

/**
 * Panel containing the list of products.
 */
public class ProductListPanel extends UiPart<Region> {
    private static final String FXML = "ProductListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProductListPanel.class);

    @FXML
    private ListView<Product> productListView;

    public ProductListPanel(ObservableList<Product> productList) {
        super(FXML);
        setConnections(productList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Product> productList) {
        productListView.setItems(productList);
        productListView.setCellFactory(listView -> new ProductListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        productListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in product list panel changed to : '" + newValue + "'");
                        raise(new ProductPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ProductCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            productListView.scrollTo(index);
            productListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Product} using a {@code ProductCard}.
     */
    class ProductListViewCell extends ListCell<Product> {
        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);

            if (empty || product == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProductCard(product, getIndex() + 1).getRoot());
            }
        }
    }
}
