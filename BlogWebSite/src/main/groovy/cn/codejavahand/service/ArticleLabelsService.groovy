package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
@Service
class ArticleLabelsService {
    RestResp doService() {
        tempData()
    }

    private RestResp tempData() {
        RestResp resp = new RestResp()
        List<String> list = new ArrayList<>()
        list.with {
            add "spring boot"
            add "redis"
            add "docker"
            add "dubbo"
            add "mybatis"
        }
        resp.with {
            code = 200
            msg = "ok"
            data = list
        }
        resp
    }
}
