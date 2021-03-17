package cn.codejavahand.dao

import cn.codejavahand.dao.po.MsgPo

/**
 * @author heshaojun* @date 2021/3/16
 * @description TODO 网站留言数据操作接口
 */
interface IMsgsRepo extends ICacheCleaner {
    List<MsgPo> getAllMsg()

    void addMsg(MsgPo msgPo)
}