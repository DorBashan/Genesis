package logic.miners;

import model.MineDataModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoorDashMiner implements Miner<MineDataModel>
{
    private DriverWrapper driverWrapper = new DriverWrapper();
    private static final String DOORDASH_URL = "https://www.doordash.com/";

    @Override
    public List<String> mineURLS() {
        return driverWrapper
                .site(DOORDASH_URL)
                .findElement(By.tagName("input"))
                .sendKeys("Miami Beach, FL, USA")
                .waitFor(5)
                .findElement(By.xpath("//li"))
                .click()
                .waitFor(5)
                .infinityScrollDown()
                .findElements(By.xpath("//a[contains(@href,'/store/')]"))
                .mapElementsTo(webElement -> webElement.getAttribute("href"))
                .stream().filter(url-> url.startsWith("https://www.doordash.com"))
                .collect(Collectors.toList());
    }

    @Override
    public List<MineDataModel> mineModels(List<String> pagesURLS) {
        List<MineDataModel> models = new ArrayList<>();

        pagesURLS.forEach(url -> {

            try {
                MineDataModel model = new MineDataModel(url);
                List<WebElement> elements =
                        driverWrapper
                                .site(url)
                                .waitFor(5)
                                .findElements(By.xpath("//span[@class='AddressFooter_addressAndBusinessName___3yEYv']"))
                                .getElements();

                model.setDba(elements.get(0).getText());
                model.setAddress(elements.get(1).getText());
                models.add(model);

                System.out.println("Mined Successfully url: " + url);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Couldn't mine url: " + url);
            }
        });

        return models;
    }
}
