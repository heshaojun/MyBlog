package cn.codejavahand.controller

import cn.codejavahand.common.RestResp
import cn.codejavahand.service.ClassifyLabelsService
import cn.codejavahand.service.HottestArticleService
import cn.codejavahand.service.NewestArticleService
import cn.codejavahand.service.SiteInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/08
 * @Description TODO
 */
@RestController
@RequestMapping("rest")
class RestApiController {
    @Autowired
    private SiteInfoService siteInfoService
    @Autowired
    private NewestArticleService newestArticleService
    @Autowired
    private HottestArticleService hottestArticleService
    @Autowired
    private ClassifyLabelsService classifyLabelsService

    @GetMapping("siteInfo")
    RestResp siteInfo() {
        siteInfoService.doService();
    }

    @GetMapping("newestArticles")
    RestResp newestArticles() {
        newestArticleService.doService()
    }

    @GetMapping("hottestArticles")
    RestResp hottestArticles() {
        hottestArticleService.doService()
    }

    @GetMapping("classifyLabels")
    RestResp classifyLabels() {
        classifyLabelsService.doService()
    }
}
