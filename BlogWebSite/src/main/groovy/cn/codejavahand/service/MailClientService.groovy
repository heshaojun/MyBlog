package cn.codejavahand.service

import cn.codejavahand.common.RestResp
import cn.codejavahand.config.SysConfig
import com.sun.mail.smtp.SMTPMessage
import com.sun.mail.smtp.SMTPTransport
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/04/17
 * @Description TODO
 */
@Service
@Log
class MailClientService {
    @Autowired
    private SysConfig sysConfig
    @Autowired
    private VerificationCodeService verificationCodeService
    private Properties props


    RestResp sentVerificationCode(HttpServletRequest request) {
        String email = ""
        try {
            email = request.getParameter("email")
        } catch (Exception e) {
            e.printStackTrace()
            email = ""
        }
        RestResp resp = new RestResp()
        if (!verifyEmail(email)) {
            resp.code = 300
            resp.msg = "Invalid email!"
        } else {
            if (!verificationCodeService.isSendAble(email)) {
                resp.code = 300
                resp.msg = "frequent operation!"
            } else {
                String code = verificationCodeService.generateCode(email)
                String subject = "Mr.Ho个人网站验证码"
                String context = "验证码：${code} 请在10分钟内使用！"
                if (sendMsg(subject, context, email)) {
                    log.info("验证码：${code}")
                    HttpSession session = request.getSession()
                    session.setAttribute("email", email)
                    session.setAttribute("code", code)
                    resp.code = 200
                    resp.msg = "ok"
                } else {
                    resp.code = 300
                    resp.msg = "Sending email is failure!"
                    verificationCodeService.removeEmail(email)
                }
            }
        }
        resp
    }

    private boolean verifyEmail(String email) {
        if (email.contains("@") && email.contains("."))
            return true
        else
            return false
    }


    private boolean sendMsg(String subject, String context, String to) {
        try {
            Session session = Session.getInstance(props, null)
            Message msg = new SMTPMessage(session)
            msg.setFrom sysConfig.email
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to))
            msg.setSubject(subject)
            msg.setText(context, "utf8")
            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp")
            transport.connect(sysConfig.email, sysConfig.stmpPasswd)
            transport.sendMessage(msg, msg.getAllRecipients())
            return true
        } catch (Exception e) {
            e.printStackTrace()
            log.info "发送邮件失败"
        }
        false
    }

    @PostConstruct
    private void init() {
        props = new Properties()
        props.put("mail.smtp.host", sysConfig.mailHost)
        props.put("mail.smtp.auth", "true")
    }
}
