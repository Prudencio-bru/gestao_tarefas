package gestao.com.br.gestao_tarefas.Implements.Task;

import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Entity.Task.TaskEntity;
import gestao.com.br.gestao_tarefas.Repository.TaskRepository;
import gestao.com.br.gestao_tarefas.Service.Task.TaskService;
import gestao.com.br.gestao_tarefas.Util.DateTimeBR;
import org.springframework.stereotype.Service;

@Service
public class TaskImplements implements TaskService {

    private final TaskRepository taskRepository;

    public TaskImplements(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(CreateTaskDto dto) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(dto.getTitle());
        taskEntity.setDescription(dto.getDescription());
        taskEntity.setStatus(dto.getStatus());
        taskEntity.setPriority(dto.getPriority());
        taskEntity.setDue_date(dto.getDue_date());
        taskEntity.setCreatedAt(DateTimeBR.nowLocal());

        taskRepository.save(taskEntity);
    }
}
