package gestao.com.br.gestao_tarefas.Dto.Task;

import gestao.com.br.gestao_tarefas.Enum.Task.PriorityTaskEnum;
import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ListTaskDto(
        Long id_task,
        String title,
        String description,
        StatusTaskEnum status,
        PriorityTaskEnum priority,
        LocalDate due_date,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
}
