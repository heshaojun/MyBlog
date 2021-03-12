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
    String articleStoragePath
    String siteDataStorePath

}
