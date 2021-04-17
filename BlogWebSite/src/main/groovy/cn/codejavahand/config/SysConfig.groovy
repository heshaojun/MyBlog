package cn.codejavahand.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

/**
 * @author heshaojun* @date 2021/3/11
 * @description TODO
 */
@Configuration
@ConfigurationProperties(prefix = "sys.config")
@EnableCaching
class SysConfig {
    /*文章存储路径*/
    String articleStoragePath
    /*网站文章相关数据存储路径*/
    String articleDataStorePath
    /*网站留言数据存储路径*/
    String siteMsgsDataStorePath
    /*文章评论展示数量*/
    Integer commentsListNum = 10
    /*最新文章展示数量*/
    Integer newestArticleListNum = 8
    /*最热文章展示数量*/
    Integer hottestArticleListNum = 8
    /*shell脚本路径*/
    String shellPath = "gitpull.sh"
    /*限制域名*/
    List<String> domainNames
    /*git webhook 密码*/
    String webHookPasswd
    /*邮箱服务器地址*/
    String mailHost
    /*邮箱stmp密码*/
    String stmpPasswd
    /*邮箱名*/
    String email
    /*验证码超时时间*/
    Long codeTimeout
    /*邮件重新发送超时时间*/
    Long resendTimeout

}
