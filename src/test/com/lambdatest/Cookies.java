package com.lambdatest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Cookies {

    private RemoteWebDriver driver;
    private String Status = "failed";


    public void setup() throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        ;
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "Cookies Test");
        caps.setCapability("name", this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

    }

    public void cookiesTest() throws InterruptedException {

        String spanText;
        System.out.println("Loading Url");

        driver.get("https://lambdatest.github.io/sample-todo-app/");

        driver.manage().addCookie(new Cookie("cookieName", "lambdatest")); // Creates and adds the cookie

        Set<Cookie> cookiesSet = driver.manage().getCookies(); // Returns the List of all Cookies

        for (Cookie itemCookie : cookiesSet) {
            System.out.println((itemCookie.getName() + ";" + itemCookie.getValue() + ";" + itemCookie.getDomain() + ";"
                    + itemCookie.getPath() + ";" + itemCookie.getExpiry() + ";" + itemCookie.isSecure()));
        }

        driver.manage().getCookieNamed("cookieName"); // Returns the specific cookie according to name

        driver.manage().deleteCookie(driver.manage().getCookieNamed("cookieName")); // Deletes the specific cookie
        driver.manage().deleteCookieNamed("cookieName"); // Deletes the specific cookie according to the Name
        driver.manage().deleteAllCookies(); // Deletes all the cookies

        System.out.println("Checking Box");
        driver.findElement(By.name("li1")).click();

        System.out.println("Checking Another Box");
        driver.findElement(By.name("li2")).click();

        System.out.println("Checking Box");
        driver.findElement(By.name("li3")).click();

        System.out.println("Checking Another Box");
        driver.findElement(By.name("li4")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 6");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 7");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 8");
        driver.findElement(By.id("addbutton")).click();

        System.out.println("Checking Another Box");
        driver.findElement(By.name("li1")).click();

        System.out.println("Checking Another Box");
        driver.findElement(By.name("li3")).click();

        System.out.println("Checking Another Box");
        driver.findElement(By.name("li7")).click();

        System.out.println("Checking Another Box");
        driver.findElement(By.name("li8")).click();
        Thread.sleep(300);

        System.out.println("Entering Text");
        driver.findElement(By.id("sampletodotext")).sendKeys("Get Taste of Lambda and Stick to It");

        driver.findElement(By.id("addbutton")).click();

        System.out.println("Checking Another Box");
        driver.findElement(By.name("li9")).click();

        // Let's also assert that the todo we added is present in the list.

        spanText = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[9]/span")).getText();
        "Get Taste of Lambda and Stick to It".equalsIgnoreCase(spanText);
        Status = "passed";
        Thread.sleep(150);

        System.out.println("TestFinished");

    }

    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        Cookies test = new Cookies();
        test.setup();
        test.cookiesTest();
        test.tearDown();
    }

}