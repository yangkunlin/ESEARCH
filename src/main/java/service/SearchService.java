package service;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
public interface SearchService {

    String allFieldSearchWithType(String _INDEX, String _TYPE, String _TYPEVALUE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;
    String allFieldSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;
    String hotFieldSearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception;
    String recommendSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;
    String recommendSearch(String _INDEX, String _TYPE, Object _TYPEORKEYVALUE, Object _RECOMMENDVALUE, int _FROM, int _SIZE, int _FLAG) throws Exception;
    String recommendSearch(String _INDEX, String _TYPE, Object _TYPEVALUE, Object _KEYVALUE, Object _RECOMMENDVALUE, int _FROM, int _SIZE) throws Exception;
    String emptySearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception;

}
