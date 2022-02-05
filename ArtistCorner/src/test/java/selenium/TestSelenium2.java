package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TestSelenium2 {
    @Test
    public void testSelenium2(){
        ClassLoader classLoader = TestSelenium2.class.getClassLoader();
        File file = new File(classLoader.getResource("chromedriver_win32.exe").getFile());
        String absolutePath = file.getAbsolutePath();
        System.setProperty("webdriver.chrome.driver",absolutePath);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.convertire-unita.info/calcolatore-di-unita.php");
        driver.findElement(By.xpath("/html/body/div/main/div/div[2]/article/div[2]/form[1]/p/select/option[36]")).click();
        driver.findElement(By.xpath("//*[@id=\"valore\"]")).sendKeys("30000");
        driver.findElement(By.xpath("/html/body/div/main/div/div[2]/article/div[2]/form[2]/p[3]/select/option[18]")).click();
        driver.findElement(By.xpath("/html/body/div/main/div/div[2]/article/div[2]/form[2]/p[3]/select/option[22]")).click();
        driver.findElement(By.xpath("/html/body/div/main/div/div[2]/article/div[2]/form[2]/p[5]/input")).click();
        String result = driver.findElement(By.xpath("/html/body/div/main/div/div[2]/article/div[3]/strong")).getText();
        assertEquals("3,0×10-6 Metro [m]",result);
        driver.close();
    }

}
