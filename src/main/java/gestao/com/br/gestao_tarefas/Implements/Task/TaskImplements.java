package gestao.com.br.gestao_tarefas.Implements.Task;

import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.ListTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateStatusTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateTaskDto;
import gestao.com.br.gestao_tarefas.Entity.Task.TaskEntity;
import gestao.com.br.gestao_tarefas.Enum.Task.PriorityTaskEnum;
import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;
import gestao.com.br.gestao_tarefas.Exception.BusinessException;
import gestao.com.br.gestao_tarefas.Exception.TaskNotFoundException;
import gestao.com.br.gestao_tarefas.Repository.TaskRepository;
import gestao.com.br.gestao_tarefas.Service.Task.TaskService;
import gestao.com.br.gestao_tarefas.Util.DateTimeBR;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.Conversion;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
public class TaskImplements implements TaskService {

    private final TaskRepository taskRepository;

    public TaskImplements(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(CreateTaskDto dto) {

        if (dto.getDue_date() == null) {
            throw new BusinessException("Expiration date information is required.");
        }

        if (dto.getTitle().isBlank() || dto.getTitle().isEmpty() || dto.getTitle().trim().length() < 3) {
            throw new BusinessException("Title must have at least 3 characters");
        }

        if (dto.getDue_date() != null && dto.getDue_date().isBefore(LocalDate.now())) {
            throw new BusinessException("Due date cannot be earlier than today");
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(dto.getTitle());
        taskEntity.setDescription(dto.getDescription());
        taskEntity.setStatus(dto.getStatus());
        taskEntity.setPriority(dto.getPriority());
        taskEntity.setDueDate(dto.getDue_date());
        taskEntity.setCreatedAt(DateTimeBR.nowLocal());

        taskRepository.save(taskEntity);
    }

    @Override
    public void updateTask(UpdateTaskDto dto) {

        Optional.ofNullable(dto.getId_task()).orElseThrow(() -> new BusinessException("Please provide the ID of the task you wish to modify."));

        if (dto.getTitle().isBlank() || dto.getTitle().isEmpty() || dto.getTitle().trim().length() < 3) {
            throw new BusinessException("Title must have at least 3 characters");
        }

        if (dto.getDue_date() != null && dto.getDue_date().isBefore(LocalDate.now())) {
            throw new BusinessException("Due date cannot be earlier than today");
        }

        TaskEntity taskEntity = taskRepository.findById(dto.getId_task()).orElseThrow(() -> new TaskNotFoundException(dto.getId_task()));
        taskEntity.setTitle(dto.getTitle());
        taskEntity.setDescription(dto.getDescription());
        taskEntity.setStatus(dto.getStatus());
        taskEntity.setPriority(dto.getPriority());
        taskEntity.setDueDate(dto.getDue_date());
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
                        task.getDueDate(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public ListTaskDto findById(Long id_task) {
        TaskEntity task = taskRepository.findById(id_task)
                .orElseThrow(() -> new TaskNotFoundException(id_task));
        return new ListTaskDto(
                task.getIdTask(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    @Override
    public void delete(Long id_task) {
        TaskEntity task = taskRepository.findById(id_task)
                .orElseThrow(() -> new TaskNotFoundException(id_task));
        if (task.getStatus() == StatusTaskEnum.done) {
            throw new BusinessException("Completed tasks cannot be deleted");
        }
        taskRepository.deleteById(id_task);
    }

    @Override
    public List<ListTaskDto> findByStatus(StatusTaskEnum status) {

        if (status == null) {
            throw new BusinessException("Status must not be null");
        }

        List<TaskEntity> tasks = taskRepository.findByStatus(status);

        return tasks.stream()
                .map(task -> new ListTaskDto(
                        task.getIdTask(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public List<ListTaskDto> findByPriority(PriorityTaskEnum priority) {
        if (priority == null) {
            throw new BusinessException("Priority must not be null");
        }
        List<TaskEntity> tasks = taskRepository.findByPriority(priority);

        return tasks.stream()
                .map(task -> new ListTaskDto(
                        task.getIdTask(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public List<ListTaskDto> findByDueDate(LocalDate due_date) {

        if (due_date == null) {
            throw new BusinessException("Due date must not be null");
        }

        return taskRepository.findByDueDate(due_date)
                .stream()
                .map(task -> new ListTaskDto(
                        task.getIdTask(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    //Ordenação Crescente
    @Override
    public List<ListTaskDto> listOrderByCreatedAtAsc() {

        Sort sort = Sort.by("createdAt").ascending();

        return taskRepository.findAll(sort)
                .stream()
                .map(task -> new ListTaskDto(
                        task.getIdTask(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    //Ordenação Decrescente
    @Override
    public List<ListTaskDto> listOrderByCreatedAtDesc() {

        Sort sort = Sort.by("createdAt").descending();

        return taskRepository.findAll(sort)
                .stream()
                .map(task -> new ListTaskDto(
                        task.getIdTask(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    //Ordenação por Prioridade
    @Override
    public List<ListTaskDto> listOrderByPriority() {

        Sort sort = Sort.by("priority").ascending();

        return taskRepository.findAll(sort)
                .stream()
                .map(task -> new ListTaskDto(
                        task.getIdTask(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDueDate(),
                        task.getCreatedAt(),
                        task.getUpdatedAt()
                ))
                .toList();
    }

    //Update Status Task
    @Override
    public void updateStatus(UpdateStatusTaskDto dto) {

        TaskEntity task = taskRepository.findById(dto.id_task())
                .orElseThrow(() -> new TaskNotFoundException(dto.id_task()));

        if (task.getStatus() == StatusTaskEnum.done) {
            throw new BusinessException("Task already finished and cannot change status");
        }

        task.setStatus(dto.status());
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);
    }
}
