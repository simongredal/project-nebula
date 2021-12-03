package gruppe8.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
public class NebulaApplication {
	public static void main(String[] args) {
		SpringApplication.run(NebulaApplication.class, args);
	}
}
