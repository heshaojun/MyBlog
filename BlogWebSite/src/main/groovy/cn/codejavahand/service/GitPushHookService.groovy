package cn.codejavahand.service

import cn.codejavahand.config.SysConfig

import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/15
 * @description TODO
 */
@Service
@Log
class GitPushHookService {
    @Autowired
    private SysConfig sysConfig


    void doService() {
        log.info("接收到git服务器webhook请求，开始拉取数据")
        try {
            log.info("sh ${sysConfig.shellPath}".execute().text)
        } catch (Exception e) {
            e.printStackTrace()
        }
        log.info("拉取git远程仓库数据成功")
    }
}
