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
    StringBuffer hotFieldSearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception;
    String recommendSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception;

}
