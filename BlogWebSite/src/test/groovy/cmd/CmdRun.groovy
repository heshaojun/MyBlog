package cmd

/**
 * @author heshaojun* @date 2021/3/15
 * @description TODO
 */
class CmdRun {
    public static void main(String[] args) {
        def exe = ["java", "-version"].execute()
        println(exe.waitFor())
        println "return code: ${ exe.exitValue()}"
        println "stderr: ${exe.err.text}"
        println "stdout: ${exe.in.text}"
    }
}
