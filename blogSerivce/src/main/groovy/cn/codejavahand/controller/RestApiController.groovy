package cn.codejavahand.controller

import cn.codejavahand.common.RestResp
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/08
 * @Description TODO
 */
@RestController
@RequestMapping("rest")
class RestApiController {

    RestResp articleList() {}
}
