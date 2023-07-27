package fullstack.emprendimiento.jugueteria.controladores;

import fullstack.emprendimiento.jugueteria.entidades.*;
import fullstack.emprendimiento.jugueteria.servicios.*;
//import java.io.*;
//import java.nio.file.Paths;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("edades")
public class EdadControlador implements WebMvcConfigurer {

    @Autowired
    EdadServicio edadServicio;

    @GetMapping
    private ModelAndView index() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Lista de edades");
        maw.addObject("vista", "edades/index");
        maw.addObject("edades", edadServicio.getAll());
        return maw;
    }

    @GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle de la edad #" + id);
        
        maw.addObject("vista", "edades/ver");
        maw.addObject("edades", edadServicio.getById(id));
        return maw;
    }

    @GetMapping("/crear")
    public ModelAndView crear(Edad edad) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear edades");
        maw.addObject("vista", "edades/crear");
        maw.addObject("edades", edadServicio.getAll());
        return maw;
    }

    @PostMapping("/crear")
    public ModelAndView guardar(@Valid Edad edad, BindingResult br, RedirectAttributes ra) {

        if (br.hasErrors()) {
            return this.crear(edad);
        }

        edadServicio.save(edad);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Edad guardada exitosamente");
        return maw;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Edad edad) {
        return this.editar(id, edad, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Edad edad, boolean estaPersistido) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar edades");
        maw.addObject("vista", "edades/editar");
        maw.addObject("edades", edadServicio.getAll());

        if (estaPersistido)
            maw.addObject("edad", edadServicio.getById(id));

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id, @Valid Edad edad, BindingResult br,
            RedirectAttributes ra) {
        if (br.hasErrors()) {
            return this.editar(id, edad, false);
        }

        Edad registro = edadServicio.getById(id);
        registro.setEdades(edad.getEdades());

        ModelAndView maw = this.index();

        edadServicio.save(registro);
        maw.addObject("exito", "Edad editada exitosamente");
        return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id) {
        ModelAndView maw = this.index();
        Edad edad = edadServicio.getById(id);
        if (edad.getProductos().size()>0)
        {
            maw.addObject("error", "no se puede borrar la edad porque tiene registros asociados");
        }else{  
            edadServicio.delete(id);

            maw.addObject("exito", "Edad borrada exitosamente");
        }
    return maw;
    }
}
