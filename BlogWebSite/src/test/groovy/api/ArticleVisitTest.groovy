package api

import cn.codejavahand.AppStarter
import cn.codejavahand.dao.IArticleCommentRepo
import cn.codejavahand.dao.IVisitCountRepo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/14
 * @Description TODO
 */
@SpringBootTest(classes = [AppStarter.class])
class ArticleVisitTest {
    @Autowired
    private IVisitCountRepo visitCountRepo

    @Test
    public void test() {
        println visitCountRepo.getArticleVisitCount("123455")
    }
}
