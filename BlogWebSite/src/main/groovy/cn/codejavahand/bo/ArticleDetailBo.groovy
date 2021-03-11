package cn.codejavahand.bo

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
class ArticleDetailBo {
    String title
    String summery
    String type
    String time
    String visit
    String comment
    String id
    String context
    Child pre
    Child next

    static class Child {
        String title
        String id
    }
}
