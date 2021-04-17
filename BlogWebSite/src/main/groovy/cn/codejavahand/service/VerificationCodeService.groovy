package cn.codejavahand.service

import cn.codejavahand.config.SysConfig
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentHashMap

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/04/17
 * @Description TODO
 */
@Service
@Log
class VerificationCodeService {
    @Autowired
    private SysConfig sysConfig
    private static final Map<String, CodeObj> CODE_CACHED = new ConcurrentHashMap<>()
    private static final Map<String, Long> SEND_LIMIT = new ConcurrentHashMap<>()


    String generateCode(String email) {
        String code = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(5, 11)
        Long refreshTime = new Date().getTime()
        CODE_CACHED.put(email, new CodeObj(code: code, refreshTime: refreshTime))
        SEND_LIMIT.put(email, refreshTime)
        code
    }

    boolean verifyCode(String code, String email) {
        if (CODE_CACHED.containsKey(email)) {
            if (code == CODE_CACHED.get(email).getCode()) {
                CODE_CACHED.remove(email)
                return true
            }
        }
        return false
    }

    boolean isSendAble(String email) {
        if (SEND_LIMIT.containsKey(email)) {
            return false
        }
        true
    }

    void removeEmail(String email) {
        try {
            SEND_LIMIT.remove(email)
        } catch (Exception e) {
            e.printStackTrace()
        }
        try {
            CODE_CACHED.remove(email)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Scheduled(cron = "0/2 * * * * *")
    private void timeoutService() {
        for (Map.Entry<String, CodeObj> entry : CODE_CACHED) {
            long currentTime = new Date().getTime()
            try {
                if (currentTime - entry.getValue().refreshTime >= sysConfig.codeTimeout) {
                    log.info("删除邮件为${entry.getKey()}验证码缓存")
                    CODE_CACHED.remove(entry.getKey())
                }
            } catch (Exception e) {
                e.printStackTrace()
            }

        }
        for (Map.Entry<String, CodeObj> entry : SEND_LIMIT) {
            long currentTime = new Date().getTime()
            try {
                if (currentTime - entry.getValue() >= sysConfig.resendTimeout) {
                    log.info("删除邮件为${entry.getKey()}限制缓存")
                    SEND_LIMIT.remove(entry.getKey())
                }
            } catch (Exception e) {
                e.printStackTrace()
            }

        }
    }

    private static class CodeObj {
        String code
        Long refreshTime
    }
}
