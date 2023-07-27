package fullstack.emprendimiento.jugueteria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.emprendimiento.jugueteria.entidades.Cliente;
import fullstack.emprendimiento.jugueteria.repositorios.ClienteRepositorio;

import java.util.*;


@Service
public class ClienteServicio {
    
    @Autowired //para inyectar una dependencia usamos esta anotacion
    ClienteRepositorio clienteRepositorio;

    public List<Cliente> getAll(){ //Esta funcion me va a devolver a todos los clientes
        List<Cliente> lista = new ArrayList<Cliente>();
        clienteRepositorio.findAll().forEach(registro -> lista.add(registro));//recorremos cada uno de los registros traidos y lo agregamos al listado
        return lista;
    }

    public Cliente getById(Long id){ //Esta funcion me va a devolver a un cliente
        return clienteRepositorio.findById(id).get();
    }

    public void save (Cliente client){ //Esta funcion guarda a un cliente
        clienteRepositorio.save(client);
    }

    public void delete(Long id){ //esta funcion borra un cliente 
        clienteRepositorio.deleteById(id);
    }
}

