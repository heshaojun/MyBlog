package cn.codejavahand.dao.impl

import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import com.alibaba.fastjson.JSONObject
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
class ArticleInfoRepo implements IArticleInfoRepo {
    @Autowired
    private SysConfig sysConfig

    @Cacheable(cacheNames = ["articleInfo"], key = "#articleId")
    @Override
    ArticleInfoPo getArticleInfoById(String articleId) {
        File file = new File("${sysConfig.articleStoragePath}/${articleId}.json")
        ArticleInfoPo articleInfoPo = null
        try {

            if (file.exists() && file.isFile()) {
                new FileReader(file).with {
                    articleInfoPo = JSONObject.parseObject readLines().join(""), ArticleInfoPo.class
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        articleInfoPo
    }

    @CacheEvict(cacheNames = ["articleInfo"])
    @Override
    void cleanCache() {
    }
}
