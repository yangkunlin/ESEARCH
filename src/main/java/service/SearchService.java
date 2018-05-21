package service;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
public interface SearchService {

    public String singleFieldSearch(String _INDEX, String _TYPE, String _QUERYKEY, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;
    public String searchForDel(String _INDEX, String _TYPE, String _QUERYKEY, Object _QUERYVALUE) throws Exception;
    public String allFieldSearchWithType(String _INDEX, String _TYPE, String _TYPEVALUE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;
    public String allFieldSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;
    public StringBuffer hotFieldSearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception;
    public String recommendSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;

}
