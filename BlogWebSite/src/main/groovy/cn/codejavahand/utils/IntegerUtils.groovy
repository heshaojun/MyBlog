package cn.codejavahand.utils

/**
 * @Author shaojun he
 * @Mail keepword_heshaojun@hotmail.com
 * @Date 2021/03/14
 * @Description TODO
 */
class IntegerUtils {
    def static toEasyRead(Integer self) {
        String result = "$self"
        if (self >= 1000 && self < 10000) {
            Integer s = self / 1000
            if (self % 1000 > 0) {
                result = "${s}千+"
            } else {
                result = "${s}千"
            }
        } else if (self >= 10000) {
            Integer ts = self / 10000
            if (self % 10000 > 0) {
                result = "${ts}万+"
            } else {
                result = "${ts}万"
            }
        }
        result
    }

}
