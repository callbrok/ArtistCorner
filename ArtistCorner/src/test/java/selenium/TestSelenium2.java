package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TestSelenium2 {
    public static void main(String[] args){
        ClassLoader classLoader = TestSelenium2.class.getClassLoader();
        File file = new File(classLoader.getResource("chromedriver_win32.exe").getFile());
        String absolutePath = file.getAbsolutePath();
        System.setProperty("webdriver.chrome.driver",absolutePath);
        WebDriver driver = new ChromeDriver();
        driver.get("https://github.com/callbrok/ArtistCorner");
        driver.findElement(By.xpath("//*[@id=\"repo-content-pjax-container\"]/div/div[2]/div[2]/div/div[5]/div/h2/a")).click();
        String contributor1 = driver.findElement(By.xpath("//*[@id=\"contributors\"]/ol/li[1]/span/h3/a[2]")).getText();
        String contributor2 = driver.findElement(By.xpath("//*[@id=\"contributors\"]/ol/li[2]/span/h3/a[2]")).getText();
        String Contributors = contributor1+ " and "+contributor2;
        assertEquals("callbrok and Alessio1100",Contributors);
        driver.close();
    }

}
