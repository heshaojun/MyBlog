package cn.codejavahand.dao

import cn.codejavahand.dao.po.ArticleDetailPo

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
interface IArticleDetailRepo extends ICacheCleaner {
    ArticleDetailPo getArticleDetailById(String articleId)
}