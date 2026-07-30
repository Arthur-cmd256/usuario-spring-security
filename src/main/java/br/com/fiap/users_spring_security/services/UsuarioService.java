package br.com.fiap.users_spring_security.services;

import br.com.fiap.users_spring_security.dtos.UsuarioCadastradoDTO;
import br.com.fiap.users_spring_security.dtos.cadastrarUsuarioDTO;
import br.com.fiap.users_spring_security.entities.Usuario;
import br.com.fiap.users_spring_security.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.users_spring_security.repositories.UsuarioRepository;
import br.com.fiap.users_spring_security.util.BCryptHashingUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioCadastradoDTO cadastrarUsuario(cadastrarUsuarioDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(BCryptHashingUtil.hashPassword(dto.senha()));
        usuarioRepository.save(usuario);
        return new UsuarioCadastradoDTO(usuario);
    }

    public List<UsuarioCadastradoDTO> listarUsuario() {
         return usuarioRepository.findAll().stream().map(UsuarioCadastradoDTO::new).toList();
    }

    public UsuarioCadastradoDTO buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).map(UsuarioCadastradoDTO::new)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encotrado na busca"));
    }
}
