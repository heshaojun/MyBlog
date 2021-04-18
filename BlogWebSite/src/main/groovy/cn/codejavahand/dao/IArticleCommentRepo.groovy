package cn.codejavahand.dao

import cn.codejavahand.dao.po.ArticleCommentPo

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO 文章评论数据操作接口
 */
interface IArticleCommentRepo extends ICacheCleaner {
    /**
     * 通过文章id获取文章评论数据
     * @param articleId 文章id
     * @param num 最大评论数
     * @return
     */
    List<ArticleCommentPo> getCommentsByArticleId(String articleId)

    /**
     *  添加文章评论数据
     * @param articleId
     * @param ArticleCommentPo
     * @return
     */
    boolean addArticleComment(String articleId, ArticleCommentPo articleCommentPo)

    /**
     * 判断但前邮件评价数是否上限
     *
     */
    boolean beyondLimit(String articleId, String email, int limit)


}