package seedu.address.logic.commands;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import seedu.address.logic.*;
import seedu.address.model.*;

import java.io.*;
import java.util.*;

public class DownloadSelectCommand extends Command {

    public static final String COMMAND_WORD = "downloadSelect";
    public static final String MESSAGE_USAGE = "downloadSelect pass/(password) user/(username) mod/(moduleCode) file/(0,1,2....n))";
    public static final String CHROMEDRIVER_PATH_WINDOWS = "/chromeDrivers/windows/chromedriver.exe";
    public static final String CHROMEDRIVER_PATH_MAC = "/chromeDrivers/mac/chromedriver";
    public static final String DOWNLOAD_RELATIVE_PATH = "/notesDownload";
    public static final String IVLE_TITLE = "IVLE";
    public static final String WINDOWS_OS_NAME="Windows";
    public static final String MAC_OS_NAME="Mac";
    public static final String IVLE_ADDRESS = "https://ivle.nus.edu.sg";
    public static final String IVLE_USERNAME_FIELD_ID = "ctl00_ctl00_ContentPlaceHolder1_userid";
    public static final String IVLE_PASSWORD_FIELD_ID = "ctl00_ctl00_ContentPlaceHolder1_password";
    public static final String IVLE_LOGIN_BUTTON_ID = "ctl00_ctl00_ContentPlaceHolder1_btnSignIn";
    public static final String WRONG_PASS_USER_MESSAGE = "WRONG PASSWORD OR USERNAME ENTERED";
    public static final String IVLE_DOWNLOAD_PAGE_ADDRESS = "https://ivle.nus.edu.sg/v1/File/download_all.aspx";
    public static final String IVLE_MODULE_LIST_FIELD_ID = "ctl00_ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_ddlModule";
    public static final String MODULE_NOT_FOUND_MESSAGE = "MODULE CODE NOT FOUND";
    public static final String WORKBIN_CSS_SELECTOR_ID = "a[href^=\"/workbin\"]";
    public static final String TREEVIEW_CLASS_ID = "TreeView";
    public static final String FILE_DOWNLOAD_LINK_ATTRIBUTE_ID="href";

    private String username;
    private String password;
    private ArrayList<Integer> fileSelect;
    private String moduleCode;
    private String downloadFilePath;

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
    public CommandResult execute(Model model, CommandHistory history) {
        String returnMessage;
        WebDriver driver=initializeWebDriver();
        loginIvle(driver);
        if(checkLoggedIn(driver)==false){
            driver.close();
            returnMessage = WRONG_PASS_USER_MESSAGE;
        }
        else{
            if(checkModuleExists(driver)){
                if(fileSelect==null) {
                    returnMessage=getFileNames(driver);
                    driver.close();
                }
                else{
                    initializeDownloadFolder();
                    downloadFiles(driver);
                    dynamicWaiting();
                    driver.close();
                    returnMessage = moduleCode+" FILES DOWNLOADED AT :"+downloadFilePath;
                }
            }
            else{
                driver.close();
                returnMessage = MODULE_NOT_FOUND_MESSAGE;
            }
        }
        return new CommandResult(returnMessage);
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
    private void downloadFiles(WebDriver driver){
        WebElement treeview = driver.findElement(By.className(TREEVIEW_CLASS_ID));
        List<WebElement> fileResult = treeview.findElements(By.cssSelector(WORKBIN_CSS_SELECTOR_ID));
        for(int fileID:fileSelect) {
            driver.get(fileResult.get(fileID).getAttribute(FILE_DOWNLOAD_LINK_ATTRIBUTE_ID));
        }
    }

    private WebDriver initializeWebDriver(){
        downloadFilePath = new File("").getAbsolutePath();

        if(System.getProperty("os.name").contains(WINDOWS_OS_NAME)) {
            System.setProperty("webdriver.chrome.driver",downloadFilePath+ CHROMEDRIVER_PATH_WINDOWS);
        }
        else if(System.getProperty("os.name").contains(MAC_OS_NAME)) {
            System.setProperty("webdriver.chrome.driver",downloadFilePath+ CHROMEDRIVER_PATH_MAC);
        }
        downloadFilePath += DOWNLOAD_RELATIVE_PATH ;

        HashMap<String,Object> chromePrefs =  new HashMap<String,Object>();
        chromePrefs.put("profile.default_content_settings.popups",0);
        chromePrefs.put("download.default_directory",downloadFilePath);
        chromePrefs.put("browser.setDownloadBehavior", "allow");

        ChromeOptions options=new ChromeOptions();
        options.setExperimentalOption("prefs",chromePrefs);
        WebDriver driver=new ChromeDriver(options);
        driver.manage().window().setPosition(new Point(-2000,0));
        return driver;
    }
    private void loginIvle(WebDriver driver){

        driver.get(IVLE_ADDRESS);
        driver.findElement(By.id(IVLE_USERNAME_FIELD_ID)).sendKeys(username);
        driver.findElement(By.id(IVLE_PASSWORD_FIELD_ID)).sendKeys(password);
        driver.findElement(By.id(IVLE_LOGIN_BUTTON_ID)).click();
    }

    private boolean checkLoggedIn(WebDriver driver){
        if(driver.getTitle().contains(IVLE_TITLE)){
            return false;
        }
        return true;
    }

    private boolean checkModuleExists(WebDriver driver){
        driver.get(IVLE_DOWNLOAD_PAGE_ADDRESS);
        Select dropDown= new Select(driver.findElement(By.id(IVLE_MODULE_LIST_FIELD_ID)));
        List<WebElement> itemsModules=dropDown.getOptions();
        int itemCount=itemsModules.size();
        //i starts at 1 because 0 is reserved for "select module"
        for(int i=1;i<itemCount;i++) {
            if(checkModuleMatches(itemsModules.get(i).getText().toLowerCase())) {
                moduleCode=itemsModules.get(i).getText();
                dropDown.selectByIndex(i);
                return true;
            }
        }
        return false;
    }
    private boolean checkModuleMatches(String input){
        try {
            for(int i=0;i<moduleCode.length();i++){
                if(input.charAt(i)!=moduleCode.charAt(i)) {
                    return false;
                }
            }
        }
        catch (StringIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private void initializeDownloadFolder(){
        File folder = new File(downloadFilePath);
        File[] filesList = folder.listFiles();

        for (int i = 0; i < filesList.length; i++) {
            File currentFile = filesList[i];
            if (currentFile.getName().endsWith(".crdownload")) {
                filesList[i].delete();
            }
        }
    }
    private void dynamicWaiting(){
        String[] keyExtentions={"crdownload"};
        try {
            do {
                Thread.sleep(100);
            } while(!org.apache.commons.io.FileUtils.listFiles(new File(downloadFilePath), keyExtentions,false).isEmpty());
        }
        catch(InterruptedException e){
        }
    }
}
