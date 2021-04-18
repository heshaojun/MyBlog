package cn.codejavahand.dao.impl

import cn.codejavahand.common.CommConst
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.po.ArticleCommentPo
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO
 */
@Service
class ArticleCommentRepo implements IArticleCommentRepo {
    @Autowired
    private SysConfig sysConfig

    @Cacheable(cacheNames = ["articleComments"], key = "#articleId")
    @Override
    List<ArticleCommentPo> getCommentsByArticleId(String articleId) {
        List<ArticleCommentPo> commentPos = new ArrayList<>()
        File path = new File("${sysConfig.articleDataStorePath}/${articleId}/${CommConst.ARTICLE_COMMENT_PATH}")
        if (path.exists()) {
            Arrays.asList(path.list([accept: { dir, name -> dir.exists() && name.endsWith(".json") && (new File("${dir.absolutePath}/$name").isFile()) }] as FilenameFilter)).forEach(
                    {
                        new FileReader("${sysConfig.articleDataStorePath}/${articleId}/${CommConst.ARTICLE_COMMENT_PATH}/${it}").with {
                            commentPos.add(JSONObject.parseObject(readLines().join(""), ArticleCommentPo.class))
                        }
                    }
            )
        } else {
            path.mkdirs()
        }
        commentPos
    }

    @CacheEvict(cacheNames = ["articleComments"], key = "#articleId")
    @Override
    boolean addArticleComment(String articleId, ArticleCommentPo articleCommentPo) {
        File file = new File("${sysConfig.articleDataStorePath}/${articleId}/${CommConst.ARTICLE_COMMENT_PATH}/${new Date().getTime()}.json")
        while (file.exists()) {
            file = new File("${sysConfig.articleDataStorePath}/${articleId}/${CommConst.ARTICLE_COMMENT_PATH}/${new Date().getTime()}.json")
        }
        if (file.createNewFile()) {
            String jsonString = JSONObject.toJSONString(articleCommentPo)
            try {
                new FileWriter(file).with {
                    write(jsonString)
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
            return true
        }
        return false
    }

    @Override
    boolean beyondLimit(String articleId, String email, int limit) {
        List<ArticleCommentPo> articleCommentPos = getCommentsByArticleId(articleId)
        int count = 0
        for (ArticleCommentPo po in articleCommentPos) {
            if (po.email == email) {
                count++
            }
        }
        return count >= limit
    }

    @Override
    void cleanCache() {
    }
}
