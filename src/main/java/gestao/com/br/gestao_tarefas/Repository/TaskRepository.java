package gestao.com.br.gestao_tarefas.Repository;

import gestao.com.br.gestao_tarefas.Entity.Task.TaskEntity;
import gestao.com.br.gestao_tarefas.Enum.Task.PriorityTaskEnum;
import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Override
    Optional<TaskEntity> findById(Long aLong);

    List<TaskEntity> findByStatus(StatusTaskEnum status);
    List<TaskEntity> findByPriority(PriorityTaskEnum priority);
    List<TaskEntity> findByDueDateLessThanEqual(LocalDate due_date);
    List<TaskEntity> findByDueDateGreaterThanEqual(LocalDate due_date);
    List<TaskEntity> findByDueDate(LocalDate due_date);

}
