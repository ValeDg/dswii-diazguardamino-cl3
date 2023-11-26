package pe.edu.cibertec.dswiicl3diazguardamino.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.dswiicl3diazguardamino.model.bd.Rol;
import pe.edu.cibertec.dswiicl3diazguardamino.model.bd.Usuario;
import pe.edu.cibertec.dswiicl3diazguardamino.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNomUsuario(username);
        return autenticacionUsuario(usuario,
                obtenerListaRolesUsuario(usuario.getRoles()));
    }
    public Usuario findByNomusuario(String usuario){
        return usuarioRepository.findByNomUsuario(usuario);
    }
    public List<GrantedAuthority> obtenerListaRolesUsuario(Set<Rol> listaRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Rol rol : listaRoles){
            roles.add(new SimpleGrantedAuthority(rol.getNomrol()));
        }
        return new ArrayList<GrantedAuthority>(roles);
    }
    private UserDetails autenticacionUsuario(Usuario usuario,
                                             List<GrantedAuthority> authorityList){
        return new User(usuario.getNomusuario(),
                usuario.getPassword(),
                usuario.getActivo(),
                true,
                true,
                true, authorityList);
    }
}