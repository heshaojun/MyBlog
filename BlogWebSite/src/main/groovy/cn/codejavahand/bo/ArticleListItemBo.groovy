package cn.codejavahand.bo
/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
class ArticleListItemBo {
    /*文章标题*/
    String title
    /*文章摘要*/
    String summery
    /*文章类型 blog 、note*/
    String type
    /*文章编写时间*/
    String time
    /*文章ID*/
    String id
    /*文章标签*/
    String articleLabel
    /*文章分类标签*/
    List<String> classifyLabels = new ArrayList<>()
    /*访问数量*/
    Integer visit
    /*评论数量*/
    Integer comment

}
