import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YKL on 2018/4/20.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
public class DESTest {

    public static void main(String args[]) throws Exception {

//        String str = "{[{\"datetime\":\"1524037938000\",\"gid\":\"test_017\",\"data\":\"{\\n          \\\"gid\\\": \\\"EWQTREBVCBSDGBDFSFSADFGHSDA\\\",\\n          \\\"data\\\": '''{\\\"star\\\":\\\"刘亦菲\\\",\\\"company\\\":\\\"宝莱坞\\\"}''',\\n          \\\"type\\\": \\\"video\\\",\\n          \\\"content\\\": \\\"这是杨昆霖早期的成名作品。\\\",\\n          \\\"mark\\\": '''{\\\"star\\\":\\\"张曼玉\\\",\\\"company\\\":\\\"坏莱坞\\\"}'''\\n        }\",\"type\":\"test\",\"content\":\"诸神摧毁了上古之神的城堡，并把藏在城堡中的四个邪恶生物囚禁在远离地面的地下监狱中。元素生物失去了上古之神的力量，无法在实体世界中保持自己的形态，很快就纷纷化为乌有，溶入了大地。整个世界顿时恢复了一派和谐的景象。泰坦看到危机消除，就开始了他们的工作。\",\"mark\":\"{\\\"star\\\":\\\"张曼玉\\\",\\\"company\\\":\\\"坏莱坞\\\"}\"}," +
//                "{\"datetime\":\"1524037938000\",\"gid\":\"test_017\",\"data\":\"{\\n          \\\"gid\\\": \\\"EWQTREBVCBSDGBDFSFSADFGHSDA\\\",\\n          \\\"data\\\": '''{\\\"star\\\":\\\"刘亦菲\\\",\\\"company\\\":\\\"宝莱坞\\\"}''',\\n          \\\"type\\\": \\\"video\\\",\\n          \\\"content\\\": \\\"这是杨昆霖早期的成名作品。\\\",\\n          \\\"mark\\\": '''{\\\"star\\\":\\\"张曼玉\\\",\\\"company\\\":\\\"坏莱坞\\\"}'''\\n        }\",\"type\":\"test\",\"content\":\"诸神摧毁了上古之神的城堡，并把藏在城堡中的四个邪恶生物囚禁在远离地面的地下监狱中。元素生物失去了上古之神的力量，无法在实体世界中保持自己的形态，很快就纷纷化为乌有，溶入了大地。整个世界顿时恢复了一派和谐的景象。泰坦看到危机消除，就开始了他们的工作。\",\"mark\":\"{\\\"star\\\":\\\"张曼玉\\\",\\\"company\\\":\\\"坏莱坞\\\"}\"}]}";

        Map<String, Object> map = new HashMap<>();

        map.put("name", "yang");

        String str = JSONObject.toJSONString(map);

        List<String> list = new ArrayList<>();

        list.add(str);
        list.add(str);

        System.out.println(list.toString());

        String arr = JSONObject.toJSONString(list.toArray());

        JSONArray array = JSONObject.parseArray(arr);


        System.out.println(array.get(0).toString());

//        System.out.println(new String(DESUtils.encrypt(str.getBytes(), "95880288")));
//        System.out.println(new String(DESUtils.decrypt(DESUtils.encrypt(str.getBytes(), "95880288"), "95880288")));
    }

}
