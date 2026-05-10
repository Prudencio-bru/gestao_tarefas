package gestao.com.br.gestao_tarefas.Implements.Task;

import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.ListTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateTaskDto;
import gestao.com.br.gestao_tarefas.Entity.Task.TaskEntity;
import gestao.com.br.gestao_tarefas.Repository.TaskRepository;
import gestao.com.br.gestao_tarefas.Service.Task.TaskService;
import gestao.com.br.gestao_tarefas.Util.DateTimeBR;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
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

    @Override
    public void updateTask(UpdateTaskDto dto) {
        TaskEntity taskEntity = taskRepository.findById(dto.getId_task()).orElseThrow(() -> new RuntimeException("Task not found"));
        taskEntity.setTitle(dto.getTitle());
        taskEntity.setDescription(dto.getDescription());
        taskEntity.setStatus(dto.getStatus());
        taskEntity.setPriority(dto.getPriority());
        taskEntity.setDue_date(dto.getDue_date());
        taskEntity.setUpdatedAt(DateTimeBR.nowLocal());

        taskRepository.save(taskEntity);
    }

    @Override
    public List<ListTaskDto> findAll() {
        return  taskRepository.findAll()
                .stream()
                .map(task -> new ListTaskDto(
                        task.getIdTask(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDue_date(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public ListTaskDto findById(Long id_task) {
        TaskEntity task = taskRepository.findById(id_task)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return new ListTaskDto(
                task.getIdTask(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDue_date(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
