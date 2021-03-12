package cn.codejavahand.dao

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO 文章标签数据操作接口
 */
interface IArticleLabelRepo extends ICacheCleaner {
    List<String> getArticleLabels()
}