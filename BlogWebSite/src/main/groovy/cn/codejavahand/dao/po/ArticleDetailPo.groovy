package cn.codejavahand.dao.po

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO 文章详情持久化对象
 */
class ArticleDetailPo extends ArticleInfoPo {

    /*上一篇文章*/
    Child pre
    /*下一篇文章*/
    Child next


    static class Child {
        String title
        String id
    }
}
