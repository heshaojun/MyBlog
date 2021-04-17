package cn.codejavahand.service


import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.po.ArticleCommentPo
import cn.codejavahand.dao.po.ArticleInfoPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.mail.Session
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
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
    @Autowired
    private IArticleInfoRepo articleInfoRepo

    RestResp getComments(String articleId) {
        RestResp resp = [
                code: 300,
                msg : "fail"
        ] as RestResp
        try {
            ArticleInfoPo articleInfo = articleInfoRepo.getArticleInfoById(articleId)
            if (articleInfo) {
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
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        resp
    }

    RestResp commitComment(HttpServletRequest request) {
        RestResp resp = new RestResp(code: 300, msg: "fail")
        HttpSession session = request.getSession()
        String userName = session.getAttribute("userName")
        if (userName != null && userName != "") {
            String articleId = request.getParameter("articleId")
            String comment = request.getParameter("comment")
            if (articleId != null && articleId != "" && comment != null && comment != "" && comment.length() <= 300) {

            }
        } else {
            resp.msg = "请登录！"
        }


    }

}
