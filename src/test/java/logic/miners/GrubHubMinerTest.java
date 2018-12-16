package logic.miners;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrubHubMinerTest
{
    GrubHubMiner miner = new GrubHubMiner();

    @Test
    public void mineURLS_shouldReturnRestaurantsURLS() {
        //System.out.println(miner.mineURLS());

        miner.mineModels(Lists.newArrayList(
                "https://www.grubhub.com/restaurant/the-daily-creative-food-co-959-west-ave-9-miami-beach/362756"));
    }
}