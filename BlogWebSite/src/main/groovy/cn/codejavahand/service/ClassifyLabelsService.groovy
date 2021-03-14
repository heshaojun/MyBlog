package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/09
 * @Description TODO
 */
@Service
class ClassifyLabelsService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IArticleIdRepo articleIdRepo
    @Autowired
    private IArticleInfoRepo articleInfoRepo

    RestResp doService() {
        RestResp resp = [code: 300, msg: "fail"] as RestResp
        try {
            Set<String> classifyLabels = new HashSet<>()
            List<String> articleIdList = articleIdRepo.getAllArticleList()
            articleIdList.each {
                ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById("$it")
                classifyLabels.addAll(articleInfoPo.classifyLabels)
            }
            resp = [code: 200, msg: "ok", data: classifyLabels] as RestResp
        } catch (Exception e) {
            e.printStackTrace()
        }
        resp
    }
}
