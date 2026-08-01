package com.taskLevel.up.repository;

import com.taskLevel.up.models.Conquista;
import com.taskLevel.up.models.ModeloConquista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConquistaRepository extends JpaRepository<Conquista, Integer> {

    List<Conquista> findAllByModeloConquista(ModeloConquista modelo);
}
