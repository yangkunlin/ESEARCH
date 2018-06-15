package service.impl;

import com.alibaba.fastjson.JSON;
import common.RSParams;
import model.RedisResultModel;
import org.springframework.stereotype.Service;
import service.RedisService;
import utils.SortUtil;
import utils.redis.JedisUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 *
 * @author YKL on 2018/6/12.
 * @version 1.0
 *          spark:梦想开始的地方
 */
@Service
public class RedisServiceImpl implements RedisService{


    @Override
    public Object getResult(String type, String dateStr, int num) throws Exception {
        RedisResultModel<String, String> redisResultModelValueIsString = new RedisResultModel<>();
        RedisResultModel<String, Map<String, String>> redisResultModelValueIsMap = new RedisResultModel<>();

        Set<String> stringSet = new HashSet<>();
        Set<String> mapSet = new HashSet<>();

        stringSet.add(RSParams.REDISONLINEKEY);
        stringSet.add(RSParams.REDISLOGINEDONLINEKEY);
        stringSet.add(RSParams.REDISAGAINONLINEKEY);
        stringSet.add(RSParams.REDISAGAINLOGINEDONLINEKEY);
        stringSet.add(RSParams.REDISNEWACTIVATIONKEY);
        stringSet.add(RSParams.REDISNEWUSERKEY);
        stringSet.add(RSParams.REDISNEWIOSKEY);
        stringSet.add(RSParams.REDISCLICKTOTALKEY);
        stringSet.add(RSParams.REDISCLICKUIDKEY);
        stringSet.add(RSParams.REDISUIDKEY);
        stringSet.add(RSParams.REDISCOUNTDEVKEY);
        stringSet.add(RSParams.REDISLEFTNEXTKEY);

        mapSet.add(RSParams.REDISAREAKEY);
        mapSet.add(RSParams.REDISLOGINEDAREAKEY);
        mapSet.add(RSParams.REDISPATHKEY);
        mapSet.add(RSParams.REDISLOGINEDPATHKEY);
        mapSet.add(RSParams.REDISOSKEY);
        mapSet.add(RSParams.REDISLOGINEDOSKEY);
        mapSet.add(RSParams.REDISMODELKEY);
        mapSet.add(RSParams.REDISLOGINEDMODELKEY);
        mapSet.add(RSParams.REDISCHANNELKEY);
        mapSet.add(RSParams.REDISLOGINEDCHANNELKEY);
        mapSet.add(RSParams.REDISSEARCHKEY);
        mapSet.add(RSParams.REDISACTKEY);
        mapSet.add(RSParams.REDISCLICKHOURKEY);
        mapSet.add(RSParams.REDISSTAYKEY);


        if (stringSet.contains(type)) {
            setRedisResultModelByString(dateStr, type, redisResultModelValueIsString);
            return JSON.toJSONString(redisResultModelValueIsString);
        } else if (mapSet.contains(type)) {
            setRedisResultModelByMap(dateStr, type, redisResultModelValueIsMap, num);
            return JSON.toJSONString(redisResultModelValueIsMap);
        } else {
            return null;
        }
    }

    private void setRedisResultModelByMap(String dateStr, String key, RedisResultModel<String, Map<String, String>> redisResultModel, int num) {
        redisResultModel.setKey(key + "_" + dateStr);
        if (JedisUtil.getJedis().exists(key + "_" + dateStr)) {
            redisResultModel.setValue(SortUtil.sortMapByValue(JedisUtil.getJedis().hgetAll(key + "_" + dateStr), num));
        } else {
            redisResultModel.setValue(null);
        }
    }

    private void setRedisResultModelByString(String dateStr, String key, RedisResultModel<String, String> redisResultModel) {
        redisResultModel.setKey(key + "_" + dateStr);
        if (JedisUtil.getJedis().exists(key + "_" + dateStr)) {
            redisResultModel.setValue(JedisUtil.getJedis().get(key + "_" + dateStr));
        } else {
            redisResultModel.setValue(null);
        }
    }

}
