package seedu.address.logic.commands;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import seedu.address.logic.*;
import seedu.address.model.*;

import java.io.*;
import java.util.*;

public class DownloadAllCommand extends Command {

    public static final String COMMAND_WORD = "downloadAll";
    public static final String MESSAGE_USAGE = "downloadAll pass/(password) user/(username) mod/(moduleCode)";
    public static final String CHROMEDRIVER_PATH = "/chromedriver";
    public static final String DOWNLOAD_RELATIVE_PATH = "/notesDownload";
    public static final String IVLE_TITLE = "IVLE";
    public static final String IVLE_ADDRESS = "https://ivle.nus.edu.sg";
    public static final String IVLE_USERNAME_FIELD_ID = "ctl00_ctl00_ContentPlaceHolder1_userid";
    public static final String IVLE_PASSWORD_FIELD_ID = "ctl00_ctl00_ContentPlaceHolder1_password";
    public static final String IVLE_LOGIN_BUTTON_ID = "ctl00_ctl00_ContentPlaceHolder1_btnSignIn";
    public static final String WRONG_PASS_USER_MESSAGE = "WRONG PASSWORD OR USERNAME ENTERED";
    public static final String IVLE_DOWNLOAD_PAGE_ADDRESS = "https://ivle.nus.edu.sg/v1/File/download_all.aspx";
    public static final String IVLE_MODULE_LIST_FIELD_ID = "ctl00_ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_ddlModule";
    public static final String MODULE_NOT_FOUND_MESSAGE = "MODULE CODE NOT FOUND";
    public static final String CHECKBOX_XPATH_VALUE = "//input[@type='checkbox']";
    public static final String IVLE_DOWNLOAD_PAGE_BUTTON_ID = "ctl00_ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_btnDownloadSel";


    private String username;
    private String password;
    private String moduleCode;
    private String downloadFilePath;

    public DownloadAllCommand(String username, String password, String moduleCode){
        this.password=password;
        this.username=username;
        this.moduleCode=moduleCode.toLowerCase();
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String returnMessage= new String();
        returnMessage = bootWebDriver(returnMessage);

        return new CommandResult(returnMessage);
    }

    private String bootWebDriver(String returnMessage) {
        WebDriver driver=initilizeWebDriver();
        loginIvle(driver);

        if(checkLoggedIn(driver)==false){
            driver.close();
            return WRONG_PASS_USER_MESSAGE;
        }

        if(checkModExists(driver)){
           return moduleCode+" FILE DOWNLOADED AT :"+downloadFilePath;
        }
        else {
            driver.close();
            return MODULE_NOT_FOUND_MESSAGE;
        }
    }

    private WebDriver initilizeWebDriver(){
        downloadFilePath = new File("").getAbsolutePath();
        System.setProperty("webdriver.chrome.driver",downloadFilePath+CHROMEDRIVER_PATH);
        downloadFilePath += DOWNLOAD_RELATIVE_PATH;

        HashMap<String,Object> chromePrefs =  new HashMap<String,Object>();
        chromePrefs.put("profile.default_content_settings.popups",0);
        chromePrefs.put("download.default_directory",downloadFilePath);
        chromePrefs.put("browser.setDownloadBehavior", "allow");

        ChromeOptions options=new ChromeOptions();
        options.setExperimentalOption("prefs",chromePrefs);
        WebDriver driver=new ChromeDriver(options);
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

    private boolean checkModExists (WebDriver driver){
        driver.get(IVLE_DOWNLOAD_PAGE_ADDRESS);
        Select dropDown= new Select(driver.findElement(By.id(IVLE_MODULE_LIST_FIELD_ID)));
        List<WebElement> itemsModules=dropDown.getOptions();
        int itemCount=itemsModules.size();
        for(int i=1;i<itemCount;i++) {
            if(checkModMatches(itemsModules.get(i).getText().toLowerCase())) {
                moduleCode=itemsModules.get(i).getText();
                dropDown.selectByIndex(i);
                downloadFiles(driver);
                return true;
            }
        }
        return false;
    }
    private boolean checkModMatches(String input){
        try {
            for(int i=0;i<6;i++){
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

    private void downloadFiles(WebDriver driver){
        List<WebElement> checkBoxList=driver.findElements(By.xpath(CHECKBOX_XPATH_VALUE));
        initilizeDownloadFolder();
        for(WebElement checkBox: checkBoxList) {
            if(!checkBox.isSelected()) {
                checkBox.click();
            }
        }
        driver.findElement(By.id(IVLE_DOWNLOAD_PAGE_BUTTON_ID)).click();
        dynamicWaiting(driver);

    }
    private void initilizeDownloadFolder(){
        File folder = new File(downloadFilePath);
        File[] filesList = folder.listFiles();

        for (int i = 0; i < filesList.length; i++) {
            File currentFile = filesList[i];
            if (currentFile.getName().endsWith(".crdownload")) {
                filesList[i].delete();
            }
        }
    }
    private void dynamicWaiting(WebDriver driver){
        String[] keyExtentions={"crdownload"};
        try {
            do {
                Thread.sleep(100);
            } while(!org.apache.commons.io.FileUtils.listFiles(new File(downloadFilePath), keyExtentions,false).isEmpty());
        }
        catch(InterruptedException e){
        }
            driver.close();
    }
}
