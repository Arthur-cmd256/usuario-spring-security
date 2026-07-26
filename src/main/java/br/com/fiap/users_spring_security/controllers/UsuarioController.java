package br.com.fiap.users_spring_security.controllers;

import br.com.fiap.users_spring_security.dtos.UsuarioCadastradoDTO;
import br.com.fiap.users_spring_security.dtos.cadastrarUsuarioDTO;
import br.com.fiap.users_spring_security.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioCadastradoDTO cadastrarUsuario(@RequestBody cadastrarUsuarioDTO cadastrarUsuarioDTO) {
        return usuarioService.cadastrarUsuario(cadastrarUsuarioDTO);
    }
}
