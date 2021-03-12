package cn.codejavahand.dao.impl

import cn.codejavahand.common.CommConst
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleDetailRepo
import cn.codejavahand.dao.po.ArticleDetailPo
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
        File root = [pathname: "${sysConfig.articleStoragePath}/${CommConst.BLOG_ARTICLE_PATH}"] as File
        File dataFile = [pathname: "${sysConfig.articleStoragePath}/${CommConst.BLOG_ARTICLE_PATH}/${CommConst.ARTICLE_DATA_NAME}"]
        File articleFile = [pathname: "${sysConfig.articleStoragePath}/${CommConst.BLOG_ARTICLE_PATH}/${CommConst.ARTICLE_NAME}"]
        if (root.exists() && root.isDirectory() && dataFile.exists() && dataFile.isFile() && articleFile.exists() && articleFile.isFile()) {
        } else {
            root = [pathname: "${sysConfig.articleStoragePath}/${CommConst.NOTE_ARTICLE_PATH}"] as File
            dataFile = [pathname: "${sysConfig.articleStoragePath}/${CommConst.NOTE_ARTICLE_PATH}/${CommConst.ARTICLE_DATA_NAME}"]
            articleFile = [pathname: "${sysConfig.articleStoragePath}/${CommConst.NOTE_ARTICLE_PATH}/${CommConst.ARTICLE_NAME}"]
            if (root.exists() && root.isDirectory() && dataFile.exists() && dataFile.isFile() && articleFile.exists() && articleFile.isFile()) {
            } else {
                return null
            }
        }
        new FileReader(dataFile).with {
            
        }
    }

    @CacheEvict(cacheNames = ["articleDetail"])
    @Override
    void cleanCache() {}


}
