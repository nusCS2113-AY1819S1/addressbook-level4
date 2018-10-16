import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.util.*;

// note to self. running code: -> go to file->project structure->modules->dependencies then add the jar files.

public class Main {

    public static void main(String[] args) throws InterruptedException
    {

        //input user details
        System.out.println("Enter ivle user name: ie: E1234567");
        Scanner scanner= new Scanner(System.in);
        String ivleUsername=scanner.nextLine();
        System.out.println("Enter Password");
        String ivlePassword=scanner.nextLine();



        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        // TODO: 16/10/2018 Specifiy where the actual webdriver is to be stored in relative to ab4 and then include the route in the system set property
        // this path is relative to where the chromedriver is stored.

        String downloadFilePath="D:\\JavaCode\\test\\downloads";
        // TODO: 16/10/2018 SET THE PATH
        HashMap<String,Object> chromePrefs =  new HashMap<String,Object>();
        chromePrefs.put("profile.default_content_settings.popups",0);
        chromePrefs.put("download.default_directory",downloadFilePath);
        ChromeOptions options=new ChromeOptions();
        options.setExperimentalOption("prefs",chromePrefs);
        options.addArguments("headless");
        WebDriver driver=new ChromeDriver(options);

        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        //WebDriver driver= new ChromeDriver(options);

       // WebDriver driver=new ChromeDriver();

        // login to ivle
        driver.get("https://ivle.nus.edu.sg");
        WebElement usernameInputField0= driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_userid"));
        usernameInputField0.sendKeys(ivleUsername);
        WebElement passwordInputField0= driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_password"));
        passwordInputField0.sendKeys(ivlePassword);
        driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_btnSignIn")).click();


        //Access Download files page
        driver.get("https://ivle.nus.edu.sg/v1/File/download_all.aspx");
        //this is the download page link
        WebElement moduleDropList= driver.findElement(By.id("ctl00_ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_ddlModule"));



        //this prints out all the modules that are available to the student
        Select dropDown= new Select(moduleDropList);
        List<WebElement> itemsModules=dropDown.getOptions();
        int itemCount=itemsModules.size();
        for(int i=1;i<itemCount;i++)
        {
            System.out.println(i+" : "+itemsModules.get(i).getText());
        }
        //at this point, you should be at the page which displays all the available file downloads.

        System.out.println("Enter Desired module by code by the index");
        String intendedModule=scanner.nextLine();
        dropDown.selectByIndex(Integer.parseInt(intendedModule));

        List<WebElement> els=driver.findElements(By.xpath("//input[@type='checkbox']"));
        for(WebElement el: els)
        {

            if(!el.isSelected())
            {
                el.click();
            }
        }

        driver.findElement(By.id("ctl00_ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_btnDownloadSel")).click();


// TODO: 16/10/2018 the below code displays all the folders and then all the links of the files. Sooooooo some parsing must be done
/*
        WebElement treeview = driver.findElement(By.className("TreeView"));
        // to get folder name
        // its findElements not findElement coz i need to grab all of them instead of 1
        List<WebElement> folderResult = treeview.findElements(By.cssSelector("td > span"));
        for (int i=0; i<folderResult.size(); i++) {
            System.out.println(folderResult.get(i).getAttribute("innerText")); // folder name
        }


        // to get file names and their download link
        // its findElements not findElement coz i need to grab all of them instead of 1

        List<WebElement> fileResult = treeview.findElements(By.cssSelector("a[href^=\"/workbin\"]"));
        for (int i=0; i<fileResult.size(); i++) {
            System.out.println(fileResult.get(i).getText()); // filename
            System.out.println(fileResult.get(i).getAttribute("href")); // link
        }
*/


        //driver.close();
    }

}
