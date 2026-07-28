package com.taskLevel.up.repository;

import com.taskLevel.up.models.Dificuldade;
import com.taskLevel.up.models.Task;
import com.taskLevel.up.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

List<Task> findAllByUsuarioOrderByCriado(Usuario usuario);
Optional<Task> findByIdAndUsuario(Long id, Usuario usuario);

long countByUsuarioAndStatus(Usuario usuario, String status);
long countByUsuarioAndStatusAndDificuldade(Usuario usuario, String status, Dificuldade dificuldade);

    @Modifying
    @Query("UPDATE Task t SET t.statusTask = com.taskquest.models.StatusTask.ATRASADA " +
            "WHERE t.statusTask = com.taskquest.models.StatusTask.PENDENTE AND t.data < :now")
    int markOverdueTasks(@Param("now") LocalDateTime now);
}
