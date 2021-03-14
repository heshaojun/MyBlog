package cn.codejavahand.service

import cn.codejavahand.bo.HottestArticleBo
import cn.codejavahand.bo.NewestArticleBo
import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.IVisitCountRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import com.sun.codemodel.internal.JCatchBlock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/09
 * @Description TODO
 */
@Service
class HottestArticleService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IArticleIdRepo articleIdRepo
    @Autowired
    private IVisitCountRepo visitCountRepo
    @Autowired
    private IArticleInfoRepo articleInfoRepo

    RestResp doService() {
        RestResp resp = [code: 300, msg: "fail"] as RestResp
        try {
            List<String> articleIdList = articleIdRepo.getAllArticleList()
            List<ArticleVisitBo> articleVisitBoList = new ArrayList<>()
            articleIdList.each {
                articleVisitBoList.add([id: "$it", visit: visitCountRepo.getArticleVisitCount("$it")] as ArticleVisitBo)
            }
            articleVisitBoList.sort { v1, v2 -> v2.visit - v1.visit }
            int len = sysConfig.hottestArticleListNum
            if (articleVisitBoList.size() <= sysConfig.hottestArticleListNum) {
                len = articleVisitBoList.size()
            }
            List<HottestArticleBo> data = new ArrayList<>()
            for (int i = 0; i < len; i++) {
                ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById(articleVisitBoList.get(i).id)
                if (articleInfoPo) {
                    data.add([title: articleInfoPo.title, id: articleInfoPo.id] as HottestArticleBo)
                }
            }
            resp = [code: 200, msg: "ok", data: data] as RestResp
        } catch (Exception e) {
            e.printStackTrace()
        }
        resp
    }

    private static class ArticleVisitBo {
        String id
        Integer visit
    }
}
