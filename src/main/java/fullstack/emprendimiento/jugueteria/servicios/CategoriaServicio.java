package fullstack.emprendimiento.jugueteria.servicios;
import fullstack.emprendimiento.jugueteria.repositorios.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.emprendimiento.jugueteria.entidades.*;

@Service
public class CategoriaServicio {

    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    
    public List<Categoria> getAll()
    {
        List<Categoria> lista = new ArrayList<Categoria>();
        categoriaRepositorio.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
   

    public Categoria getById(Long id)
    {
        return categoriaRepositorio.findById(id).get();
    }

    public void save(Categoria categoria)
    {
        categoriaRepositorio.save(categoria);
    }

    public void delete(Long id)
    {
        categoriaRepositorio.deleteById(id);
    }
    
}


