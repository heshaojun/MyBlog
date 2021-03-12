package cn.codejavahand.dao

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO 文章id数据统计接口
 */
interface IArticleIdListRepo extends ICacheCleaner {
    /**
     * 获取所有文章的id
     * @return
     */
    List<String> getAllArticleList()
}
