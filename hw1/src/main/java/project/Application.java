package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.cli.AppCLI;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(Application.class, args);
        var appCLI = ctx.getBean(AppCLI.class);
        appCLI.start();
    }
}
