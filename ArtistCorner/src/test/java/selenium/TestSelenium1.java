package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author  Marco Purificato
 */
public class TestSelenium1 {

    @Test
    public void testSeleniumArtworkPricingFormula1(){
        ClassLoader classLoader = TestSelenium1.class.getClassLoader();
        File file = new File(classLoader.getResource("chromedriver_linux64").getFile());
        String driverPath = file.getAbsolutePath();

        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.artismycareer.com/pricing-calculator/");

        driver.findElement(By.xpath("//*[@id=\"height\"]")).sendKeys("20");
        driver.findElement(By.xpath("//*[@id=\"width\"]")).sendKeys("40");
        driver.findElement(By.xpath("//*[@id=\"cpsi\"]")).sendKeys("1");
        driver.findElement(By.xpath("//*[@id=\"cost\"]")).sendKeys("30");

        driver.findElement(By.xpath("//*[@id=\"PopupSignupForm_0\"]/div[2]/div[1]")).click();

        driver.findElement(By.xpath("//*[@id=\"camper1CalculateButton\"]")).click();

        String price = driver.findElement(By.xpath("//*[@id=\"c1\"]")).getText();


        assertEquals("£1660", price);


        driver.close();
    }

}
