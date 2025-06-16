package Selenium_Firefox;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Dream_Portal {
	
public static void main(String[] args) throws Exception {
		
		System.setProperty("webdriver.geckodriver.driver","E:\\Software Testing\\Automation\\Automation Firefox\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		
		try {
            driver.get("https://arjitnigam.github.io/myDreams/");
            driver.manage().window().maximize();

            WebElement loader = driver.findElement(By.id("loading"));  // assuming the loader has id 'loading'
            if (loader.isDisplayed()) {
                System.out.println("✅ Loading animation is visible.");
            }

            long start = System.currentTimeMillis();
          //  wait.until(ExpectedConditions.invisibilityOf(loader));
            long end = System.currentTimeMillis();
            long duration = (end - start) / 1000;
            System.out.println("✅ Loader disappeared after ~" + duration + " seconds.");

            WebElement content = driver.findElement(By.id("main"));
            WebElement myDreamsBtn = driver.findElement(By.xpath("//button[contains(text(),'My Dreams')]"));
            if (content.isDisplayed() && myDreamsBtn.isDisplayed()) {
                System.out.println("✅ Main content and 'My Dreams' button are visible.");
            }

            String mainWindow = driver.getWindowHandle();
            int initialTabs = driver.getWindowHandles().size();

            myDreamsBtn.click();
            Thread.sleep(2000);

            Set<String> tabs = driver.getWindowHandles();
            if (tabs.size() == initialTabs + 2) {
                System.out.println("✅ Two new tabs opened.");
            } else {
                System.out.println("❌ Expected 2 new tabs, found: " + (tabs.size() - initialTabs));
            }

            for (String handle : tabs) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    String currentUrl = driver.getCurrentUrl();
                    if (currentUrl.contains("dreams-diary.html") || currentUrl.contains("dreams-total.html")) {
                        System.out.println("✅ Opened: " + currentUrl);
                    } else {
                        System.out.println("❌ Unexpected tab: " + currentUrl);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            driver.quit();
        }
  }
}