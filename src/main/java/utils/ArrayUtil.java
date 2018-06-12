package utils;

/**
 * Description:
 *
 * @author YKL on 2018/6/12.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class ArrayUtil {

    public static Object[] subArray(int subNum, Object[] array) {
        Object[] subArr = new Object[subNum];
        for (int i = 0; i < subNum; i++) {
            subArr[i] = array[i];
        }
        return subArr;
    }

}
