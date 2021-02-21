package cn.codejavahand

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
class AppStarter {

    static void main(String[] args) {
        new SpringApplication(AppStarter.class).with {
            addListeners(new ApplicationPidFileWriter("app.pid"))
            run(args)
        }
    }

}
