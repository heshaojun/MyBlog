package cn.codejavahand.service

import cn.codejavahand.bo.ArticleListBo
import cn.codejavahand.common.RestResp
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
@Service
class ArticleListService {
    RestResp doService(String scope, Integer page, Integer pageSize, String type, String keyword, String order) {
        tempData()
    }

    private RestResp tempData() {
        [
                code: 200,
                msg : "ok",
                data: [
                        scope: "all",
                        index: 1,
                        whole: 3,
                        count: 5,
                        list : [
                                [
                                        title  : "文章标题1",
                                        summery: "文章简介1",
                                        type   : "博客",
                                        time   : "2020-02-19 10:20:33",
                                        visit  : "1万+",
                                        comment: "100",
                                        id     : "dsfdsaf34343"
                                ] as ArticleListBo.ArticleInfo,
                                [
                                        title  : "文章标题1",
                                        summery: "文章简介1",
                                        type   : "博客",
                                        time   : "2020-02-19 10:20:33",
                                        visit  : "1万+",
                                        comment: "100",
                                        id     : "dsfdsaf34343"
                                ] as ArticleListBo.ArticleInfo,
                                [
                                        title  : "文章标题1",
                                        summery: "文章简介1",
                                        type   : "博客",
                                        time   : "2020-02-19 10:20:33",
                                        visit  : "1万+",
                                        comment: "100",
                                        id     : "dsfdsaf34343"
                                ] as ArticleListBo.ArticleInfo,
                                [
                                        title  : "文章标题1",
                                        summery: "文章简介1",
                                        type   : "博客",
                                        time   : "2020-02-19 10:20:33",
                                        visit  : "1万+",
                                        comment: "100",
                                        id     : "dsfdsaf34343"
                                ] as ArticleListBo.ArticleInfo,
                                [
                                        title  : "文章标题1",
                                        summery: "文章简介1",
                                        type   : "博客",
                                        time   : "2020-02-19 10:20:33",
                                        visit  : "1万+",
                                        comment: "100",
                                        id     : "dsfdsaf34343"
                                ] as ArticleListBo.ArticleInfo
                        ] as ArrayList<ArticleListBo.ArticleInfo>
                ] as ArticleListBo
        ] as RestResp
    }
}
