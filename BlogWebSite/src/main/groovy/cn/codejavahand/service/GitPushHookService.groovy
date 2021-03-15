package cn.codejavahand.service

import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.ICacheCleaner
import com.alibaba.fastjson.JSONObject
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

/**
 * @author heshaojun* @date 2021/3/15
 * @description TODO
 */
@Service
@Log
class GitPushHookService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private List<ICacheCleaner> cacheCleanerList


    void doService(HttpServletRequest request) {
        log.info("接收到git服务器webhook请求")
        if (!auth(request)) {
            log.info("webhook请求安全认证失败")
            return
        }
        try {
            log.info("开始执行shell脚本拉取文章数据")
            def proc = ["/bin/bash", "${sysConfig.shellPath}"].execute()
            log.info(proc.err.text)
            log.info(proc.in.text)
            log.info("拉取数据成功，开始清除dao层缓存")
            cacheCleanerList.each {
                it.cleanCache()
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        log.info("拉取git远程仓库hook调用完成")

    }

    boolean auth(HttpServletRequest request) {
        boolean result = false
        try {
            if (request.getHeader("Content-Type") == "application/json") {
                log.info("接收到的数据为json")
                byte[] jsonData = org.apache.commons.io.IOUtils.toByteArray request.getInputStream()
                String jsonStr = new String(jsonData)
                log.info("json 数据为：$jsonStr")
                JSONObject json = JSONObject.parseObject(jsonStr)
                if (json.getString("password") == sysConfig.webHookPasswd) result = true
            } else {
                log.info("接收到的数据不为json")
            }
        } catch (Exception e) {
            e.printStackTrace()
            log.info("解析数据异常")
        }

        result
    }
}
