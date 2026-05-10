package gestao.com.br.gestao_tarefas.Repository;

import gestao.com.br.gestao_tarefas.Entity.Task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Override
    Optional<TaskEntity> findById(Long aLong);
}
