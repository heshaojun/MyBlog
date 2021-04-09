package cn.codejavahand.service

import cn.codejavahand.dao.IVisitCountRepo
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/14
 * @Description TODO 文章访问次数记录服务类,减少磁盘io操作
 */
@Service
@Log
@EnableScheduling
class ArticleVisitRecordService {
    private Map<String, Integer> recorder = new Hashtable<>()
    private static final Object object = new Object()
    @Autowired
    private IVisitCountRepo visitCountRepo

    void record(String articleId) {
        log.info("有新的访问$articleId")
        synchronized (object) {
            try {
                if (recorder.containsKey(articleId)) {
                    Integer currentCount = recorder.get(articleId)
                    recorder.put(articleId, currentCount + 1)
                } else {
                    recorder.put(articleId, 1)
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    void storeUp() {
        synchronized (object) {
            log.info("开始存储文章的访问记录数据")
            Set<String> stored = new HashSet<>()
            recorder.each {
                k, v ->
                    try {
                        stored.add("$k")
                        log.info("开始获取文章原有访问量")
                        int count = visitCountRepo.getArticleVisitCount("$k")
                        log.info("文章$k 原有访问量 $count")
                        count += v
                        log.info("开始存储文章最新反问量")
                        visitCountRepo.setArticleVisitCount("$k", count)
                        log.info("存储访问记录$k  $count")
                    } catch (Exception e) {
                        e.printStackTrace()
                    }
            }
            stored.each {
                try {
                    recorder.remove("$it")
                } catch (Exception e) {
                    e.printStackTrace()
                }
            }
            log.info("数据存储完成！")
        }
    }

}
