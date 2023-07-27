package fullstack.emprendimiento.jugueteria.repositorios;

import fullstack.emprendimiento.jugueteria.entidades.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface EdadRepositorio extends CrudRepository<Edad, Long> {

}
