package fullstack.emprendimiento.jugueteria.servicios;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.emprendimiento.jugueteria.entidades.Edad;
import fullstack.emprendimiento.jugueteria.repositorios.EdadRepositorio;

@Service
public class EdadServicio {

    @Autowired
    EdadRepositorio edadRepositorio;

    public List<Edad> getAll() {
        List<Edad> lista = new ArrayList<Edad>();
        // edadRepositorio.findAll().forEach(registro -> lista.add(registro));
        lista = (ArrayList<Edad>) edadRepositorio.findAll();
        return lista;
    }

    public Edad getById(Long id) {
        return edadRepositorio.findById(id).get();
    }

    public void save(Edad edad) {
        edadRepositorio.save(edad);
    }

    public void delete(Long id) {
        edadRepositorio.deleteById(id);
    }

}


