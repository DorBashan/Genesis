package logic.miners;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DriverWrapper
{
    // EXAMPLE:
    // Xpath=//tagname[@attribute='value']

    private WebDriver driver = new ChromeDriver();
    private WebElement currentElement;
    private List<WebElement> currentElements;

    public DriverWrapper() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
    }

    public DriverWrapper site(String url) {
        driver.get(url);
        return this;
    }

    public DriverWrapper findElement(By by) {
        currentElement = driver.findElement(by);
        return this;
    }

    public DriverWrapper findElements(By by) {
        currentElements = driver.findElements(by);
        return this;
    }

    public DriverWrapper sendKeys(CharSequence sequence) {
        currentElement.sendKeys(sequence);
        return this;
    }

    public DriverWrapper click() {
        currentElement.click();
        return this;
    }

    public List<WebElement> getElements() {
        return currentElements;
    }

    public WebElement getCurrentElement() {
        return currentElement;
    }

    public <T> List<T> mapElementsTo(Function<WebElement, T> function) {
        return currentElements.stream().map(function).collect(Collectors.toList());
    }

    public DriverWrapper infinityScrollDown() {

        Long nextValue = 0L;
        Long currentValue = 1L;

        while (!currentValue.equals(nextValue)) {
            currentValue = getPageYOffset();

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

            waitFor(2);

            nextValue = getPageYOffset();
        }

        return this;
    }

    private Long getPageYOffset() {
        return (Long) ((JavascriptExecutor) driver).executeScript("return window.pageYOffset;");
    }

    public void close() {
        driver.close();
    }

    public DriverWrapper waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this;
    }
}