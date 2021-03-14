package cn.codejavahand.service

import cn.codejavahand.bo.ArticleCommentBo
import cn.codejavahand.common.CommConst
import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.po.ArticleCommentPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.lang.reflect.Array
import java.text.SimpleDateFormat

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
@Service
class ArticleCommentsService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IArticleCommentRepo articleCommentRepo


    RestResp doService(String articleId) {
        RestResp resp = [
                code: 300,
                msg : "fail"
        ] as RestResp
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            List<ArticleCommentPo> data = new ArrayList<>()
            List<ArticleCommentPo> articleCommentPos = articleCommentRepo.getCommentsByArticleId(articleId)
            if (articleCommentPos && articleCommentPos.size() > 0) {
                articleCommentPos.sort { t1, t2 -> dateFormat.parse(t2.time).getTime() - dateFormat.parse(t1.time).getTime() }
                if (articleCommentPos.size() <= sysConfig.commentsListNum) {
                    data = articleCommentPos
                } else {
                    data = articleCommentPos.subList(0, sysConfig.commentsListNum)
                }
            }
            resp = [code: 200, msg: "ok", data: data] as RestResp
        } catch (Exception e) {
            e.printStackTrace()
        }
        resp
    }


}