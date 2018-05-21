package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YKL on 2018/4/16.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@SpringBootApplication(scanBasePackages = "controller")
public class RunApplication {

    public static void main(String[] args) {

        SpringApplication.run(RunApplication.class, args);

    }

}
