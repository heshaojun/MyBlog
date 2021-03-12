package cn.codejavahand.dao

import cn.codejavahand.dao.po.ArticleDetailPo

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO 文章详情数据操作接口
 */
interface IArticleDetailRepo extends ICacheCleaner {
    /**
     * 通过文章id获取文章详情
     * @param articleId
     * @return
     */
    ArticleDetailPo getArticleDetailById(String articleId)
}