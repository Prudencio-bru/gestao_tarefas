package gestao.com.br.gestao_tarefas.Dto.Task;

import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusTaskDto(
        @NotNull(message = "Task id must not be null")
        Long id_task,

        @NotNull(message = "Status must not be null")
        StatusTaskEnum status
) {
}
