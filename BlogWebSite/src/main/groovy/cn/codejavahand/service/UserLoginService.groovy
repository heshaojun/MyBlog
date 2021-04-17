package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.mail.Session
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/04/17
 * @Description TODO
 */
@Service
class UserLoginService {
    @Autowired
    private MailClientService mailClientService


    RestResp login(HttpServletRequest request) {
        RestResp resp = new RestResp(code: 300, msg: "登录失败")
        String userName = request.getParameter("userName")
        String userCode = request.getParameter("code")
        String userEmail = request.getParameter("email")
        HttpSession session = request.getSession()
        String code = session.getAttribute("code")
        String email = session.getAttribute("email")
        try {
            if (!userName) throw new Exception("用户名名称不能为空")
            if (userName.length() > 20) throw new Exception("名字过长")
            if (!userCode) throw new Exception("验证码不能为空")
            if (userCode.length() != 6) throw new Exception("验证码不正确")
            if (!userEmail) throw new Exception("邮箱不能为空")
            if (!code || !email) throw new Exception("验证码不正确")
            if (userEmail != email) throw new Exception("邮箱不正确")
            if (userCode != code) throw new Exception("验证码不正确")
            session.setAttribute("userName", userName)
            session.setAttribute("loginType", "email")
            Map<String, String> map = new Hashtable<>()
            resp.code = 200
            resp.msg = "ok"
            map.put("userName", userName)
            map.put("loginType", "email")
            resp.data = map
        } catch (Exception e) {
            resp.code = 300
            resp.msg = e.getMessage()
        }

        resp

    }

    RestResp getUserInfo(HttpServletRequest request) {
        RestResp resp = new RestResp(code: 300, msg: "not login")
        HttpSession session = request.getSession()
        String userName = session.getAttribute("userName")
        String loginType = session.getAttribute("loginType")
        if (userName && loginType) {
            Map<String, String> map = new Hashtable<>()
            resp.code = 200
            resp.msg = "ok"
            map.put("userName", userName)
            map.put("loginType", loginType)
            resp.data = map
        }
        resp
    }
}
