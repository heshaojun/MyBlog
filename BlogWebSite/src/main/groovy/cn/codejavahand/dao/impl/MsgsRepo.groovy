package cn.codejavahand.dao.impl

import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IMsgsRepo
import cn.codejavahand.dao.po.MsgPo
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat

/**
 * @author heshaojun* @date 2021/3/16
 * @description TODO
 */
@Service
class MsgsRepo implements IMsgsRepo {
    @Autowired
    private SysConfig sysConfig

    @Cacheable(cacheNames = ["allMsgsPoList"])
    @Override
    List<MsgPo> getAllMsg() {
        List<MsgPo> msgPoList = new ArrayList<>()
        try {
            File root = new File("${sysConfig.siteMsgsDataStorePath}")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            if (root.isDirectory()) {
                File[] files = root.listFiles({ File dir, String name -> name.endsWith(".json") })
                for (File file in files) {
                    try {
                        new FileReader(file).with {
                            try {
                                msgPoList.add(JSONObject.parseObject(readLines().join(""), MsgPo.class))
                            } catch (Exception e) {
                                e.printStackTrace()
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace()
                    }
                }
            }
            msgPoList.sort { t1, t2 -> dateFormat.parse(t2.time).getTime() - dateFormat.parse(t1.time).getTime() }
            return msgPoList
        } catch (Exception e) {
            e.printStackTrace()
        }
        return null
    }

    @CacheEvict(cacheNames = ["allMsgsPoList"])
    @Override
    boolean addMsg(MsgPo msgPo) {
        File file = new File("${sysConfig.siteMsgsDataStorePath}/${new Date().getTime()}/.json")
        while (file.exists()) {
            file = new File("${sysConfig.siteMsgsDataStorePath}/${new Date().getTime()}/.json")
        }
        if (file.createNewFile()) {
            new FileWriter().with {
                write(JSONObject.toJSONString(msgPo))
            }
            return true
        }
        return false
    }

    @Override
    boolean beyondLimit(String email, int limit) {
        List<MsgPo> msgPoList = getAllMsg()
        int count = 0
        msgPoList.forEach({
            if (it.email == email) count++
        })

        return count >= limit
    }

    @CacheEvict(cacheNames = ["allMsgsPoList"])
    @Override
    void cleanCache() {
    }
}
