package cn.codejavahand.controller

import cn.codejavahand.common.RestResp
import cn.codejavahand.service.*
import groovy.util.logging.Log
import javafx.geometry.Pos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/08
 * @Description TODO
 */
@RestController
@RequestMapping("rest")
@Log
class RestApiController {
    @Autowired
    private SiteInfoService siteInfoService
    @Autowired
    private NewestArticleService newestArticleService
    @Autowired
    private HottestArticleService hottestArticleService
    @Autowired
    private ClassifyLabelsService classifyLabelsService
    @Autowired
    private ArticleLabelsService articleLabelsService
    @Autowired
    private ArticleDetailService articleDetailService
    @Autowired
    private ArticleCommentsService articleCommentsService
    @Autowired
    private ArticleListService articleListService
    @Autowired
    private GitPushHookService gitPushHookService
    /*网站统计信息*/

    @GetMapping("siteInfo")
    RestResp siteInfo() {
        siteInfoService.doService();
    }
    /*最新文章*/

    @GetMapping("newestArticles")
    RestResp newestArticles() {
        newestArticleService.doService()
    }
    /*热门文章*/

    @GetMapping("hottestArticles")
    RestResp hottestArticles() {
        hottestArticleService.doService()
    }
    /*文章分类标签*/

    @GetMapping("classifyLabels")
    RestResp classifyLabels() {
        classifyLabelsService.doService()
    }
    /*文章标签*/

    @GetMapping("articleLabels")
    RestResp articleLabels() {
        articleLabelsService.doService()
    }

    /*文章详情*/

    @GetMapping("articleDetail")
    RestResp articleDetail(String articleId) {
        articleDetailService.doService articleId
    }
    /*文章评论信息*/

    @GetMapping("comments")
    RestResp articleComments(String articleId) {
        articleCommentsService.doService articleId
    }

    /*文章列表--分页*/

    @GetMapping("articleList")
    RestResp articleList(@RequestParam(name = "scope", defaultValue = "all") String scope, @RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(name = "type", defaultValue = "all") String type, @RequestParam(name = "keyword", defaultValue = "_") String keyword, @RequestParam(name = "order", defaultValue = "time") String order) {
        log.info "request data: scope=$scope, page=$page, pageSize=$pageSize, type=$type, keyword=$keyword, order=$order"
        articleListService.doService(scope, page, pageSize, type, keyword, order)
    }

    @PostMapping("commitComment")
    RestResp commitComment() {

    }

    @PostMapping("getUserInfo")
    RestResp getUserInfo() {
    }

    @PostMapping("gitUpdate")
    void gitUpdate(HttpServletRequest request) {
        gitPushHookService.doService(request)
    }
}
