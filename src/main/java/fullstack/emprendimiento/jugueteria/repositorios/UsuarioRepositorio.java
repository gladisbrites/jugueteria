package fullstack.emprendimiento.jugueteria.repositorios;

import fullstack.emprendimiento.jugueteria.entidades.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends CrudRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    boolean existsByEmail(String email);
    
}