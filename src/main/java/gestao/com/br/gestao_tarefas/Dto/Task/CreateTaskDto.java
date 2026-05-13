package gestao.com.br.gestao_tarefas.Dto.Task;

import gestao.com.br.gestao_tarefas.Enum.Task.PriorityTaskEnum;
import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateTaskDto {

    @NotBlank(message = "Title must not be blank")
    @Size(min = 3, message = "Title must have at least 3 characters")
    private String title;

    private String description;

    @NotNull(message = "Status is required")
    private StatusTaskEnum status;

    @NotNull(message = "Priority is required")
    private PriorityTaskEnum priority;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate due_date;

    // getters e setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusTaskEnum getStatus() {
        return status;
    }

    public void setStatus(StatusTaskEnum status) {
        this.status = status;
    }

    public PriorityTaskEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityTaskEnum priority) {
        this.priority = priority;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }
}