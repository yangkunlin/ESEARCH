package service;

import java.util.List;
import java.util.Map;

/**
 * @author YKL on 2018/4/16.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
public interface IndexService {

    boolean addWithoutID(String _INDEX, String _TYPE, Map<String, Object> _FIELD) throws Exception;

    boolean addWithID(String _INDEX, String _TYPE, Map<String, Object> _FIELD, String _ID) throws Exception;

    boolean bulkAddWithoutID(String _INDEX, String _TYPE, List<Map<String, Object>> _FIELDS) throws Exception;

    boolean bulkAddWithID(String _INDEX, String _TYPE, List<Map<String, Object>> _FIELDS) throws Exception;

    String getWithID(String _INDEX, String _TYPE, String _ID) throws Exception;

    boolean delWithID(String _INDEX, String _TYPE, String _ID) throws Exception;

    boolean updateWithID(String _INDEX, String _TYPE, Map<String, Object> _BODY, String _ID) throws Exception;

//    public void multiGet(String... ids) throws Exception;
//
//    public void bulk(String... ids) throws Exception;
//
//    public void bulkProcesstor(String index, String type, String... ids) throws Exception;


}
