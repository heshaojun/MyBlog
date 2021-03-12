package cn.codejavahand.dao

import cn.codejavahand.dao.po.ArticleCommentPo

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
interface IArticleCommentRepo extends ICacheCleaner {
    List<ArticleCommentPo> getCommentsByArticleId(String articleId)

    boolean addArticleComment(String articleId, ArticleCommentPo)

}