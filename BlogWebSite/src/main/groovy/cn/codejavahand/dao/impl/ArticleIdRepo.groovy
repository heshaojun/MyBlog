package cn.codejavahand.dao.impl

import cn.codejavahand.config.SysConfig
import cn.codejavahand.dao.IArticleIdRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/12
 * @description TODO
 */
@Service
class ArticleIdRepo implements IArticleIdRepo {
    @Autowired
    private SysConfig sysConfig

    @Cacheable(cacheNames = ["allArticleId"])
    @Override
    List<String> getAllArticleList() {
        List<String> idList = new ArrayList<>()
        Arrays.asList((new File(sysConfig.articleStoragePath)).list([accept: { dir, name -> dir.exists() && name.endsWith(".json") && (new File("${dir.absolutePath}/$name").isFile()) }] as FilenameFilter)).forEach(
                {
                    idList.add(it.split(".json")[0])
                }
        )
        idList
    }

    @CacheEvict(cacheNames = ["allArticleId"])
    @Override
    void cleanCache() {
    }

}
