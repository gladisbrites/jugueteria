package fullstack.emprendimiento.jugueteria.controladores;

import fullstack.emprendimiento.jugueteria.repositorios.*;
import fullstack.emprendimiento.jugueteria.servicios.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeControlador {
  

    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    CategoriaRepositorio categoriaRepositorio;

 
    @RequestMapping("/")
    public ModelAndView home()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Inicio");
        maw.addObject("vista", "inicio/home");
        maw.addObject("categorias", categoriaServicio.getAll());
        System.out.println(categoriaServicio.getAll());
      
        return maw;  
    }

}