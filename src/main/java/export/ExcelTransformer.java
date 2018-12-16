package export;

import com.google.common.collect.Lists;
import model.ExcelSheet;
import model.Header;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExcelTransformer<T>
{
    private Class<T> clazz = null;

    public ExcelTransformer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ExcelSheet transformModels(String name, List<T> data) {
        ExcelSheet sheet = new ExcelSheet(name + " - Data");
        sheet.setHeaders(transformToHeaders());
        sheet.setRows(transformToRows(data));
        return sheet;
    }

    public ExcelSheet transformURLS(String name, List<String> urls) {
        ExcelSheet excelSheet = new ExcelSheet(name + " - URLS");
        excelSheet.setHeaders(Lists.newArrayList("url"));
        List<List<String>> data = new ArrayList<>();
        urls.forEach(url -> data.add(Lists.newArrayList(url)));
        excelSheet.setRows(data);
        return excelSheet;
    }

    private List<List<String>> transformToRows(List<T> data) {
        List<List<String>> rows = new ArrayList<>();
        data.forEach(entity-> rows.add(transformEntity(entity)));
        return rows;
    }

    private List<String> transformToHeaders() {
        return getSortedHeadersMethodsStream()
                .map(method -> {
                    Header annotation = method.getAnnotation(Header.class);
                    return annotation.name();
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private ArrayList<String> transformEntity(T entity) {
        ArrayList<String> row = new ArrayList<>();

        getSortedHeadersMethodsStream().forEach(m-> {
            try {
                row.add(m.invoke(entity).toString());
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.out.println("Failed to invoke method: " + m.getName() + " of instance: " + entity);
            }
        });

        return row;
    }

    private Stream<Method> getSortedHeadersMethodsStream() {
        return Stream.of(clazz.getMethods()).filter(m->m.getAnnotation(Header.class) != null).sorted(headersCompare());
    }

    private Comparator<Method> headersCompare() {
        return Comparator.comparingInt(o -> o.getAnnotation(Header.class).index());
    }
}