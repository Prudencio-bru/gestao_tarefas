package gestao.com.br.gestao_tarefas.Dto.Task;

import gestao.com.br.gestao_tarefas.Enum.Task.PriorityTaskEnum;
import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;

import java.time.LocalDate;

public class UpdateTaskDto {
    private Long id_task;
    private String title;
    private String description;
    private StatusTaskEnum status;
    private PriorityTaskEnum priority;
    private LocalDate due_date;

    public Long getId_task() {
        return id_task;
    }

    public void setId_task(Long id_task) {
        this.id_task = id_task;
    }

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
