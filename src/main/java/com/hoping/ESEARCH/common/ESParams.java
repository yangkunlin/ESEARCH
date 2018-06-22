package com.hoping.ESEARCH.common;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 * spark：梦想开始的地方
 */
public class ESParams {

    /** elasticsearch集群信息 **/
    public static String _CLUSTERNAME = "elasticsearch.clustername";

    public static String HOST_01 = "elasticsearch.host.01";

    public static String HOST_02 = "elasticsearch.host.02";

    public static String HOST_03 = "elasticsearch.host.03";

    public static String HOST_04 = "elasticsearch.host.04";

    public static String HOST_05 = "elasticsearch.host.05";

    public static String _PORT = "elasticsearch.port";

    public static String ELASTICSEARCH_XPACK = "elasticsearch.xpack";
    /********************************************************************************************/

    /** 测试环境索引库信息 **/
    public static String XIYUAN_INDEX = "trial_xysearch";

    public static String XIYUAN_TYPE = "xiyuan";

    public static String SCREENPLAY_INDEX = "trial_spsearch";

    public static String SCREENPLAY_TYPE = "screenplay";

    public static String HOTWORD_INDEX = "trial_hwsearch";

    public static String HOTWORD_TYPE = "hotword";

    public static String RECOMMEND_INDEX = "trial_rcsearch";

    public static String RECOMMEND_TYPE = "recommend_common";

    public static String BADWORD_INDEX = "trial_bwsearch";

    public static String BADWORD_TYPE = "badword";
    /********************************************************************************************/

    /** 生产环境索引库信息 **/
//    public static String XIYUAN_INDEX = "final_xysearch";
//
//    public static String XIYUAN_TYPE = "xiyuan";
//
//    public static String SCREENPLAY_INDEX = "final_spsearch";
//
//    public static String SCREENPLAY_TYPE = "screenplay";
//
//    public static String HOTWORD_INDEX = "final_hwsearch";
//
//    public static String HOTWORD_TYPE = "hotword";
//
//    public static String RECOMMEND_INDEX = "final_rcsearch";
//
//    public static String RECOMMEND_TYPE = "recommend_common";
//
//    public static String BADWORD_INDEX = "final_bwsearch";
//
//    public static String BADWORD_TYPE = "badword";
    /********************************************************************************************/

    /** 坏词库参数 **/
    public static String WORD = "word";
    /********************************************************************************************/

    /** 推荐库参数 **/
    public static String RECOMMENDGID = "recommendGid";

    public static String COSINERECOMMENDTYPE = "1";
    /********************************************************************************************/

    /** 公共参数 **/
    public static String ELASTICSEARCH_ID = "gid";

    public static String FROM = "from";

    public static String SIZE = "size";

    public static int DEFAULT_FROM = 0;

    public static int DEFAULT_SIZE = 10;

    public static String SCORE = "_score";

    public static String TYPE = "type";

    public static String KEY = "key";
    /********************************************************************************************/

    /** 戏缘库参数 **/
    public static String UID = "uid";

    public static String IMEI = "imei";

    public static String MEID = "meid";

    public static String MARK = "mark";

    public static String CONTENT = "content";

    public static String GID = "gid";

    public static String DATA = "data";

    public static String RECOMMEND = "recommend";

    public static String DATETIME = "datetime";
    /********************************************************************************************/

    /** 热词库参数 **/
    public static String HOTKEYNUM = "num";

    public static String HOTKEYTIMES = "times";

    public static String HOTKEYFIELD = "field";
    /********************************************************************************************/



}
