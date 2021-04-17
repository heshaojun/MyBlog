package cn.codejavahand;

import cn.codejavahand.config.SysConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/04/17
 * @Description TODO
 */
public class ForInTest {
    public static void main(String[] args) {
       /* List<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        for (String str : list) {
            System.out.println(str);
            list.remove(str);
        }*/
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "v1");
        map.put("2", "v2");
        map.put("3", "v3");
        for (Map.Entry<String, String> set : map.entrySet()) {
            System.out.println("key:=" + set.getKey() + "  value=" + set.getValue());

        }
    }
}
