package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.po.ArticleCommentPo
import cn.codejavahand.dao.po.ArticleInfoPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
    @Autowired
    private IArticleIdRepo articleIdRepo

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
                List<ArticleCommentPo> result = []
                data.forEach({
                    String[] strings = it.email.split("@")
                    int len = "${strings[0]}".length() / 2
                    String prefer = "${strings[0]}".substring(0, len)
                    it.email = "${prefer}**@${strings[1]}"
                })
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
        String email = session.getAttribute("email")
        if (userName != null && userName != "") {
            String articleId = request.getParameter("articleId")
            String comment = request.getParameter("comment")
            if (articleId != null && articleId != "" && comment != null && comment != "" && comment.length() <= 300) {
                ArticleCommentPo po = new ArticleCommentPo()
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                po.name = userName
                po.email = email
                po.comment = comment
                po.time = dateFormat.format(new Date())
                if (!articleIdRepo.getAllArticleList().contains(articleId)) {
                    resp.setMsg("评论的文章不存在！")
                } else if (articleCommentRepo.beyondLimit(articleId, email, sysConfig.commentLimit)) {
                    resp.msg = "你对单前文章的评论数已经超过限制"
                } else if (articleCommentRepo.addArticleComment(articleId, po)) {
                    resp.code = 200
                    resp.setMsg("提交成功")
                } else {
                    resp.setMsg("提交失败")
                }
            } else {
                resp.setMsg("输入信息异常")
            }
        } else {
            resp.setMsg("请登录！")
        }
        resp
    }

}
