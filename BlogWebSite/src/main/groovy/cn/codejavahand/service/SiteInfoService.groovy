package cn.codejavahand.service

import cn.codejavahand.bo.SiteInfoBo
import cn.codejavahand.common.RestResp
import cn.codejavahand.dao.IArticleIdRepo
import cn.codejavahand.dao.IArticleInfoRepo
import cn.codejavahand.dao.IMsgsRepo
import cn.codejavahand.dao.IVisitCountRepo
import cn.codejavahand.dao.po.ArticleInfoPo
import cn.codejavahand.dao.po.MsgPo
import cn.codejavahand.utils.IntegerUtils
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/09
 * @Description TODO
 */
@Service
@Log
class SiteInfoService {
    @Autowired
    private IArticleIdRepo articleIdRepo
    @Autowired
    private IVisitCountRepo visitCountRepo
    @Autowired
    private IArticleInfoRepo articleInfoRepo
    @Autowired
    private IMsgsRepo msgsRepo

    RestResp doService() {
        log.info("获取网站统计信息")
        [
                code: 200,
                msg : "ok",
                data: [
                        visit  : getVisitCount(),
                        article: getArticleCount(),
                        blog   : getBlogCount(),
                        note   : getNoteCount(),
                        msg    : getMsgCount()
                ] as SiteInfoBo
        ] as RestResp
    }

    private String getVisitCount() {
        String result = "0"
        try {
            Integer count = 0
            List<String> allArticleId = articleIdRepo.getAllArticleList()
            if (allArticleId) {
                allArticleId.each {
                    count += visitCountRepo.getArticleVisitCount("$it")
                }
            }
            use(IntegerUtils) {
                result = count.toEasyRead()
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        result
    }

    private String getArticleCount() {
        String result = "0"
        try {
            Integer count = articleIdRepo.getAllArticleList().size()
            use(IntegerUtils) {
                result = count.toEasyRead()
            }
        } catch (Exception e) {
        }
        result
    }

    private String getBlogCount() {
        String result = "0"
        try {
            Integer count = 0
            List<String> allArticleId = articleIdRepo.getAllArticleList()
            allArticleId.each {
                ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById("$it")
                if (articleInfoPo.type == "blog") {
                    count++
                }
            }
            use(IntegerUtils) {
                result = count.toEasyRead()
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        result
    }

    private String getNoteCount() {
        String result = "0"
        try {
            Integer count = 0
            List<String> allArticleId = articleIdRepo.getAllArticleList()
            allArticleId.each {
                ArticleInfoPo articleInfoPo = articleInfoRepo.getArticleInfoById("$it")
                if (articleInfoPo.type == "note") {
                    count++
                }
            }
            use(IntegerUtils) {
                result = count.toEasyRead()
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        result
    }

    private String getMsgCount() {
        String result = "0"
        try {
            List<MsgPo> list = msgsRepo.getAllMsg()
            if (list) {
                result = "${list.size()}"
            }
        } catch (Exception e) {
        }
        result
    }

}
