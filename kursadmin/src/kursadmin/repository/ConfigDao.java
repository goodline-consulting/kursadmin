package kursadmin.repository;
import java.util.List;
import kursadmin.domain.Config;

public interface ConfigDao 
{
    public List<Config> getConfigList(String kategori);     
    public Config getConfig(String kategori, String namn);
    public void insertConfig(Config conf);
    public void deleteConfig(Config conf);
    public void updateConfig(Config conf);
    public void insertConfigList(List<Config> list);    
}













