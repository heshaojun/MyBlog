package cn.codejavahand.bo


import cn.codejavahand.dao.po.ArticleInfoPo

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
class ArticleDetailBo extends ArticleInfoPo {
    /*文章访问量*/
    String visit
    /*文章评论数*/
    String comment
    /*上一篇文章*/
    Child pre
    /*下一篇文章*/
    Child next


    static class Child {
        String title
        String id
    }
}
