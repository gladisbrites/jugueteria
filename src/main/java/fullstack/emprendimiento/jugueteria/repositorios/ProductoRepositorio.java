package fullstack.emprendimiento.jugueteria.repositorios;
import java.util.*;

import fullstack.emprendimiento.jugueteria.entidades.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProductoRepositorio extends CrudRepository<Producto, Long> {
            List<Producto> findByNombreContainingIgnoreCase(String nombre);


}

