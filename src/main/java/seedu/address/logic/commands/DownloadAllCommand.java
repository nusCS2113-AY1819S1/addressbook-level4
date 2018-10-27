package seedu.address.logic.commands;
import org.openqa.selenium.*;
import seedu.address.commons.util.UnzipUtil;
import seedu.address.logic.*;
import seedu.address.logic.commands.exceptions.*;
import seedu.address.model.*;

import java.io.IOException;
import java.util.*;

public class DownloadAllCommand extends DownloadAbstract {

    public static final String COMMAND_WORD = "downloadAll";

    public static final String MESSAGE_USAGE = "downloadAll user/(username) pass/(password) mod/(moduleCode)";

    private static final String CHECKBOX_XPATH_VALUE = "//input[@type='checkbox']";

    private static final String IVLE_DOWNLOAD_PAGE_BUTTON_ID = "ctl00_ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_btnDownloadSel";


    public DownloadAllCommand (String username, String password, String moduleCode){
        this.password = password;
        this.username = username;
        this.moduleCode = moduleCode.toLowerCase();
        this.currentDirectory = System.getProperty(PARAM_CURRENT_DIRECTORY);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        initializePaths();
        WebDriver driver = initializeWebDriver();
        loginIvle(driver);
        if(!isLoggedIn(driver)) {
            driver.close();
            throw new CommandException(WRONG_PASS_USER_MESSAGE);
        }
        else{
            if(isModuleExisting(driver)){
                initializeDownloadFolder();
                downloadFiles(driver);
                dynamicWaiting();
                driver.close();
                try{
                    UnzipUtil.unzipFile(downloadTemporaryPath, UNZIP_FILE_KEYWORD,
                                        currentDirectory, DOWNLOAD_FILE_PATH, moduleCode);

                } catch (IOException ioe) {
                    throw new CommandException(MESSAGE_FILE_CORRUPTED);
                }
                return new CommandResult(moduleCode + MESSAGE_SUCCESS
                                            + currentDirectory + DOWNLOAD_FILE_PATH);
            }
            else{
                driver.close();
                throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
            }
        }
    }

    /**
     * downloadFiles selects all the available files to be downloaded and then selects the "download files" button
     */

    protected void downloadFiles(WebDriver driver){
        List<WebElement> checkBoxList=driver.findElements(By.xpath(CHECKBOX_XPATH_VALUE));
        for(WebElement checkBox: checkBoxList) {
            if(!checkBox.isSelected()) {
                checkBox.click();
            }
        }
        driver.findElement(By.id(IVLE_DOWNLOAD_PAGE_BUTTON_ID)).click();
    }
}
