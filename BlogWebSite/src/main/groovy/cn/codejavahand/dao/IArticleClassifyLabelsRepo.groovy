package cn.codejavahand.dao

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO 文章分类标签数据操作接口
 */
interface IArticleClassifyLabelsRepo extends ICacheCleaner {
    List<String> getAllClassifyLabels()

}