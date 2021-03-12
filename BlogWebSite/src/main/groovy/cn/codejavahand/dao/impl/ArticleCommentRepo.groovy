package cn.codejavahand.dao.impl

import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.po.ArticleCommentPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO
 */
@Service
class ArticleCommentRepo implements IArticleCommentRepo {
    @Autowired
    private SysConfig sysConfig

    @Override
    List<ArticleCommentPo> getCommentsByArticleId(String articleId, int num) {
        return null
    }

    @Override
    boolean addArticleComment(String articleId, ArticleCommentPo articleCommentPo) {
        return false
    }

    @Override
    void cleanCache() {

    }
}
