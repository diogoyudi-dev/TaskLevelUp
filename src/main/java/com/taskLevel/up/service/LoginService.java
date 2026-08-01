package com.taskLevel.up.service;


import com.taskLevel.up.dto.autenticação.Registro;
import com.taskLevel.up.dto.autenticação.UsuarioToken;
import com.taskLevel.up.exception.BusinessException;
import com.taskLevel.up.models.Usuario;
import com.taskLevel.up.repository.UsuarioRepository;
import com.taskLevel.up.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Encoder;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public UsuarioToken registrar(Registro registro){
        if(usuarioRepository.existsByUsuario(registro.nomeUsuario())) {
            throw new BusinessException("Já existe um usuário com esse usuário");
        }
        if(usuarioRepository.existsByEmail(registro.email())){
            throw new BusinessException("Já existe um usuário com esse email");
        }

        Usuario usuario = Usuario.builder()
                .nome(registro.nomeUsuario())
                .email(registro.email())
                .senha(passwordEncoder.encode((registro.senha())))
                .build();

        usuarioRepository.save(usuario);

        String token = jwtService.geraToken(usuario);
        return UsuarioToken.bearer(token, usuario.getUsername(), jwtService.getExpirationMs());
    }
}
