package cn.codejavahand.service

import cn.codejavahand.bo.ArticleDetailBo
import cn.codejavahand.common.RestResp
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.IVisitCountRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import com.alibaba.fastjson.JSONObject
import groovy.util.logging.Log
import org.apache.logging.log4j.Level
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODOøØ
 */
@Service
@Log
class ArticleDetailService {
    @Autowired
    private IArticleInfoRepo articleInfoRepo
    @Autowired
    private IArticleIdRepo articleIdRepo
    @Autowired
    private IVisitCountRepo visitCountRepo
    @Autowired
    private IArticleCommentRepo articleCommentRepo
    @Autowired
    private ArticleVisitRecordService articleVisitRecordService

    RestResp doService(String articleId) {
        log.info("获取文章详情数据！")
        RestResp resp = [code: 300, msg: "fail"] as RestResp
        try {
            ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById(articleId)
            if (articleInfoPo) {
                ArticleDetailBo articleDetailBo = JSONObject.parseObject JSONObject.toJSONString(articleInfoPo), ArticleDetailBo.class
                if (articleDetailBo.articleLabel) {
                    Map<String, ArticleDetailBo.Child> relevantArticle = getRelevantArticle(articleDetailBo.articleLabel, articleId)
                    articleDetailBo.pre = relevantArticle.get("pre")
                    articleDetailBo.next = relevantArticle.get("next")
                }
                articleDetailBo.visit = visitCountRepo.getArticleVisitCount(articleId)
                articleDetailBo.comment = articleCommentRepo.getCommentsByArticleId(articleId).size()
                resp = [code: 200, msg: "ok", data: articleDetailBo] as RestResp
                articleVisitRecordService.record(articleId)
                log.info("获取文章详情数据成功！")
            }
        } catch (Exception e) {
            e.printStackTrace()
            log.log Level.ERROR, "获取文章详情异常"
        }
        resp
    }

    /**
     * 通过文章标签获取上一篇和下一篇文章的id
     * @param articleLabel
     * @return
     */
    private Map<String, ArticleDetailBo.Child> getRelevantArticle(String articleLabel, String articleId) {
        List<String> idList = articleIdRepo.getAllArticleList()
        List<ArticleInfoPo> sameLabelArticles = new ArrayList<>()
        Map<String, ArticleDetailBo.Child> result = new HashMap<>()
        result.put("next", null)
        result.put("pre", null)
        idList.forEach({
            ArticleInfoPo articleInfo = articleInfoRepo.getArticleInfoById(it)
            if (articleInfo && articleInfo.articleLabel == articleLabel) sameLabelArticles.add(articleInfo)
        })
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sameLabelArticles.sort { t1, t2 -> dateFormat.parse(t1.time).getTime() - dateFormat.parse(t2.time).getTime() }
        int index = sameLabelArticles.findIndexOf { it.id == articleId }
        if (index >= 0 && index <= sameLabelArticles.size() && sameLabelArticles.size() > 1) {
            if (index - 1 >= 0) {
                ArticleInfoPo pre = sameLabelArticles.get(index - 1)
                result.put("pre", [title: pre.title, id: pre.id] as ArticleDetailBo.Child)
            }
            if (sameLabelArticles.size() - 1 > index) {
                ArticleInfoPo next = sameLabelArticles.get(index + 1)
                result.put("next", [title: next.title, id: next.id] as ArticleDetailBo.Child)
            }
        }
        result
    }

}
