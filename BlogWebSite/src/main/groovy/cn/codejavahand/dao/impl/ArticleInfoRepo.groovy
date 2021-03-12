package cn.codejavahand.dao.impl

import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO
 */
@Service
@Log
class ArticleInfoRepo implements IArticleInfoRepo {
    @Autowired
    private SysConfig sysConfig

    @CachePut(cacheNames = ["articleInfo"], key = "#articleId")
    @Override
    ArticleInfoPo getArticleInfoById(String articleId) {
        File file = new File("${sysConfig.articleStoragePath}/${articleId}.json")
        return null
    }

    @Override
    void cleanCache() {

    }
}
