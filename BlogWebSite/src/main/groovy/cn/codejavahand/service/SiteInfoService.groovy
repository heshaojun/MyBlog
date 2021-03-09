package cn.codejavahand.service

import cn.codejavahand.bo.SiteInfoBo
import cn.codejavahand.common.RestResp
import org.springframework.stereotype.Service

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/09
 * @Description TODO
 */
@Service
class SiteInfoService {
    RestResp doService() {
        tempData()
    }

    private RestResp tempData() {
        RestResp resp = new RestResp()
        SiteInfoBo siteInfoBo = new SiteInfoBo()
        siteInfoBo.with {
            visit = "10万+"
            article = "1000"
            blog = "500"
            note = "500"
            msg = "3000"
        }
        resp.with {
            code = 200
            msg = "ok"
            data = siteInfoBo
        }
        resp
    }
}
