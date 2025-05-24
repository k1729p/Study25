package kp.resource.server.b;

import kp.resource.server.configuration.PasswordProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * The application serves as a resource server.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "kp.resource.server.configuration",
        "kp.resource.server.services",
        "kp.resource.server.b.configuration",
        "kp.resource.server.b"
})
@EnableConfigurationProperties(PasswordProperties.class)
public class Application {
    /**
     * The primary entry point for launching the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}