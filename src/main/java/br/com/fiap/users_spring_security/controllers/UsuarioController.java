package br.com.fiap.users_spring_security.controllers;

import br.com.fiap.users_spring_security.dtos.DadosPerfilDTO;
import br.com.fiap.users_spring_security.dtos.UsuarioCadastradoDTO;
import br.com.fiap.users_spring_security.dtos.CadastrarUsuarioDTO;
import br.com.fiap.users_spring_security.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioCadastradoDTO cadastrarUsuario(@RequestBody CadastrarUsuarioDTO cadastrarUsuarioDTO) {
        return usuarioService.cadastrarUsuario(cadastrarUsuarioDTO);
    }

    @GetMapping()
    public List<UsuarioCadastradoDTO> listarUsuarios() {
        return usuarioService.listarUsuario();
    }

    @GetMapping("/{id}")
    public UsuarioCadastradoDTO buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    @PatchMapping("/adicionar-perfil/{id}")
    public ResponseEntity<UsuarioCadastradoDTO> adicionarPerfil(@PathVariable Long id, @RequestBody @Valid DadosPerfilDTO dadosPerfil) {
        return ResponseEntity.ok( usuarioService.adicionarPerfil(id, dadosPerfil));
    }

    @PatchMapping("remover-perfil/{id}")
    public ResponseEntity<UsuarioCadastradoDTO> removerPerfil(@PathVariable Long id, @RequestBody @Valid DadosPerfilDTO dados){
        return ResponseEntity.ok(usuarioService.removerPerfil(id, dados));
    }
}
