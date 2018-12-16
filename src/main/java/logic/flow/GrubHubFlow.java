package logic.flow;

import export.ExcelExporter;
import export.ExcelTransformer;
import logic.miners.GrubHubMiner;
import model.MineDataModel;

import java.io.IOException;
import java.util.List;

public class GrubHubFlow
{
    private final GrubHubMiner miner = new GrubHubMiner();
    private final ExcelExporter exporter = new ExcelExporter();
    private final ExcelTransformer<MineDataModel> transformer = new ExcelTransformer<>(MineDataModel.class);

    public void start() throws IOException
    {
        List<String> urls = miner.mineURLS();
        exporter.export(transformer.transformURLS("GrubHub", urls));

        List<MineDataModel> models = miner.mineModels(urls);
        exporter.export(transformer.transformModels("GrubHub", models));
    }
}
