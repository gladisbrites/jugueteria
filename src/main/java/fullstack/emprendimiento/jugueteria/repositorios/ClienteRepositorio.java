package fullstack.emprendimiento.jugueteria.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fullstack.emprendimiento.jugueteria.entidades.Cliente;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, Long>{
    
}

