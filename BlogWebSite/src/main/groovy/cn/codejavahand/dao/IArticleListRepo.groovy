package cn.codejavahand.dao

import cn.codejavahand.dao.po.ArticleInfoPo

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO 文章群数据查询操作接口
 */
interface IArticleListRepo extends ICacheCleaner {
    /**
     * 获取全部文章数据并对数据进行缓存通过name= allArticleInfo进行缓存
     * @return
     */
    List<ArticleInfoPo> getAllArticle()
    /**
     * 通过文章类型进行数据查询，获取相同类型所有文章数据
     * @param scope
     * @return
     */
    List<ArticleInfoPo> getAllArticleByScope(String scope)

    /**
     * 通过文章标签查询所有文章数据
     * @param articleLabel 文章标签
     * @return
     */
    List<ArticleInfoPo> getAllArticleByLabel(String articleLabel)

    /**
     * 通过文章分类标签获取所有文章数据
     * @param classify
     * @return
     */
    List<ArticleInfoPo> getAllArticleByClassify(String classify)

    /**
     * 通过文章标题关键字获取全部文章数据
     * @param keyWord
     * @return
     */
    List<ArticleInfoPo> getAllArticleByKeyWord(String keyWord)
}