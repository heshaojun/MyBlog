package cn.codejavahand.dao.po

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO 文章评论数据持久化对象
 */
class ArticleCommentPo {
    /*用户名称*/
    String name
    /*用户类型（微信|github|qq）*/
    String type
    /*文章评论文本*/
    String comment
    /*评论时间*/
    String time
}