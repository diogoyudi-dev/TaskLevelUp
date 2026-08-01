package com.taskLevel.up.repository;

import com.taskLevel.up.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String nomeUsuario);
    Optional<Usuario> findByEmail(String email);
    Boolean existsByUsuario(String nomeUsuario);
    Boolean existsByEmail(String email);
    List<Usuario> findAllByOrderByTotalXpDesc(Pageable pageable);
}
