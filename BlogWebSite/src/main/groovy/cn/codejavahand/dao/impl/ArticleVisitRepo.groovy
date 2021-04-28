package cn.codejavahand.dao.impl

import cn.codejavahand.common.CommConst
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IVisitCountRepo
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
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

    @CacheEvict(cacheNames = ["articleVisitCount"], key = "#articleId")
    @Override
    boolean setArticleVisitCount(String articleId, int count) {
        try {
            new FileOutputStream("${sysConfig.articleDataStorePath}/${articleId}/${CommConst.VISIT_COUNT_NAME}", false).with {
                write("${count}".getBytes())
            }
            return true
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
    }

    @Cacheable(cacheNames = ["articleVisitCount"], key = "#articleId")
    @Override
    int getArticleVisitCount(String articleId) {
        int count = 0
        try {
            File file = new File("${sysConfig.articleDataStorePath}/${articleId}/${CommConst.VISIT_COUNT_NAME}")
            if (file.exists()) {
                new FileReader(file).with {
                    String countStr = readLines()[0]
                    if (countStr) {
                        count = Integer.valueOf(countStr)
                    }
                }
            } else {
                new File("${sysConfig.articleDataStorePath}/${articleId}").mkdirs()
                file.createNewFile()
            }
        } catch (Exception e) {
            e.printStackTrace()
            return 0
        }
        count
    }


    @CacheEvict(cacheNames = ["articleVisitCount", "articleVisitCount"], allEntries = true)
    @Override
    void cleanCache() {
    }

}
