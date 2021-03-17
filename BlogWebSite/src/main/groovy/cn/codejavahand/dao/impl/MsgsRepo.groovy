package cn.codejavahand.dao.impl

import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IMsgsRepo
import cn.codejavahand.dao.po.MsgPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

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
            if (root.isDirectory()) {
                File[] files = root.listFiles({ File dir, String name -> name.endsWith(".json") })
                for (File file in files) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return null
    }

    @CacheEvict(cacheNames = ["allMsgsPoList"])
    @Override
    void addMsg(MsgPo msgPo) {
    }

    @CacheEvict(cacheNames = ["allMsgsPoList"])
    @Override
    void cleanCache() {
    }
}
