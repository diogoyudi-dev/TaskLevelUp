package com.taskLevel.up.service;

import com.taskLevel.up.dto.task.CriacaoTask;
import com.taskLevel.up.dto.task.TaskCompleta;
import com.taskLevel.up.dto.task.TaskDTO;
import com.taskLevel.up.exception.BusinessException;
import com.taskLevel.up.exception.ResourceNotFoundException;
import com.taskLevel.up.models.StatusTask;
import com.taskLevel.up.models.Task;
import com.taskLevel.up.models.Usuario;
import com.taskLevel.up.repository.TaskRepository;
import com.taskLevel.up.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private static final double PENALIDADE_POR_ATRASO =0.5;

    private final TaskRepository taskRepository;
    private final UsuarioRepository usuarioRepository;
    private final LevelService levelService;
    private final ConquistaService conquistaService;

    @Transactional
    public TaskDTO criandoTask(String usuario, CriacaoTask criacaoTask) {
        Usuario usua = getUsuario(usuario);

        Task task = Task.builder()
                .usuario(usua)
                .titulo(criacaoTask.titulo())
                .descricao(criacaoTask.descricao())
                .dificuldade(criacaoTask.dificuldade())
                .data(criacaoTask.data())
                .statusTask(StatusTask.PENDENTE)
                .build();

        return TaskDTO.taskDTO(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> listarTASK(String usuario) {
        Usuario usua = getUsuario(usuario);
        return taskRepository.findAllByUsuarioOrderByCriadoDesc(usua).stream()
                .map(TaskDTO::taskDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskDTO getTask(String username, Long taskId) {
        return TaskDTO.taskDTO(getUsuarioTask(username, taskId));
    }

    @Transactional
    public TaskCompleta completoTask(String usuario, Long taskId) {
        Usuario usua = getUsuario(usuario);
        Task task = getUsuarioTask(usuario, taskId);

        if (task.getStatusTask() == StatusTask.CONCLUIDA) {
            throw new BusinessException("Essa tarefa já foi concluida");
        }

        LocalDateTime now = LocalDateTime.now();
        boolean eAtrasada = task.getCriado() != null && now.isAfter(task.getData());

        int baseXp = task.getDificuldade().getXpBase();
        int xpGanho = eAtrasada ? (int) Math.round(baseXp * PENALIDADE_POR_ATRASO) : baseXp;

        task.setStatusTask(StatusTask.CONCLUIDA);
        task.setCompletado(now);
        task.setAtividadeAtrasadaCompleta(eAtrasada);
        task.setXpGanho(xpGanho);
        taskRepository.save(task);

        int levelAntes = levelService.calculaLevel(usua.getTotalXp());

        usua.setTotalXp(usua.getTotalXp() + xpGanho);
        updateStreak(usua, now.toLocalDate());
        usuarioRepository.save(usua);

        int levelDepois = levelService.calculaLevel(usua.getTotalXp());
        boolean upouLevel = levelDepois > levelAntes;

        List<String> conquistasBloqueadas = conquistaService.checkAndUnlockAchievements(usua);

        return new TaskCompleta(
                TaskDTO.taskDTO(task),
                xpGanho,
                usua.getTotalXp(),
                levelDepois,
                upouLevel,
                conquistasBloqueadas
        );
    }

    private void updateStreak(Usuario usuario, LocalDate today) {
        LocalDate last = usuario.getUltimaConclusao();

        if (last != null && last.equals(today)) {
            return;
        }

        if (last != null && last.equals(today.minusDays(1))) {
            usuario.setDiasOfensivos(usuario.getDiasOfensivos() + 1);
        } else {
            usuario.setDiasOfensivos(1);
        }

        usuario.setUltimaConclusao(today);
        if (usuario.getDiasOfensivos() > usuario.getMaiorOfensiva()) {
            usuario.setMaiorOfensiva(usuario.getDiasOfensivos());
        }
    }

    private Usuario getUsuario(String username) {
        return usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário " + username + " não encontrado"));
    }

    private Task getUsuarioTask(String usuario, Long taskId) {
        Usuario usuar = getUsuario(usuario);
        return taskRepository.findByIdAndUsuario(taskId, usuar)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa " + taskId + " não encontrada"));
    }
}
