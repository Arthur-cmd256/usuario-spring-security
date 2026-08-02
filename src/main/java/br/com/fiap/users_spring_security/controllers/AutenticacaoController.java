package br.com.fiap.users_spring_security.controllers;

import br.com.fiap.users_spring_security.dtos.LoginUsuarioDTO;
import br.com.fiap.users_spring_security.dtos.TokenResponseDTO;
import br.com.fiap.users_spring_security.entities.Usuario;
import br.com.fiap.users_spring_security.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager,  TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> efetuarLogin (@Valid @RequestBody LoginUsuarioDTO dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String tokenAcesso = tokenService.generateToken(( Usuario) Objects.requireNonNull(authentication.getPrincipal()));
        return ResponseEntity.ok(new TokenResponseDTO(tokenAcesso));
    }
}
