package fullstack.emprendimiento.jugueteria.servicios;

import java.util.*;

/*import javax.transaction.Transactional;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fullstack.emprendimiento.jugueteria.entidades.Rol;
import fullstack.emprendimiento.jugueteria.entidades.Usuario;
import fullstack.emprendimiento.jugueteria.repositorios.*;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

   /*    @Autowired
    private BCryptPasswordEncoder codificador;*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        List<GrantedAuthority> ga = buildAuthorities(usuario.getRol());
        return buildUser(usuario, ga);
    }

    public User buildUser(Usuario usuario, List<GrantedAuthority> ga) {
        return new User(usuario.getEmail(), usuario.getPassword(), ga);
    }

    public List<GrantedAuthority> buildAuthorities(Rol rol) {
        List<GrantedAuthority> ga = new ArrayList<>();
        ga.add( new SimpleGrantedAuthority("ROLE_" + rol.getNombre()) );
        return ga;
    }

   /*  @Transactional
    public void registro(Usuario usuario) {
        if (usuarioRepositorio.existsByEmail(usuario.getEmail()))
            throw new IllegalArgumentException("Ya existe un usuario con este email");

        usuario.setPassword( codificador.encode(usuario.getPassword()) );
        usuario.setRol(rolRepositorio.findByNombre("Usuario").orElseThrow(() -> new IllegalArgumentException("Error al crear usuario")));
        usuarioRepositorio.save(usuario);
    }*/

}