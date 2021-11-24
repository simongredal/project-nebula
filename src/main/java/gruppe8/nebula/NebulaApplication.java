package gruppe8.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class NebulaApplication {
	private final TaskService taskService;

	public static void main(String[] args) {
		SpringApplication.run(NebulaApplication.class, args);
	}


	public NebulaApplication(TaskService taskService) {
		this.taskService = taskService;
	}


	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Hello World";
	}

	@GetMapping("/tasks")
	@ResponseBody
	public String tasks(@RequestParam Long user) {
		return String.join("<br>\n", taskService.getTasksForUser(user));
	}

}
