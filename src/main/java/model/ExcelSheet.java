package model;

import java.util.ArrayList;
import java.util.List;

public class ExcelSheet
{
    private String name;
    private List<String> headers = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();

    public ExcelSheet(String name) {
        this.name = name;
    }

    public List<String> getHeaders()
    {
        return headers;
    }

    public void setHeaders(List<String> headers)
    {
        this.headers = headers;
    }

    public List<List<String>> getRows()
    {
        return rows;
    }

    public void setRows(List<List<String>> rows)
    {
        this.rows = rows;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}