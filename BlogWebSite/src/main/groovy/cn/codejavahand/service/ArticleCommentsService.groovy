package cn.codejavahand.service

import cn.codejavahand.bo.ArticleCommentBo
import cn.codejavahand.common.RestResp
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
@Service
class ArticleCommentsService {
    RestResp doService(String articleId) {
        tempData()
    }

    private RestResp tempData() {

        [
                code: 200,
                msg : "ok",
                data:
                        [
                                [name: "用户名称", type: "wechat", comment: "这个文章写的一般般吧", time: "2021-02-23 12:00:12"] as ArticleCommentBo,
                                [name: "用户名称", type: "wechat", comment: "这个文章写的一般般吧", time: "2021-02-23 12:00:12"] as ArticleCommentBo,
                                [name: "用户名称", type: "wechat", comment: "这个文章写的一般般吧", time: "2021-02-23 12:00:12"] as ArticleCommentBo,
                                [name: "用户名称", type: "wechat", comment: "这个文章写的一般般吧", time: "2021-02-23 12:00:12"] as ArticleCommentBo
                        ] as ArrayList<ArticleCommentBo>
        ] as RestResp
    }

}
