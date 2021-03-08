package cn.codejavahand.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.HttpServletResponse

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/08
 * @Description TODO
 */
@Controller
@RequestMapping
class PageController {
    @RequestMapping
    void home(HttpServletResponse response) {
        response.sendRedirect("/home.html")
    }
}
