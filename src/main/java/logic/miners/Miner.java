package logic.miners;

import java.util.List;

public interface Miner<T>
{
    List<String> mineURLS();

    List<T> mineModels(List<String> pagesURLS);
}