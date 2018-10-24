package seedu.address.logic.commands;
import org.openqa.selenium.*;
import seedu.address.logic.*;
import seedu.address.logic.commands.exceptions.*;
import seedu.address.model.*;
import java.util.*;

public class DownloadSelectCommand extends DownloadAbstract{

    public static final String COMMAND_WORD = "downloadSelect";

    public static final String MESSAGE_USAGE = "downloadSelect user/(username) pass/(password) mod/(moduleCode) file/(0,1,2...n))";

    public static final String NO_FILES_SELECTED_MESSAGE = "Please select a file after the \"file/\" tag. Ie: file/(0,1,2...n))";

    private static final String WORKBIN_CSS_SELECTOR_ID = "a[href^=\"/workbin\"]";

    private static final String TREEVIEW_CLASS_ID = "TreeView";

    private static final String FILE_DOWNLOAD_LINK_ATTRIBUTE_ID = "href";



    private ArrayList<Integer> fileSelect;
    /**
     * secondary constructor to handle execution if user enters values with the PREFIX_SELECT_FILE prefix.
     */

    public DownloadSelectCommand(String username, String password,String moduleCode,String fileSelectInput){
        this.password=password;
        this.username=username;
        this.moduleCode=moduleCode;
        fileSelect = new ArrayList<>();
        for(String id: fileSelectInput.split(",")){
            fileSelect.add(Integer.parseInt(id));
        }
    }

    public DownloadSelectCommand(String username, String password,String moduleCode){
        this.password=password;
        this.username=username;
        this.moduleCode=moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException
    {
        initializePaths();
        WebDriver driver=initializeWebDriver();
        loginIvle(driver);
        if(!isLoggedIn(driver)){
            driver.close();
            throw new CommandException(WRONG_PASS_USER_MESSAGE);
        }
        else{
            if(isModuleExisting(driver)){
                if(fileSelect==null) {
                    driver.close();
                    return new CommandResult(getFileNames(driver));
                }
                else{
                    initializeDownloadFolder();
                    downloadFiles(driver);
                    dynamicWaiting();
                    driver.close();
                    return new CommandResult(moduleCode + "\r\n" + "DOWNLOADED AT: " + downloadFilePath);

                }
            }
            else{
                driver.close();
                throw new CommandException(MODULE_NOT_FOUND_MESSAGE);
            }
        }
    }

    private String getFileNames(WebDriver driver){
        WebElement treeview = driver.findElement(By.className(TREEVIEW_CLASS_ID));
        List<WebElement> fileResult = treeview.findElements(By.cssSelector(WORKBIN_CSS_SELECTOR_ID));
        String result= new String();
        for (int i=0; i<fileResult.size(); i++) {
            result+=(i+": "+fileResult.get(i).getText()+"\r\n");
            //below statements are for debug. todo: remove when publishing
            //System.out.println(fileResult.get(i).getText()); // filename
            //System.out.println(fileResult.get(i).getAttribute("href")); // link
        }
        return result;
    }
    protected void downloadFiles(WebDriver driver){
        WebElement treeview = driver.findElement(By.className(TREEVIEW_CLASS_ID));
        List<WebElement> fileResult = treeview.findElements(By.cssSelector(WORKBIN_CSS_SELECTOR_ID));
        for(int fileID:fileSelect) {
            driver.get(fileResult.get(fileID).getAttribute(FILE_DOWNLOAD_LINK_ATTRIBUTE_ID));
        }
    }
}
