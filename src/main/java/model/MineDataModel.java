package model;

public class MineDataModel
{
    private String url;
    private String dba;
    private String address;

    public MineDataModel(String url)
    {
        this.url = url;
    }

    @Header(name = "url", index = 1)
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Header(name = "dba", index = 2)
    public String getDba()
    {
        return dba;
    }

    public void setDba(String dba)
    {
        this.dba = dba;
    }

    @Header(name = "address", index = 3)
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "MineDataModel{" +
                "url='" + url + '\'' +
                ", dba='" + dba + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
