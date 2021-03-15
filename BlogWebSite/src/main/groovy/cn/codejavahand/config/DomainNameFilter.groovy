package cn.codejavahand.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

/**
 * @author heshaojun* @date 2021/3/15
 * @description TODO
 */
@WebFilter(urlPatterns = "/*")
@Service
class DomainNameFilter implements Filter {
    @Autowired
    private SysConfig sysConfig

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request
        //只运行带域名的连接访问
        String host = servletRequest.getHeader("host")
        if (!sysConfig.domainNames.contains(host)) {
            response.writer.with {
                write("bad request!")
                flush()
            }
            return
        } else {
            chain.doFilter(request, response)
        }
    }
}
