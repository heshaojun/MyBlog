package cn.codejavahand.dao.impl

import cn.codejavahand.common.CommConst
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleIdListRepo
import cn.codejavahand.dao.IVisitCountRepo
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO
 */
@Service
@Log
class ArticleVisitRepo implements IVisitCountRepo {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IArticleIdListRepo articleIdListRepo

    @CacheEvict(cacheNames = ["articleVisitCount"], key = "#articleId")
    @CacheEvict(cacheNames = ["allArticleVisitCount"])
    @Override
    boolean setArticleVisitCount(String articleId, int count) {
        return false
    }

    @CachePut(cacheNames = ["articleVisitCount"], key = "#articleId")
    @Override
    int getArticleVisitCount(String articleId) {
        File file = new File("${sysConfig.siteDataStorePath}/${articleId}/${CommConst.VISIT_COUNT_NAME}")
        int count = 0
        if (file.exists()) {
            new FileReader(file).with {
                count = Integer.valueOf(readLines()[0])
            }
        } else {
            new File("${sysConfig.siteDataStorePath}/${articleId}").mkdirs()
        }
        count
    }

    @CachePut(cacheNames = ["allArticleVisitCount"])
    @Override
    int getAllArticleVisitCount() {
        int count = 0
        List<String> articleIdList = articleIdListRepo.getAllArticleList()
        articleIdList.forEach({
            count += getArticleVisitCount(it)
        })
        count
    }


    @Override
    void cleanCache() {
    }

}
