package cn.codejavahand

import com.sun.mail.smtp.SMTPMessage
import com.sun.mail.smtp.SMTPTransport

import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/04/17
 * @Description TODO
 */
class MailSendTest {
    private static String host = "smtp.163.com"

    static void main(String[] args) {
        Properties props = new Properties()
        props.with {
            put "mail.smtp.host", "smtp.163.com"
            put "mail.smtp.auth", "true"
        }
        Session session = Session.getInstance(props, null)
        Message msg = new SMTPMessage(session)
        msg.setFrom "masker_zero@163.com"
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress("keepword_heshaojun@hotmail.com"))
        msg.setSubject("这是一篇测试邮件！")
        msg.setText("测试测试测试", "utf8")
        SMTPTransport transport = (SMTPTransport) session.getTransport("smtp")
        transport.connect("masker_zero@163.com", "BQODYTFERHGHSOPQ")
        transport.sendMessage(msg, msg.getAllRecipients())

    }
}
