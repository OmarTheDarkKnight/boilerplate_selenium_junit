package base;

import com.bat.configurations.SpringConfig;
import com.bat.webdrivers.provider.WebDriverProvider;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.ExcelFileReader;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver = null;
    public WebDriverWait wait = null;
    public Properties prop = null;
    public JavascriptExecutor js;
    public Actions act;
    public static ExcelFileReader excelFileReader;

    public String xlsxFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data..xlsx";

    /**
     * launches browser based on the string provided by initializing the spring application context
     * Reads the preferences for the specific browser from properties file and set the browser accordingly
     * */
    public void launchBrowser(String browserName) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        WebDriverProvider webDriverProvider = applicationContext.getBean("webDriverProvider", WebDriverProvider.class);
        try {
            driver = webDriverProvider.getWebDriver(browserName);
            // setting a default explicit wait
            setExplicitWaitTime(20);

            js = (JavascriptExecutor)driver;
            act = new Actions(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the explicit wait time according to param @sectToWait
     * */
    public void setExplicitWaitTime(long secsToWait) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(secsToWait));
    }

    /**
     * param @filePath
     *
     * Check if the param has "//" in the path
     * If it does not have "//" then this is just a file name.
     * Assume the file is in resources folder.
     * Build the full path with the help of 'env' variable and return it
     *
     * Else if the path start with "//src" substring. If it does,
     * Then assume that the file is inside the project. Add the project path with it and return it.
     *
     * Else Assume  the file path is alright and return it as it is.
     * */
    public void loadPropertyFile(String filePath) {
        int lastOccurrence = filePath.lastIndexOf("//");
        if(lastOccurrence < 0)
            filePath = System.getProperty("user.dir") + "//src//test//resources//" + filePath;
        else if(filePath.substring(0, lastOccurrence).startsWith("//src"))
            filePath = System.getProperty("user.dir") + filePath;

        try {
            prop = new Properties();
            FileInputStream fs = new FileInputStream(filePath);
            prop.load(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProp(String key) {
        return prop.getProperty(key);
    }

    /**
     * sleep for n number of seconds passed as the parameter
     * */
    public void dynamicSleep(int time) {
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForPageToLoad(boolean includesJQuery){
        for(int i = 0; i!=10; i++){
            if(js.executeScript("return document.readyState;").equals("complete")) break; else dynamicSleep(2);
        }

        // check for jquery status
        if(includesJQuery) {
            for(int i = 0; i!=10; i++){
                if(((Long) js.executeScript("return jQuery.active;")) == 0 ) break; else dynamicSleep(2);
            }
        }
    }

    /**
     * param @locatorKey is used to find the element in the page
     * returns true if the element is present and visible
     * else returns false
     *
     * Uses explicit wait to wait for the element to to be present and visible
     * */
    public boolean isElementPresent(String locatorKey) {
        By locator = getObjectLocator(locatorKey);
        setExplicitWaitTime(10);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator));

        return true;
    }

    /**
     * param @locatorKey is the key of the properties file
     * a reference of the org.openqa.selenium.By class is returned depending on the ending sub-string of the @locatorKey
     * */
    public By getObjectLocator(@NotNull String locatorKey) {
        if(locatorKey.endsWith("_id")) return By.id(getProp(locatorKey));
        else if(locatorKey.endsWith("_name")) return By.name(getProp(locatorKey));
        else if(locatorKey.endsWith("_css")) return By.cssSelector(getProp(locatorKey));
        else return By.xpath(getProp(locatorKey));
    }

    public void mainWindow() {
        driver.switchTo().defaultContent();
    }

    /**
     * Opens a new tab or window
     * Or switches to a different window with the provided name
     * */
    public void switchWindow(String windowName) {
        windowName = windowName.trim();
        if(windowName.equalsIgnoreCase("tab") || windowName.equalsIgnoreCase("window"))
            driver.switchTo().newWindow(WindowType.fromString(windowName));
        driver.switchTo().window(windowName);
    }

    /**
     * Closes the current window and switches back to the default window
     * */
    public void closeAndSwitch() {
        driver.close();
        mainWindow();
    }

    /**
     * CLoses the current window and switches back to the window name provided
     * */
    public void closeAndSwitch(String windowName) {
        driver.close();
        switchWindow(windowName.trim());
    }

    /**
     * Vertical & Horizontal scroll
     * */
    public void scroll(double x, double y) {
        js.executeScript("window.scrollTo("+ x +","+ (y-200) +")");
    }

    /**
     * Vertical scroll
     * */
    public void scroll(double y) {
        scroll(0, y);
    }
}
