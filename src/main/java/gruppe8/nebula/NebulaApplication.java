package gruppe8.nebula;

import com.google.gson.Gson;
import gruppe8.nebula.services.ProjectService;
import gruppe8.nebula.services.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class NebulaApplication {
	private final ProjectService projectService;

	public static void main(String[] args) {
		SpringApplication.run(NebulaApplication.class, args);
	}

	public NebulaApplication(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Hello World";
	}

	@GetMapping("/tasks")
	@ResponseBody
	public String tasks(@RequestParam Long project) {
		return new Gson().toJson( projectService.getProjectById(project) );
	}

}
