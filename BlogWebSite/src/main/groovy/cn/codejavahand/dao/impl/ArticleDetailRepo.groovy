package cn.codejavahand.dao.impl

import cn.codejavahand.common.CommConst
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleDetailRepo
import cn.codejavahand.dao.po.ArticleDetailPo
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
@Service
@Log
class ArticleDetailRepo implements IArticleDetailRepo {
    @Autowired
    private SysConfig sysConfig

    @CachePut(cacheNames = ["articleDetail"], key = "#articleId")
    @Override
    ArticleDetailPo getArticleDetailById(String articleId) {
        ArticleDetailPo articleDetailPo = new ArticleDetailPo()
        File file = new File("${sysConfig.articleStoragePath}/${articleId}.json")
        if (file.exists() && file.isFile()) {
            new FileReader(file).with {
                articleDetailPo = JSONObject.parseObject(readLines().join(""), ArticleDetailPo.class)
            }
            if (articleDetailPo.articleLabel) {
                Map<String, ArticleDetailPo.Child> relevantArticleMap = getRelevantArticle articleDetailPo.articleLabel, articleId
                articleDetailPo.pre = relevantArticleMap.get("pre")
                articleDetailPo.next = relevantArticleMap.get("next")
            }
        }
        articleDetailPo
    }

    /**
     * 获取相关的文章，通过文章标签
     * @param articleLabel
     * @param articleId
     * @return
     */
    private Map<String, ArticleDetailPo.Child> getRelevantArticle(String articleLabel, String articleId) {
        return null
    }

    
    @CacheEvict(cacheNames = ["articleDetail"])
    @Override
    void cleanCache() {}


}
