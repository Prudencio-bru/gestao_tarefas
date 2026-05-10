package gestao.com.br.gestao_tarefas.Service.Task;

import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.ListTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateTaskDto;

import java.util.List;

public interface TaskService {
    void createTask(CreateTaskDto createTaskDto);
    void  updateTask(UpdateTaskDto updateTaskDto);
    List<ListTaskDto> findAll();
    ListTaskDto findById(Long id_task);
}
