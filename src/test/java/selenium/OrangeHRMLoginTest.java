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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigUtils;

import java.time.Duration;

public class OrangeHRMLoginTest {
    //    B1: khoi tao trinh duyet
//    driver la class giup tuong tac tren page
    private WebDriver driver;

    private WebDriverWait wait;

    //    URL cua page Login
//    private static final String LOGIN_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
//
//    //    username
//    private static final String USERNAME = "Admin";
//    private static final String PASSWORD = "admin123";
    //Cách 2
    private  static  final  By  USERNAME_INPUT =By.name("username");
    //By.cssSelector("input[name='username']")
    //By.cssSelector("input[placeholder='Username']")

    private static  final By USER_DROPDOWN = By.xpath("//li[@class='oxd-userdropdown']");
    private static final By LOGOUT_LINK=By.linkText("Logout");
    //By.classNmae("oxd-userdropdown")
    //By.cssSelecotr("li.oxd")
    //By.xpath("a[@href=]")

    //define các elements , locators
//    private static final By USERNAME_INPUT = By.xpath("//input[@name='username']");
//    private static final By PASSWORD_INPUT = By.xpath("//input[@name='password']");
    private  static  final By PASSWORD_INPUT = By.cssSelector("input[name='password']");
    private static final By LOGIN_BTN =  By.xpath("//button[@type='submit']");
    //By.cssSelector("buton[type='submit']")

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
        driver.get(ConfigUtils.getLoginUrl());
//        Thread.sleep(15000);
        //sẽ đợi đến khi username input xuất hiện trên giao diện
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
//        B2: tim element input username va fill username vao
        WebElement usernameField = driver.findElement(USERNAME_INPUT);
        usernameField.sendKeys(ConfigUtils.getUsername());
        Thread.sleep(2000);

//        B3: tim element input password va fill password
        WebElement passwordField = driver.findElement(PASSWORD_INPUT);
        passwordField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);
//        B4: tim element button Login va click vao button
        WebElement loginBtn = driver.findElement(LOGIN_BTN);
        loginBtn.click();
//        Thread.sleep(5000);
        //đợi có điều kiện :đợi đến khi xuất hiện url có chứa  "dashboard"
        wait.until(ExpectedConditions.urlContains("dashboard"));

//        B5: verify ket qua sau khi thao tac login
//        TH1: kiem tra URL co chua "dashboard" khong
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("dashboard"),
                "URL phải chứa 'dashboard' sau khi login"
        );
    }
    @Test(description = "Test Logout")
    public void testLogout() throws  InterruptedException {
        //        B1: truy cap trang web Login
        driver.get(ConfigUtils.getLoginUrl());
//        Thread.sleep(15000);
//        doi co dieu kien: doi den khi username input xuat hiện trên DOM (giao diện)
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));

//        B2: tim element input username va fill username vao
        WebElement usernameField = driver.findElement(USERNAME_INPUT);
        usernameField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);

//        B3: tim element input password va fill password
        WebElement passwordField = driver.findElement(PASSWORD_INPUT);
        passwordField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);
//        B4: tim element button Login va click vao button
        WebElement loginBtn = driver.findElement(LOGIN_BTN);
        loginBtn.click();
//        Thread.sleep(10000);
//        Đợi có điều kiện: Đợi đến khi xuất hiện url có chứa "dashboard"
        wait.until(ExpectedConditions.urlContains("dashboard"));

//        Logout
//        Click vafo avatar để mở dropdown
        wait.until(ExpectedConditions.elementToBeClickable(USER_DROPDOWN)).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK)).click();

//        Đợi về trang login
        wait.until(ExpectedConditions.urlContains("auth/login"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("auth/login"),
                "Sau khi logout phải trở về trang Login"
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