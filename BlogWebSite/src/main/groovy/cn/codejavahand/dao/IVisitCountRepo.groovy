package cn.codejavahand.dao

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
interface IVisitCountRepo extends ICacheCleaner {
    boolean setArticleVisitCount(String articleId, int count)

    int getArticleVisitCount(String articleId)


}