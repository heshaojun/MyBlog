package cn.codejavahand.bo

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
class ArticleListBo {
    String scope = "all"
    int index = 0
    int whole = 0
    int count = 0
    List<ArticleInfo> list

    static class ArticleInfo {
        String title
        String summery
        String type
        String time
        String visit
        String comment
        String id
    }
}
