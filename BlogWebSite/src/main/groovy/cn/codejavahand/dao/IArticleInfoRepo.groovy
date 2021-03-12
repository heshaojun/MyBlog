package cn.codejavahand.dao

import cn.codejavahand.dao.po.ArticleInfoPo

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO 文章信息数据操作接口
 */
interface IArticleInfoRepo extends ICacheCleaner {
    /**
     * 通过文章的id查询文章信息数据，使用name=articleInfo key=#articleId进行数据缓存
     * @param articleId
     * @return
     */
    ArticleInfoPo getArticleInfoById(String articleId)
}