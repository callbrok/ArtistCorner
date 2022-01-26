package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.net.URISyntaxException;

public class TestSelenium1 {

    public static void main(String[] args) throws URISyntaxException {
        ClassLoader classLoader = TestSelenium1.class.getClassLoader();
        File file = new File(classLoader.getResource("chromedriver").getFile());
        String absolutePath = file.getAbsolutePath();

        System.setProperty("webdriver.chrome.driver", absolutePath);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.rapidtables.com/convert/temperature/celsius-to-fahrenheit.html");

        driver.findElement(By.xpath("//*[@id=\"x\"]")).sendKeys("30");
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[1]/form/table/tbody/tr[2]/td[2]/button[1]")).click();

        WebElement TxtBoxContent = driver.findElement(By.xpath("//*[@id=\"y\"]"));
        System.out.println("Printing: " + TxtBoxContent.getAttribute("value"));

        driver.close();
    }
}
