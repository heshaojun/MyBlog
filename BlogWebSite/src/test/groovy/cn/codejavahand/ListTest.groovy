package cn.codejavahand

import cn.codejavahand.dao.po.MsgPo

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/04/18
 * @Description TODO
 */
class ListTest {
    public static void main(String[] args) {
        List<MsgPo> list = new ArrayList<>()
        list.add(new MsgPo(name: "hehehe", email: "dsfdsf"))
        list.forEach({
            println("${it.name}")
        })

        list.forEach({
            it.setName("sdoiksofkosd")
        })
        list.forEach({
            println("${it.name}")
        })

    }
}
