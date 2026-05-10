package gestao.com.br.gestao_tarefas.Service.Task;

import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateTaskDto;

public interface TaskService {
    void createTask(CreateTaskDto createTaskDto);
    void  updateTask(UpdateTaskDto updateTaskDto);
}
