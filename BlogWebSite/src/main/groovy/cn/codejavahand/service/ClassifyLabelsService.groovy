package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import org.springframework.stereotype.Service

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/09
 * @Description TODO
 */
@Service
class ClassifyLabelsService {
    RestResp doService() {
        tempData()
    }

    private RestResp tempData() {
        RestResp resp = new RestResp()
        resp.with {
            code = 200
            msg = "ok"
            data = ["java", "groovy", "python"]
        }
        resp
    }
}
