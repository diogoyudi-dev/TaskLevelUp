package com.taskLevel.up.repository;

import com.taskLevel.up.models.Conquista;
import com.taskLevel.up.models.ConquistaUsuario;
import com.taskLevel.up.models.Task;
import com.taskLevel.up.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConquistaUsuarioRepository extends JpaRepository<ConquistaUsuario, Long> {

    List<ConquistaUsuario> findAllByUsuario(Usuario usuario);

    boolean existsByUsuarioAndConquista(Usuario usuario, Conquista conquista);

}
