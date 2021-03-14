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
    /*网站数据存储路径*/
    String siteDataStorePath
    /*文章评论展示数量*/
    Integer commentsListNum = 10
    /*最新文章展示数量*/
    Integer newestArticleListNum = 8
    /*最热文章展示数量*/
    Integer hottestArticleListNum = 8
    /*shell脚本路径*/
    String shellPath = "gitpull.sh"

}
