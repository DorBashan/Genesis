package logic.miners;

import model.MineDataModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import java.util.ArrayList;
import java.util.List;

public class GrubHubMiner implements Miner<MineDataModel>
{
    private DriverWrapper driverWrapper = new DriverWrapper();
    private static final String GRUBHUB_URL = "https://www.grubhub.com/";

    @Override
    public List<String> mineURLS()
    {
        List<String> urls = new ArrayList<>();

        driverWrapper.site(GRUBHUB_URL)
                .findElement(By.xpath("//input[@type='search']"))
                .sendKeys("Miami Beach, FL, USA")
                .findElement(By.id("ghs-startOrder-searchBtn"))
                .click()
                .waitFor(5)
                .findElement(By.xpath("//label[contains(@for, 'Now')]"))
                .click()
                .waitFor(5);

        boolean canClick = true;
        while (canClick) {
            urls.addAll(new ArrayList<>(driverWrapper
                            .findElements(By.xpath("//a[contains(@href,'/restaurant/')]"))
                            .mapElementsTo(webElement -> webElement.getAttribute("href"))));

            try {
                driverWrapper.findElement(By.xpath("//a[@aria-label='Next']")).click().waitFor(5);
            }
            catch (WebDriverException e) {
                canClick = false;
            }
        }

        return urls;
    }

    @Override
    public List<MineDataModel> mineModels(List<String> pagesURLS)
    {
        List<MineDataModel> models = new ArrayList<>();

        pagesURLS.forEach(url-> {
            try {
                MineDataModel model = new MineDataModel(url);

                driverWrapper.site(url).waitFor(5);

                model.setAddress(driverWrapper
                                .findElement(By.xpath("//a[@class='restaurantAbout-info-address u-line-bottom u-line--thin u-line--light']"))
                                .getCurrentElement().getText().replaceAll("\n", " "));
                model.setDba(driverWrapper.findElement(By.xpath("//h1[@itemprop='name']")).getCurrentElement().getText());

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