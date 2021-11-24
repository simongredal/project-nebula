package gruppe8.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
