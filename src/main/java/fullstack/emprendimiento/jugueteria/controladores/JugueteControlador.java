package fullstack.emprendimiento.jugueteria.controladores;

    import fullstack.emprendimiento.jugueteria.repositorios.*;
import fullstack.emprendimiento.jugueteria.servicios.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("juguetes")
public class JugueteControlador {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    ProductoRepositorio productoRepositorio;
    // listado de Productos
    @RequestMapping
    public ModelAndView ejemplo() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Juguetes");
        maw.addObject("vista", "juguetes/todos");
        maw.addObject("productos", productoServicio.getAll());
        return maw;
    }

    // Mostrar Un Producto
    @GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del juguete #" + id);
        maw.addObject("vista", "juguetes/ver");
        maw.addObject("producto", productoServicio.getById(id));
        return maw;
    }
    @PostMapping("/buscar")
    private ModelAndView buscar(@RequestParam String busqueda) {
    ModelAndView maw = new ModelAndView();
    maw.setViewName("fragments/base");
    maw.addObject("titulo", "Resultados de la búsqueda");
    maw.addObject("vista", "juguetes/todos");
    maw.addObject("productos", productoRepositorio.findByNombreContainingIgnoreCase(busqueda));
    return maw;
     }

}

