package cn.codejavahand

import cn.codejavahand.config.SysConfig
import cn.codejavahand.service.MailClientService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/04/17
 * @Description TODO
 */
@SpringBootTest(classes = [AppStarter.class])
class EmailServiceTest {
    @Autowired
    private MailClientService mailClientService
    @Autowired
    private SysConfig sysConfig


    @Test
    void test() {
        // mailClientService.sendMsg "测试邮件2", "这是一封测试邮件", "keepword_heshaojun@hotmail.com"
        System.out.println(sysConfig.getCodeTimeout());
    }
}
