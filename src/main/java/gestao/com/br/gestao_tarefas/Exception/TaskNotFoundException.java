package gestao.com.br.gestao_tarefas.Exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id_task) {
        super("Task not found for id: " + id_task);
    }

}
