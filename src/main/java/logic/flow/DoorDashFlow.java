package logic.flow;

import com.google.common.collect.Lists;
import export.ExcelExporter;
import export.ExcelTransformer;
import logic.miners.DoorDashMiner;
import model.MineDataModel;
import model.ExcelSheet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoorDashFlow
{
    private final DoorDashMiner miner = new DoorDashMiner();
    private final ExcelExporter exporter = new ExcelExporter();
    private final ExcelTransformer<MineDataModel> transformer = new ExcelTransformer<>(MineDataModel.class);

    public void start() throws IOException {
        List<String> urls = miner.mineURLS();
        exporter.export(transformer.transformURLS("DoorDash", urls));

        List<MineDataModel> models = miner.mineModels(urls);
        exporter.export(transformer.transformModels("DoorDash", models));
    }
}
