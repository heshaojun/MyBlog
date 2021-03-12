package cn.codejavahand.dao.po

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO 文章实体持久化对象
 */
class ArticleInfoPo {
    /*文章标题*/
    String title
    /*文章摘要*/
    String summery
    /*文章类型 blog 、note*/
    String type
    /*文章编写时间*/
    String time
    /*文章访问量*/
    String visit
    /*文章评论数*/
    String comment
    /*文章ID*/
    String id
    /*文章正文*/
    String context
    /*文章标签*/
    String articleLabel
    /*文章分类标签*/
    List<String> classifyLabels = new ArrayList<>()
}
