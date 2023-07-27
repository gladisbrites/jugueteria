package fullstack.emprendimiento.jugueteria.controladores;

import java.io.*;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fullstack.emprendimiento.jugueteria.entidades.*;
import fullstack.emprendimiento.jugueteria.servicios.*;
//import fullstack.emprendimiento.jugueteria.repositorios.*;;

@RestController
@RequestMapping("productos")
public class ProductoControlador implements WebMvcConfigurer {
private final Logger LOGGER=LoggerFactory.getLogger(ProductoControlador.class);
    @Autowired
    ProductoServicio productoServicio;
    @Autowired
    EdadServicio edadServicio;
    @Autowired
    CategoriaServicio categoriaServicio;

    // listado de Productos
    @GetMapping
    private ModelAndView index() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de productos");
        maw.addObject("vista", "productos/index");
        maw.addObject("productos", productoServicio.getAll());
        maw.addObject("categorias", categoriaServicio.getAll());
        return maw;
    }

    // Mostrar Un Producto
    @GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del producto #" + id);
        maw.addObject("vista", "productos/ver");
        maw.addObject("producto", productoServicio.getById(id));
        maw.addObject("categorias", productoServicio.getById(id));
        return maw;
    }

    // Vista del producto a Crear
    @GetMapping("/crear")
    public ModelAndView crear(Producto producto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear producto");
        maw.addObject("vista", "productos/crear");
        maw.addObject("categorias", categoriaServicio.getAll());
        maw.addObject("edades", edadServicio.getAll());
        return maw;
    }

    // Creación de un Producto
    @PostMapping("/crear")
    public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo, @Valid Producto producto,
            BindingResult br, RedirectAttributes ra) {
        System.out.println(archivo.isEmpty());
        if (archivo.isEmpty())
            br.reject("archivo", "Por favor, cargue una imagen");

        if (br.hasErrors()) {
            return this.crear(producto);
        }

        productoServicio.save(producto);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = producto.getId() + extension;
        String path = Paths.get("src/main/resources/static/images/productos", foto).toAbsolutePath().toString();
        ModelAndView maw = this.index();

        try {
            archivo.transferTo(new File(path));
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        producto.setFoto(foto);
        productoServicio.save(producto);

        LOGGER.info("este es el objeto producto {}", producto);
        
        maw.addObject("exito", "Producto guardado exitosamente");
        return maw;
    }

    // Lista de producto a editar
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Producto producto) {
        return this.editar(id, producto, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Producto producto, boolean estaPersistido) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar producto");
        maw.addObject("vista", "productos/editar");
        maw.addObject("productos", productoServicio.getAll());
        maw.addObject("categorias", categoriaServicio.getAll());
        maw.addObject("edades", edadServicio.getAll());

        if (estaPersistido)
            maw.addObject("producto", productoServicio.getById(id));
        else
            producto.setFoto(productoServicio.getById(id).getFoto());

        return maw;
    }

    // Editar Producto
    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @Valid Producto producto, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return this.editar(id, producto, false);
        }

        Producto registro = productoServicio.getById(id);
        registro.setNombre(producto.getNombre());
        registro.setPrecio(producto.getPrecio());
        registro.setDescripcion(producto.getDescripcion());
        registro.setCategoria( producto.getCategoria()); 
        registro.setEdad( producto.getEdad()); 
        ModelAndView maw = this.index();

        if (!archivo.isEmpty()) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = producto.getId() + extension;
            String path = Paths.get("src/main/resources/static/images/productos", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo(new File(path));
            } catch (Exception e) {
                maw.addObject("error", "No se pudo guardar la imagen");
                return maw;
            }

            registro.setFoto(foto);
        }

        productoServicio.save(registro);
        maw.addObject("exito", "Producto editado exitosamente");
        return maw;
    }

    // Borrar Un Producto
    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id) {
        productoServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Producto borrado exitosamente");
        return maw;
    }

}


