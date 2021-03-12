package cn.codejavahand.dao

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO 文章访问计数数据操作接口
 */
interface IVisitCountRepo extends ICacheCleaner {
    boolean setArticleVisitCount(String articleId, int count)

    int getArticleVisitCount(String articleId)

    int getAllArticleVisitCount()

}