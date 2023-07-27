package fullstack.emprendimiento.jugueteria.controladores;

import fullstack.emprendimiento.jugueteria.entidades.*;
import fullstack.emprendimiento.jugueteria.servicios.*;
import java.io.*;
import java.nio.file.Paths;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("categorias")
public class CategoriaControlador implements WebMvcConfigurer {

	@Autowired
    CategoriaServicio categoriaServicio;
  
      
    @Autowired
    ProductoServicio productoServicio;

  	@GetMapping
    private ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Lista de categorias");
        maw.addObject("vista", "categorias/index");
        maw.addObject("categorias", categoriaServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle de la categoria #" + id);
        maw.addObject("vista", "categorias/ver");

        Categoria categoria =categoriaServicio.getById(id);
        maw.addObject("categoria", categoria);
        maw.addObject("productos", categoria.getProductos());
        return maw;
    }


	@GetMapping("/crear")
	public ModelAndView crear(Categoria categoria)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear categoria");
        maw.addObject("vista", "categorias/crear");
        maw.addObject("productos", productoServicio.getAll());
        maw.addObject("categorias", categoriaServicio.getAll());
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo, @Valid Categoria categoria, BindingResult br, RedirectAttributes ra)
    {
        System.out.println(archivo.isEmpty());
        if ( archivo.isEmpty() )
			br.reject("archivo", "Por favor, cargue una imagen"); 

		if ( br.hasErrors() ) {
			return this.crear(categoria);
		}

		categoriaServicio.save(categoria);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = categoria.getId() + extension;
        String path = Paths.get("src/main/resources/static/images/categorias", foto).toAbsolutePath().toString();
        ModelAndView maw = this.index();

        try {
            archivo.transferTo( new File(path) );
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        categoria.setFoto(foto);
        categoriaServicio.save(categoria);
        maw.addObject("exito", "Categoria guardada exitosamente");
		return maw;
	}

	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Categoria categoria)
    {
        return this.editar(id, categoria, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Categoria categoria, boolean estaPersistido)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar categoria");
        maw.addObject("vista", "categorias/editar");
        maw.addObject("productos", productoServicio.getAll());
        maw.addObject("categorias", categoriaServicio.getAll());
     
        if (estaPersistido)
            maw.addObject("categoria", categoriaServicio.getById(id));
        else
            categoria.setFoto( categoriaServicio.getById(id).getFoto() );

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id,
    @RequestParam(value = "archivo", required = false) MultipartFile archivo,
    @Valid Categoria categoria, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
			return this.editar(id, categoria, false);
		}

        Categoria registro = categoriaServicio.getById(id);
        registro.setNombre(categoria.getNombre() );
        registro.setProductos( categoria.getProductos() );   
        ModelAndView maw = this.index();

        if ( ! archivo.isEmpty() ) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = categoria.getId() + extension;
            String path = Paths.get("src/main/resources/static/images/categorias", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo( new File(path) );
            } catch (Exception e) {
                maw.addObject("error", "No se pudo guardar la imagen");
                return maw;
            }

            registro.setFoto(foto);
        }

        categoriaServicio.save(registro);
        maw.addObject("exito", "Categoria  editada exitosamente");
		return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {      
        ModelAndView maw = this.index();
        Categoria categoria = categoriaServicio.getById(id);
        if (categoria.getProductos().size()>0)
        {
            maw.addObject("error", "no se puede borrar la categoría porque tiene registros asociados");
        }else{  
            categoriaServicio.delete(id);

            maw.addObject("exito", "Categoria borrada exitosamente");
        }
        return maw;
    }
    
}

