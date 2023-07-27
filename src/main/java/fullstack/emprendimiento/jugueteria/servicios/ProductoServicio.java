package fullstack.emprendimiento.jugueteria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.emprendimiento.jugueteria.entidades.*;
import fullstack.emprendimiento.jugueteria.repositorios.*;

import java.util.*;

@Service
public class ProductoServicio {
    @Autowired
    ProductoRepositorio productoRepositorio;

    public List<Producto> getAll() {
        List<Producto> lista = new ArrayList<Producto>();
        productoRepositorio.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }

    public Producto getById(Long id) {
        return productoRepositorio.findById(id).get();
    }

    public void save(Producto producto) {
        productoRepositorio.save(producto);
    }

    public void delete(Long id) {
        productoRepositorio.deleteById(id);
    }

}


