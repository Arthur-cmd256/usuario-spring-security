package br.com.fiap.users_spring_security.services;

import br.com.fiap.users_spring_security.dtos.DadosPerfilDTO;
import br.com.fiap.users_spring_security.dtos.UsuarioCadastradoDTO;
import br.com.fiap.users_spring_security.dtos.CadastrarUsuarioDTO;
import br.com.fiap.users_spring_security.entities.Perfil;
import br.com.fiap.users_spring_security.entities.Usuario;
import br.com.fiap.users_spring_security.entities.enums.PerfilNome;
import br.com.fiap.users_spring_security.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.users_spring_security.repositories.PerfilRepository;
import br.com.fiap.users_spring_security.repositories.UsuarioRepository;
import br.com.fiap.users_spring_security.util.BCryptHashingUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @Transactional
    public UsuarioCadastradoDTO cadastrarUsuario(CadastrarUsuarioDTO dto){
        Perfil perfil = perfilRepository.findByNome(PerfilNome.USER);

        Usuario usuario = new Usuario(
                dto.nome(),
                dto.email(),
                BCryptHashingUtil.hashPassword(dto.senha()),
                perfil
        );
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

    @Transactional
    public UsuarioCadastradoDTO adicionarPerfil(Long id, DadosPerfilDTO dadosPerfil) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado"));
        Perfil perfil = perfilRepository.findByNome(dadosPerfil.perfilNome());
        usuario.adicionarPefil(perfil);
        usuarioRepository.save(usuario);
        return new UsuarioCadastradoDTO(usuario);
    }

    @Transactional
    public UsuarioCadastradoDTO removerPerfil(Long id, @Valid DadosPerfilDTO dados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado"));
        Perfil perfil = perfilRepository.findByNome(dados.perfilNome());
        usuario.removePerfil(perfil);
        usuarioRepository.save(usuario);
        return new UsuarioCadastradoDTO(usuario);
    }
}
