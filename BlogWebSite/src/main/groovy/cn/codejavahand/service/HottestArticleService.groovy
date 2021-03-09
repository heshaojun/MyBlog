package cn.codejavahand.service

import cn.codejavahand.bo.HottestArticleBo
import cn.codejavahand.bo.NewestArticleBo
import cn.codejavahand.common.RestResp
import org.springframework.stereotype.Service

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/09
 * @Description TODO
 */
@Service
class HottestArticleService {
    RestResp doService() {
        tempData()
    }

    private RestResp tempData() {
        RestResp resp = new RestResp()
        List<NewestArticleBo> list = new ArrayList<>()
        list.with {
            add new HottestArticleBo(id: "1243443", title: "最新文章标题")
            add new HottestArticleBo(id: "1243443", title: "最新文章标题2")

        }

        resp.with {
            code = 200
            msg = "ok"
            data = list
        }
        resp
    }

}
