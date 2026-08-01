package com.taskLevel.up.service;


import com.taskLevel.up.dto.ranking.RankDTO;
import com.taskLevel.up.dto.user.UsuarioDTO;
import com.taskLevel.up.exception.ResourceNotFoundException;
import com.taskLevel.up.models.Conquista;
import com.taskLevel.up.models.ConquistaUsuario;
import com.taskLevel.up.models.Usuario;
import com.taskLevel.up.repository.ConquistaUsuarioRepository;
import com.taskLevel.up.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ConquistaUsuarioRepository conquistaUsuarioRepository;
    private final LevelService levelService;

    @Transactional(readOnly = true)
    public Usuario getUsuario(String usuario){
        return usuarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário " + usuario + " não encontrado"));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO getPerfil(String usuario) {
        Usuario usua = getUsuario(usuario);

        List<String> nomeConquistas = conquistaUsuarioRepository.findAllByUsuario(usua).stream()
                .map(ConquistaUsuario::getConquista)
                .map(Conquista::getNome)
                .toList();

        return new UsuarioDTO(
                usua.getId(),
                usua.getNome(),
                usua.getEmail(),
                levelService.calculaLevel(usua.getTotalXp()),
                usua.getTotalXp(),
                levelService.xpSobrandoDoNivel(usua.getTotalXp()),
                levelService.xpParaProximoNivel(usua.getTotalXp()),
                usua.getDiasOfensivos(),
                usua.getMaiorOfensiva(),
                nomeConquistas
        );
    }

    @Transactional(readOnly = true)
    public List<RankDTO> topUsuarios(int limite) {
        List<Usuario> topUsuarios = usuarioRepository.findAllByOrderByTotalXpDesc(PageRequest.of(0, limite));

        return java.util.stream.IntStream.range(0, topUsuarios.size())
                .mapToObj(i -> {
                    Usuario usuario = topUsuarios.get(i);
                    return new RankDTO(i + 1, usuario.getNome(),
                    levelService.calculaLevel(usuario.getTotalXp()), usuario.getTotalXp());

                })
                .toList();
    }
}
