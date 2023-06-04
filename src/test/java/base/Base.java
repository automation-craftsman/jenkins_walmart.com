package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.util.Properties;

public class Base {

    public static Properties prop;

    public static WebDriver driver;

    private static final ThreadLocal<WebDriver> PAGE_DRIVER = new ThreadLocal<>();

    public static void setDriver(WebDriver driver){

        Base.PAGE_DRIVER.set(driver);
    }

    public static WebDriver getDriver(){

        return PAGE_DRIVER.get();
    }

    public Base(){
        loadProperties();
    }

    /**
     *
     * @return the browser instance defined in the config.properties file
     */
    public static WebDriver getBrowser(){

        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("Chrome")){
            driver = WebDriverManager.chromedriver()
                    .remoteAddress(prop.getProperty("remote_address")).create();
            setDriver(driver);

        } else if (browserName.equalsIgnoreCase("Firefox")){
            driver = WebDriverManager.firefoxdriver()
                    .remoteAddress(prop.getProperty("remote_address")).create();
            setDriver(driver);

        } else if (browserName.equalsIgnoreCase("Edge")){
            driver = WebDriverManager.edgedriver()
                    .remoteAddress(prop.getProperty("remote_address")).create();
            setDriver(driver);

        } else {
            System.out.println("[i] Invalid browser name. Launching Chrome as default.");
            ChromeOptions opt = new ChromeOptions();
//            opt.addArguments("--headless=new");
            driver = WebDriverManager.chromedriver().capabilities(opt)
                    .remoteAddress(prop.getProperty("remote_address")).create();
            setDriver(driver);
        }

        return getDriver();

    }

    /**
     * Method to load the config.properties file
     */
    public void loadProperties(){

        prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +
                    "\\src\\test\\java\\config\\config.properties");
            prop.load(fis);
        }catch(Exception e){
            System.out.println("[!] Exception occurred while loading the config file.");
        }

    }

    /**
     * Method to initiate the driver
     */
    @BeforeTest
    public static synchronized void setUp(){
        driver = getBrowser();
        driver.manage().window().maximize();
        driver.get(prop.getProperty("url"));

        System.out.println("[i] Thread ID > " + Thread.currentThread().threadId() + " | " + "Driver ID > " + getDriver());
    }

    /**
     * Garbage collector after finishing tasks
     *
     * @throws InterruptedException
     */
    @AfterTest
    public static synchronized void cleanUp() {
        getDriver().quit();
    }

}
