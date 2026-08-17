package br.com.fiap.users_spring_security.controllers;

import br.com.fiap.users_spring_security.dtos.DadosPerfilDTO;
import br.com.fiap.users_spring_security.dtos.UsuarioCadastradoDTO;
import br.com.fiap.users_spring_security.dtos.CadastrarUsuarioDTO;
import br.com.fiap.users_spring_security.entities.Usuario;
import br.com.fiap.users_spring_security.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<UsuarioCadastradoDTO> cadastrarUsuario(@RequestBody CadastrarUsuarioDTO cadastrarUsuarioDTO) {
        return ResponseEntity.ok(usuarioService.cadastrarUsuario(cadastrarUsuarioDTO));
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioCadastradoDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCadastradoDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarUsuarioPorId(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        usuarioService.deletarUsuarioPorID(id, usuario);
        return ResponseEntity.noContent().build();
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
