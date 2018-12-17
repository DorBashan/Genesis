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

//        miner.mineModels(Lists.newArrayList(
//                "https://www.grubhub.com/restaurant/the-daily-creative-food-co-959-west-ave-9-miami-beach/362756"));

        OpenTableMiner openTableMiner = new OpenTableMiner();
        openTableMiner.mineModels(Lists.newArrayList(
           "https://www.opentable.com/r/trattoria-rosalias-aventura-mall-miami?corrId=acaa6ce4-c194-423e-877d-6326e12ec5a3"
        ));
        //openTableMiner.mineModels(openTableMiner.mineURLS());

    }
}