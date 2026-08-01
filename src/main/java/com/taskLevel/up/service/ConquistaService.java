package com.taskLevel.up.service;

import com.taskLevel.up.models.Conquista;
import com.taskLevel.up.models.ConquistaUsuario;
import com.taskLevel.up.models.Dificuldade;
import com.taskLevel.up.models.ModeloConquista;
import com.taskLevel.up.models.StatusTask;
import com.taskLevel.up.models.Usuario;
import com.taskLevel.up.repository.ConquistaRepository;
import com.taskLevel.up.repository.ConquistaUsuarioRepository;
import com.taskLevel.up.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConquistaService {

        private final ConquistaRepository conquistaRepository;
        private final ConquistaUsuarioRepository conquistaUsuarioRepository;
        private final TaskRepository taskRepository;
        private final LevelService levelService;

        @Transactional
        public List<String> checkAndUnlockAchievements(Usuario user) {
            List<String> unlocked = new ArrayList<>();

            unlocked.addAll(checkCriteria(user, ModeloConquista.TAREFA_COMPLETA,
                    taskRepository.countByUsuarioAndStatus(user, StatusTask.CONCLUIDA)));

            unlocked.addAll(checkCriteria(user, ModeloConquista.TAREFA_EPICA_COMPLETA,
                    taskRepository.countByUsuarioAndStatusAndDificuldade(user, StatusTask.CONCLUIDA, Dificuldade.EPICO)));

            unlocked.addAll(checkCriteria(user, ModeloConquista.DIAS_OFENSIVOS, user.getDiasOfensivos()));

            unlocked.addAll(checkCriteria(user, ModeloConquista.NIVEL_ATINGIDO,
                    levelService.calculaLevel(user.getTotalXp())));

            return unlocked;
        }

        private List<String> checkCriteria(Usuario usuario, ModeloConquista tipo, long currentValue) {
            List<String> unlocked = new ArrayList<>();

            for (Conquista conquista : conquistaRepository.findAllByModeloConquista(tipo)) {
                if (currentValue < conquista.getLimite()) {
                    continue;
                }
                if (conquistaUsuarioRepository.existsByUsuarioAndConquista(usuario, conquista)) {
                    continue;
                }
                conquistaUsuarioRepository.save(ConquistaUsuario.builder()
                        .usuario(usuario)
                        .conquista(conquista)
                        .build());
                unlocked.add(conquista.getNome());
            }
            return unlocked;
        }
    }

