package gestao.com.br.gestao_tarefas.Service.Task;

import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.ListTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateStatusTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateTaskDto;
import gestao.com.br.gestao_tarefas.Enum.Task.PriorityTaskEnum;
import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    void createTask(CreateTaskDto createTaskDto);
    void  updateTask(UpdateTaskDto updateTaskDto);
    List<ListTaskDto> findAll();
    ListTaskDto findById(Long id_task);
    void delete(Long id_task);

    //Filtro de busca por status
    List<ListTaskDto> findByStatus(StatusTaskEnum status);

    //Filtro de busca por Prioridade
    List<ListTaskDto> findByPriority(PriorityTaskEnum priority);

    //Filtro de busca por Data Limite
    List<ListTaskDto> findByDueDate(LocalDate due_date);

    //Filtro ordenação crescente
    List<ListTaskDto> listOrderByCreatedAtAsc();

    //Filtro ordenação decrescente
    List<ListTaskDto> listOrderByCreatedAtDesc();

    //Filtro por prioridade
    List<ListTaskDto> listOrderByPriority();

    //Update de Status
    void updateStatus(UpdateStatusTaskDto dto);
}
