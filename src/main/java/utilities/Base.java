package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import java.util.concurrent.TimeUnit;

public class Base {
    protected static WebDriver driver;

    public static void initChromeDriver() {
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
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
    }

    public static void closeDriver() {
        try {driver.quit();} catch (Exception ex) {}
    }

    @AfterClass
    public void tearDown() {
        closeDriver();
    }
}
