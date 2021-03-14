package cn.codejavahand.service

import cn.codejavahand.bo.ArticleListItemBo
import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.IVisitCountRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import cn.codejavahand.utils.IntegerUtils
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
@Service
class ArticleListService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IArticleInfoRepo articleInfoRepo
    @Autowired
    private IArticleIdRepo articleIdRepo
    @Autowired
    private IVisitCountRepo visitCountRepo
    @Autowired
    private IArticleCommentRepo articleCommentRepo

    RestResp doService(String scope, Integer page, Integer pageSize, String type, String keyword, String order) {
        RestResp resp = [code: 300, msg: "fail"] as RestResp
        try {
            List<ArticleListItemBo> dataList = new ArrayList<>()
            if (scope == "note") {
                dataList = getAllNote()
            } else if (scope == "blog") {
                dataList = getAllBlog()
            } else {
                dataList = getAllArticle()
                scope = "all"
            }
            if (order == "visit") {
                dataList.sort { v1, v2 -> v2.visit - v1.visit }
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                dataList.sort { t1, t2 -> dateFormat.parse(t2.time).getTime() - dateFormat.parse(t1.time).getTime() }
            }
            if (type == "search") {
                if (keyword && keyword != "" && keyword.replaceAll(" ", "") != "" && keyword != "_") {
                    dataList = getArticleByKeyWord(dataList, keyword)
                } else {
                    dataList = new ArrayList<>()
                }
            } else if (type == "classify") {
                if (keyword && keyword != "" && keyword.replaceAll(" ", "") != "" && keyword != "_") {
                    dataList = getArticleByClassifyLabel(dataList, keyword)
                } else {
                    dataList = new ArrayList<>()
                }
            } else if (type == "label") {
                if (keyword && keyword != "" && keyword.replaceAll(" ", "") != "" && keyword != "_") {
                    dataList = getArticleByArticleLabel(dataList, keyword)
                } else {
                    dataList = new ArrayList<>()
                }
            } else if (type != "all") {
                dataList = new ArrayList<>()
            }
            int count = dataList.size()
            if (!(pageSize && pageSize > 0 && pageSize <= 30)) {
                pageSize = 10
            }
            if (page && page > 0) {
                if ((page - 1) * pageSize > count) {
                    page = count / pageSize
                    if (count % pageSize > 0) {
                        page++
                    }
                }
            } else {
                page = 1
            }
            int whole = count / pageSize
            if (count % pageSize > 0) whole++
            int start = (page - 1) * pageSize
            int end = page * pageSize
            if (page * pageSize > count) {
                end = start + count % pageSize
            }
            List<ArticleListItemBo> displayList = dataList.subList(start, end)
            List<ArticlePagedListItemBo> list = new ArrayList<>()
            displayList.each {
                ArticlePagedListItemBo articlePagedListItemBo = [
                        title         : it.title,
                        summery       : it.summery,
                        type          : it.type,
                        time          : it.time,
                        id            : it.id,
                        articleLabel  : it.articleLabel,
                        classifyLabels: it.classifyLabels
                ] as ArticlePagedListItemBo
                Integer visit = it.visit
                Integer comment = it.comment
                use(IntegerUtils) {
                    articlePagedListItemBo.comment = comment.toEasyRead()
                    articlePagedListItemBo.visit = visit.toEasyRead()
                }
                list.add(articlePagedListItemBo)
            }
            ArticlePagedListBo articlePagedListBo = [
                    scope: scope,
                    index: page,
                    whole: whole,
                    count: count,
                    list : list
            ] as ArticlePagedListBo
            resp = [code: 200, msg: "ok", data: articlePagedListBo] as RestResp
        } catch (Exception e) {
            e.printStackTrace()
        }
        resp
    }

    private List<ArticleListItemBo> getAllArticle() {
        List<ArticleListItemBo> result = new ArrayList<>()
        try {
            articleIdRepo.getAllArticleList().each {
                try {
                    ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById("$it")
                    if (articleInfoPo) {
                        Integer visit = visitCountRepo.getArticleVisitCount("$it")
                        Integer comment = articleCommentRepo.getCommentsByArticleId("$it").size()
                        ArticleListItemBo articleListItemBo = JSONObject.toJavaObject JSONObject.toJSON(articleInfoPo), ArticleListItemBo.class
                        articleListItemBo.visit = visit
                        articleListItemBo.comment = comment
                        result.add(articleListItemBo)
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        result
    }

    private List<ArticleListItemBo> getAllBlog() {
        List<ArticleListItemBo> result = new ArrayList<>()
        getAllArticle().each {
            if (it.type == "blog") {
                result.add(it)
            }
        }
        result
    }

    private List<ArticleListItemBo> getAllNote() {
        List<ArticleListItemBo> result = new ArrayList<>()
        getAllArticle().each {
            if (it.type == "note") {
                result.add(it)
            }
        }
        result
    }

    private List<ArticleListItemBo> getArticleByKeyWord(List<ArticleListItemBo> data, String keyWord) {
        List<ArticleListItemBo> result = new ArrayList<>()
        data.each {
            if (it.title.contains(keyWord) || it.summery.contains(keyWord))
                result.add(it)
        }
        result
    }

    private List<ArticleListItemBo> getArticleByArticleLabel(List<ArticleListItemBo> data, String articleLabel) {
        List<ArticleListItemBo> result = new ArrayList<>()
        data.each {
            if (it.articleLabel == articleLabel)
                result.add(it)
        }
        result
    }

    private List<ArticleListItemBo> getArticleByClassifyLabel(List<ArticleListItemBo> data, String classifyLabel) {
        List<ArticleListItemBo> result = new ArrayList<>()
        data.each {
            if (it.classifyLabels.contains(classifyLabel))
                result.add(it)
        }
        result
    }

    private static class ArticlePagedListBo {
        String scope
        Integer index
        Integer whole
        Integer count
        List<ArticlePagedListItemBo> list
    }

    private static class ArticlePagedListItemBo {
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
        String visit
        /*评论数量*/
        String comment
    }
}
