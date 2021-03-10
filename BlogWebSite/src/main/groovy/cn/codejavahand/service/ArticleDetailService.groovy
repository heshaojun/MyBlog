package cn.codejavahand.service

import cn.codejavahand.bo.ArticleInfoBo
import cn.codejavahand.common.RestResp
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
@Service
class ArticleDetailService {
    RestResp doService(String articleId) {
        tempData()
    }


    private static RestResp tempData() {
        RestResp resp = new RestResp()
        resp.with {
            code = 200
            msg = "ok"
            data = {
                title = "文章标题"
                summery = "文章摘要"
                type = "blog"
                time = "2020-10-30 10:44:23"
                visit = "10万+"
                comment = "100"
            } as ArticleInfoBo
        }
        resp
    }

    public static void main(String[] args) {
        print(tempData())
        new FileWriter('output.txt').withWriter { writer ->
            writer.write('a')
        }
    }
}
