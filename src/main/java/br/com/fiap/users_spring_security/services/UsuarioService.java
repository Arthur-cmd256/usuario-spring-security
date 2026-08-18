package br.com.fiap.users_spring_security.services;

import br.com.fiap.users_spring_security.dtos.DadosPerfilDTO;
import br.com.fiap.users_spring_security.dtos.UsuarioCadastradoDTO;
import br.com.fiap.users_spring_security.dtos.CadastrarUsuarioDTO;
import br.com.fiap.users_spring_security.entities.Perfil;
import br.com.fiap.users_spring_security.entities.Usuario;
import br.com.fiap.users_spring_security.entities.enums.PerfilNome;
import br.com.fiap.users_spring_security.exceptions.RegraDeNegocioException;
import br.com.fiap.users_spring_security.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.users_spring_security.repositories.PerfilRepository;
import br.com.fiap.users_spring_security.repositories.UsuarioRepository;
import br.com.fiap.users_spring_security.util.BCryptHashingUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final RoleHierarchy  roleHierarchy;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, RoleHierarchy roleHierarchy) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.roleHierarchy = roleHierarchy;
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

    public void deletarUsuarioPorID(Long id, Usuario usuarioLogado) {
        Usuario usuarioASerDeletado = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado"));
        if (usuarioNaoTemPermissoes(usuarioLogado, usuarioASerDeletado, "ROLE_ADMIN"))
            throw new RegraDeNegocioException("O usuario não tem permissão de delete");
        usuarioRepository.deleteById(id);
    }

    private boolean usuarioNaoTemPermissoes(Usuario usuarioLogado, Usuario usuarioASerDeletado, String perfilDesejado) {
        if (usuarioLogado.getId().equals(usuarioASerDeletado.getId()))
            return false;
        for (GrantedAuthority grantedAuthority : usuarioLogado.getAuthorities()) {
            var autoridadesAlcancaveis = roleHierarchy.getReachableGrantedAuthorities(List.of(grantedAuthority));

            for (GrantedAuthority perfil : autoridadesAlcancaveis) {
                if (perfil.getAuthority().equalsIgnoreCase(perfilDesejado))
                    return false;
            }
        }
        return true;
    }

}
