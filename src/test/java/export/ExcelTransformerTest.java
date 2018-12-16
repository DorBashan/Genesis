package export;

import com.google.common.collect.Lists;
import model.MineDataModel;
import model.ExcelSheet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelTransformerTest {

    ExcelTransformer<MineDataModel> transformer = new ExcelTransformer<>(MineDataModel.class);

    @Test
    public void transformModels_shouldTransform() {
        MineDataModel model = new MineDataModel("www.test.com");
        model.setDba("Restaurant");
        model.setAddress("Test Street");

        ExcelSheet sheet = transformer.transformModels("DoorDash", Lists.newArrayList(model));

        assertTrue(sheet.getName().equals("DoorDash - Data"));
        assertHeaders(sheet);
        assertData(sheet);
    }

    private void assertData(ExcelSheet sheet) {
        assertTrue(sheet.getRows().get(0).get(0).equals("www.test.com"));
        assertTrue(sheet.getRows().get(0).get(1).equals("Restaurant"));
        assertTrue(sheet.getRows().get(0).get(2).equals("Test Street"));
    }

    private void assertHeaders(ExcelSheet sheet) {
        assertTrue(sheet.getHeaders().get(0).equals("url"));
        assertTrue(sheet.getHeaders().get(1).equals("dba"));
        assertTrue(sheet.getHeaders().get(2).equals("address"));
    }

    @Test
    public void transformEmpty_shouldReturnEmpty() {
        ExcelSheet sheet = transformer.transformModels("", Lists.newArrayList());
        assertTrue(sheet.getRows().isEmpty());
    }
}