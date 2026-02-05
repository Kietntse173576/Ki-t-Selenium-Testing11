package selenium;
//<input data-v-1f99f73c="" class="oxd-input oxd-input--active" name="username" placeholder="Username" autofocus="">
// flow tao test case
// B1: khoi tao trinh duyet
// B2: Dieu huong toi website muon test
// B3: tim cac element, locator (input, button, label,...)
// de phuc vu viet cac step test
// B4: nhap du lieu, thao tac tren element
// B5: Verify ket qua (expect, actual)
// B6: Dong trinh duyet va giai phong tai nguyen

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class OrangeHRMLoginTest {
    //    B1: khoi tao trinh duyet
//    driver la class giup tuong tac tren page
    private WebDriver driver;

    private WebDriverWait wait;

    //    URL cua page Login
    private static final String LOGIN_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    //    username
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "admin123";

    //    setup moi truong test
//    before method: chay trước môỗi test case
//    VD: khoi tao driver
    @BeforeMethod
    public void setUp() {
//        B1: Setup chrome driver voi webdriver manager
        WebDriverManager.chromedriver().setup();

        //        B2: cau hinh chrome
        ChromeOptions options = new ChromeOptions();
//        mo browser o che do full screen
//        - Dam bao tat ca các elements deu hien thi
//        - Test nhat quan tren moi man hinh
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
//        B3: Co thoi gian doi chrome setup
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(description = "Test login thành công")
    public void testLoginSuccess() throws InterruptedException {
//        B1: truy cap trang web Login
        driver.get(LOGIN_URL);
        Thread.sleep(15000);
//        B2: tim element input username va fill username vao
        WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
        usernameField.sendKeys(USERNAME);
        Thread.sleep(2000);

//        B3: tim element input password va fill password
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys(PASSWORD);
        Thread.sleep(2000);
//        B4: tim element button Login va click vao button
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        loginBtn.click();
        Thread.sleep(5000);

//        B5: verify ket qua sau khi thao tac login
//        TH1: kiem tra URL co chua "dashboard" khong
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("dashboard"),
                "URL phải chứa 'dashboard' sau khi login"
        );
    }

    //    after method: cleanup - dong browser, giai phong tai nguyen
    @AfterMethod
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }
}