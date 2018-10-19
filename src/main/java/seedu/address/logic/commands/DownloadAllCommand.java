package seedu.address.logic.commands;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import seedu.address.logic.*;
import seedu.address.logic.commands.exceptions.*;
import seedu.address.model.*;
import org.openqa.selenium.support.ui.*;
import java.io.*;
import java.util.*;

public class DownloadAllCommand extends Command {

    public static final String COMMAND_WORD="download";
    public static final String MESSAGE_USAGE="download pass/(password) user/(username) mod/(moduleCode)";
    public static final String CHROMEDRIVER_PATH="\\chromedriver.exe";
    public static final String DOWNLOADRELATIVE_PATH="\\notesDownload";

    private String username;
    private String password;
    private String moduleCode;


    public DownloadAllCommand(String username, String password, String moduleCode){
        this.password=password;
        this.username=username;
        this.moduleCode=moduleCode;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history)
    {
        try
        {
            String test=System.setProperty("webdriver.chrome.driver","D:\\JavaCode\\Michaels-ver-of-ab4\\chromedriver.exe");
            System.out.println(test);
            WebDriver driver=new ChromeDriver();
            driver.get("https://ivle.nus.edu.sg");
        }
        catch (Exception e)
        {
            System.out.println("FUCK");
        }

        return new CommandResult("BING BONG BANG");
    }



}
