package gruppe8.nebula.services;

import gruppe8.nebula.models.Team;
import gruppe8.nebula.repositories.TaskRepository;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.requests.TeamCreationRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<TaskEntity> getTasksForUser(Long project) {
        return taskRepository.getTasksForProject(project);
    }

    /*
    #FIXME FREDAG
    public Boolean addTask(TaskCreationRequest request) {
        return taskRepository.createTask(task,parent,project);
    }
    */
}
