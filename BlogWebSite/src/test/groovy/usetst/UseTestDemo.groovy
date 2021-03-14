package usetst

import cn.codejavahand.utils.IntegerUtils

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/14
 * @Description TODO
 */
class UseTestDemo {
    public static void main(String[] args) {
        Integer v = 10000
        use(IntegerUtils) {
            println(v.toEasyRead())
        }
    }
}
