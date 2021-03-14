package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
@Service
class ArticleLabelsService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IArticleIdRepo articleIdRepo
    @Autowired
    private IArticleInfoRepo articleInfoRepo

    RestResp doService() {
        RestResp resp = [code: 300, msg: "fail"] as RestResp
        try {
            Set<String> articleLabels = new HashSet<>()
            List<String> articleIdList = articleIdRepo.getAllArticleList()
            articleIdList.each {
                ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById("$it")
                articleLabels.add(articleInfoPo.articleLabel)
            }
            resp = [code: 200, msg: "ok", data: articleLabels] as RestResp
        } catch (Exception e) {
            e.printStackTrace()
        }
        resp
    }

}
