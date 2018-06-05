package common;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 * spark：梦想开始的地方
 */
public class ESParams {

    public static String _CLUSTERNAME = "xy_elasticsearch";

    public static String HOST_01 = "bigdata-master01";

    public static String HOST_02 = "bigdata-master02";

    public static String HOST_03 = "bigdata-slave01";

    public static String HOST_04 = "bigdata-slave02";

    public static String HOST_05 = "bigdata-slave03";

    public static int _PORT = 12262;

    public static String ELASTICSEARCH_ID = "gid";

    public static String ELASTICSEARCH_XPACK = "elastic:xy2018E12_27";

//    public static String XIYUAN_INDEX = "trial_xysearch";
//
//    public static String XIYUAN_TYPE = "xiyuan";
//
//    public static String SCREENPLAY_INDEX = "trial_spsearch";
//
//    public static String SCREENPLAY_TYPE = "screenplay";
//
//    public static String HOTWORD_INDEX = "trial_hwsearch";
//
//    public static String HOTWORD_TYPE = "hotword";

    public static String XIYUAN_INDEX = "final_xysearch";

    public static String XIYUAN_TYPE = "xiyuan";

    public static String SCREENPLAY_INDEX = "final_spsearch";

    public static String SCREENPLAY_TYPE = "screenplay";

    public static String HOTWORD_INDEX = "final_hwsearch";

    public static String HOTWORD_TYPE = "hotword";

    public static String UID = "uid";

    public static String IMEI = "imei";

    public static String MEID = "meid";

    public static String MARK = "mark";

    public static String CONTENT = "content";

    public static String GID = "gid";

    public static String DATA = "data";

    public static String FROM = "from";

    public static String SIZE = "size";

    public static int DEFAULT_FROM = 0;

    public static int DEFAULT_SIZE = 10;

    public static String TYPE = "type";

    public static String KEY = "key";

    public static String HOTKEYNUM = "num";

    public static String HOTKEYTIMES = "times";

    public static String HOTKEYFIELD = "field";

    public static String RECOMMEND = "recommend";

    public static String DATETIME = "datetime";

    public static String SCORE = "_score";

    public static String[] REDISCLUSTERHOST = {"bigdata-slave01", "bigdata-slave02", "bigdata-slave03"};

    public static int[] REDISCLUSTERPORT = {7000, 7001};

}
