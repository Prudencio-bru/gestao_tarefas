package gestao.com.br.gestao_tarefas.Repository;

import gestao.com.br.gestao_tarefas.Entity.Task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
