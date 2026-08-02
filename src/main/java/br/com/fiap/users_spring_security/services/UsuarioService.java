package br.com.fiap.users_spring_security.services;

import br.com.fiap.users_spring_security.dtos.UsuarioCadastradoDTO;
import br.com.fiap.users_spring_security.dtos.CadastrarUsuarioDTO;
import br.com.fiap.users_spring_security.entities.Usuario;
import br.com.fiap.users_spring_security.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.users_spring_security.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioCadastradoDTO cadastrarUsuario(CadastrarUsuarioDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado"));
    }
}
