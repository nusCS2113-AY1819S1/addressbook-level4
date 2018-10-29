# kelvintankaiboon
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     *
     * Parses a {@code String fileName}.
     * @throws ParseException if the given string is invalid.
     */
    public static String parseFileName(String fileName) throws ParseException {
        requireNonNull(fileName);
        String invalidFileNameRegex = "[^\\w\\-. ]";
        String trimmedName = fileName.trim();
        if (trimmedName.matches(invalidFileNameRegex)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return trimmedName;
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Saves the current version of the StockList */
    void saveStockList(String fileName);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate that saveCommand has been called */
    private void indicateSaveStockListVersion(String fileName) {
        raise(new SaveStockListVersionEvent(versionedStockList, fileName));
    }

    @Override
    public void saveStockList(String fileName) {
        indicateSaveStockListVersion(fileName);
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedStockList.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        versionedStockList.removeItem(target);
        indicateStockListChanged();
    }

    @Override
    public void addItem(Item item) {
        versionedStockList.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        indicateStockListChanged();
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        versionedStockList.updateItem(target, editedItem);
        indicateStockListChanged();
    }

    //=========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedStockList}
     */
    @Override
    public ObservableList<Item> getFilteredItemList() {
        return FXCollections.unmodifiableObservableList(filteredItems);
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void saveStockListVersion(ReadOnlyStockList stockList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        stockListStorage.saveStockListVersion(stockList, filePath);
    }

    @Override
    @Subscribe
    public void handleSaveStockListVersionEvent(SaveStockListVersionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Saving current version to file"));
        try {
            saveStockListVersion(event.data, Paths.get("versions", event.fileName));
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
```
###### \java\seedu\address\storage\XmlStockListStorage.java
``` java
    @Override
    public void saveStockListVersion(ReadOnlyStockList stockList, Path filePath) throws IOException {
        requireNonNull(stockList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableStockList(stockList));
    }
}
```
