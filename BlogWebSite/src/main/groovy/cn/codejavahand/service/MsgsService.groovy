package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IMsgsRepo
import cn.codejavahand.dao.po.ArticleCommentPo
import cn.codejavahand.dao.po.MsgPo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import java.text.SimpleDateFormat

/**
 * @author heshaojun* @date 2021/3/16
 * @description TODO
 */
@Service
class MsgsService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private IMsgsRepo msgsRepo

    RestResp getMsgs(Integer page, Integer pageSize) {
        RestResp resp = [code: 300, msg: "fail"] as RestResp
        try {
            List<MsgPo> msgPoList = msgsRepo.getAllMsg()
            if (msgPoList & msgPoList.size() > 0) {
                int whole = msgPoList.size()
                int count = whole / pageSize + (whole % pageSize > 0 ? 1 : 0)
                if (page <= 0) page = 1
                int listStart = 0
                int listEnd = 0
                if (pageSize > count) pageSize = count
                listStart = (page - 1) * pageSize
                if (page * pageSize > whole) {
                    listEnd = whole
                } else {
                    listEnd = pageSize * page
                }
                List<MsgPo> data = msgPoList.subList(listStart, listEnd)
                MsgPagedListBo = msgPoList = new MsgPagedListBo(index: page, whole: whole, count: count, list: data)
                resp.setCode(200)
                resp.setMsg("ok")
                resp.setData(msgPoList)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }

    }

    RestResp commitMsg(HttpServletRequest request) {
        RestResp resp = new RestResp(code: 300, msg: "fail")
        HttpSession session = request.getSession()
        String userName = session.getAttribute("userName")
        String email = session.getAttribute("email")
        if (userName != null && userName != "") {
            String msg = request.getParameter("msg")
            if (msg != null && msg != "" && msg.length() <= 300) {
                MsgPo po = new MsgPo()
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                po.name = userName
                po.email = email
                po.msg = msg
                po.time = dateFormat.format(new Date())
                if (msgsRepo.beyondLimit(email, sysConfig.commentLimit)) {
                    resp.msg = "你的留言数已经超过限制"
                } else if (msgsRepo.addMsg(po)) {
                    resp.code = 200
                    resp.msg = "提交成功"
                } else {
                    resp.msg = "提交失败"
                }
            } else {
                resp.msg = "输入信息异常"
            }
        } else {
            resp.msg = "请登录！"
        }
    }

    private static class MsgPagedListBo {
        Integer index
        Integer whole
        Integer count
        List<MsgPo> list
    }
}
