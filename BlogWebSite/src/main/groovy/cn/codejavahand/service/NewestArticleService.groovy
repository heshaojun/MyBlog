package cn.codejavahand.service

import cn.codejavahand.bo.NewestArticleBo
import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.ICacheCleaner
import cn.codejavahand.dao.po.ArticleInfoPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/09
 * @Description TODO
 */
@Service
class NewestArticleService implements ICacheCleaner {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IArticleIdRepo articleIdRepo
    @Autowired
    private IArticleInfoRepo articleInfoRepo

    RestResp doService() {
        RestResp resp = [code: 300, msg: "fail"] as RestResp
        try {
            List<ArticleInfoPo> articleInfoPoTemList = getNewestArticles()
            articleInfoPoTemList.each {
                data.add([title: it.title, id: it.id] as NewestArticleBo)
            }
            resp = [code: 200, msg: "ok", data: data] as RestResp
        } catch (Exception e) {
            e.printStackTrace()
        }
        resp
    }

    @Cacheable(cacheNames = ["NewestArticleBoList"])
    List<NewestArticleBo> getNewestArticles() {
        List<String> articleIdList = articleIdRepo.getAllArticleList()
        List<ArticleInfoPo> articleInfoPoList = new ArrayList<>()
        List<ArticleInfoPo> articleInfoPoTemList = new ArrayList<>()
        List<NewestArticleBo> data = new ArrayList<>()
        articleIdList.each {
            ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById("$it")
            if (articleInfoPo) {
                articleInfoPoList.add articleInfoPo
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        articleInfoPoList.sort { t1, t2 -> dateFormat.parse(t2.time).getTime() - dateFormat.parse(t1.time).getTime() }
        if (articleInfoPoList) {
            if (articleInfoPoList.size() <= sysConfig.newestArticleListNum) {
                articleInfoPoTemList = articleInfoPoList
            } else {
                articleInfoPoTemList = articleInfoPoList.subList(0, sysConfig.newestArticleListNum)
            }
        }
        articleInfoPoTemList
    }

    @CacheEvict(cacheNames = ["NewestArticleBoList"])
    @Override
    void cleanCache() {

    }
}
