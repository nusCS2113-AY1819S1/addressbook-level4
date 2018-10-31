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

    /** Opens the .xml file indicated by user in the browser pane*/
    void openStockList(String fileName);
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

    /** Raises an event to indicate that OpenCommand has been called */
    private void indicateOpenStockListVersion(String fileName) {
        raise(new OpenStockListVersionEvent(versionedStockList, fileName));
    }

    @Override
    public void openStockList(String fileName) {
        indicateOpenStockListVersion(fileName);
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
