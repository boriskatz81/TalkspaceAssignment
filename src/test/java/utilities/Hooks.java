package utilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;

public class Hooks {

    @Before
    public void setUp() {
        closeDriver();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //       options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("window-size=1920,1080");
        options.addArguments("--lang=en_US");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.61 Safari/537.36");

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);
        Base.driver = new ChromeDriver(options);
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println(">>> [HOOKS] Ending scenario: " + scenario.getName());
        if (Base.driver != null) {
            Base.driver.quit();
        }
        Base.driver = null;
    }

    public static void closeDriver() {
        try {Base.driver.quit();} catch (Exception ex) {}
    }
}