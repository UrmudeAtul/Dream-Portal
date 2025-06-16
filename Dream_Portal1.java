package Selenium_Firefox;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Dream_Portal1 {

	System.setProperty("webdriver.geckodriver.driver","E:\\Software Testing\\Automation\\Automation Firefox\\geckodriver.exe");
	WebDriver driver = new FirefoxDriver();
	
	try {
        driver.get("https://arjitnigam.github.io/myDreams/");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));

        WebElement myDreamsButton = driver.findElement(By.xpath("//button[contains(text(),'My Dreams')]"));
        myDreamsButton.click();

        Thread.sleep(2000);
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            driver.switchTo().window(window);
            if (driver.getCurrentUrl().contains("dreams-diary.html")) {
                System.out.println("✅ Switched to dreams-diary.html");
                break;
            }
        }

        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));

        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        int rowCount = rows.size();

        if (rowCount == 10) {
            System.out.println("✅ Table contains exactly 10 dream entries.");
        } else {
            System.out.println("❌ Expected 10 dream entries, found: " + rowCount);
        }

        boolean allRowsValid = true;
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
            if (cols.size() != 3) {
                System.out.println("❌ Row " + (i + 1) + " does not have exactly 3 columns.");
                allRowsValid = false;
                continue;
            }

            String dreamName = cols.get(0).getText().trim();
            String daysAgo = cols.get(1).getText().trim();
            String dreamType = cols.get(2).getText().trim();

            if (dreamName.isEmpty() || daysAgo.isEmpty() || dreamType.isEmpty()) {
                System.out.println("❌ Row " + (i + 1) + " has empty column(s).");
                allRowsValid = false;
            }

            if (!dreamType.equalsIgnoreCase("Good") && !dreamType.equalsIgnoreCase("Bad")) {
                System.out.println("❌ Invalid dream type in row " + (i + 1) + ": " + dreamType);
                allRowsValid = false;
            }
        }

        if (allRowsValid) {
            System.out.println("✅ All rows have valid data and dream types.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        driver.quit();
    }
 }
}
