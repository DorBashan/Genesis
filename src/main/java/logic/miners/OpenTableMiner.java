package logic.miners;

import model.MineDataModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import java.util.ArrayList;
import java.util.List;

public class OpenTableMiner implements Miner<MineDataModel>
{
    private DriverWrapper driverWrapper = new DriverWrapper();
    private static final String OPENTABLE_URL = "https://www.opentable.com/";
    private static final int MAX = 200;

    @Override
    public List<String> mineURLS()
    {
        List<String> urls = new ArrayList<>();

        driverWrapper.site(OPENTABLE_URL)
                .findElement(By.xpath("//a[contains(@href, '/miami-restaurant')][@class='app__LocationBoxLink-fosldn-1 hGIHQL']"))
                .click()
                .waitFor(5)
                .findElement(By.xpath("//a[@class='view-all-link--rush-2383']"))
                .click();

        boolean canClick = true;
        while (canClick) {
            urls.addAll(driverWrapper
                            .findElements(By.xpath("//a[@class='rest-row-name rest-name ']"))
                            .mapElementsTo(a->a.getAttribute("href")));

            if (urls.size() > MAX) {
                return urls;
            }

            try {
                driverWrapper
                        .findElement(By.xpath("//li[@class='pagination-li pagination-arrow pagination-arrow-next pagination-border-arrow-next']"))
                        .click().waitFor(5);
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

                model.setDba(driverWrapper.findElement(By.xpath("//h1[@itemprop='name']")).getCurrentElement().getText());
                model.setAddress(driverWrapper.findElement(By.xpath("//div[@itemprop='address']")).getCurrentElement().getText());

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
