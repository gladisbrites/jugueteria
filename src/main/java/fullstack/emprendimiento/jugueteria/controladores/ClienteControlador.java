package fullstack.emprendimiento.jugueteria.controladores;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import fullstack.emprendimiento.jugueteria.entidades.Cliente;
import fullstack.emprendimiento.jugueteria.servicios.ClienteServicio;


@RestController
@RequestMapping("clientes")
public class ClienteControlador {
    
    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping //peticion tipo get direccion: localhost:8080/clientes
    private ModelAndView index(){
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de clientes");
        maw.addObject("vista", "clientes/index");
        maw.addObject("clientes", clienteServicio.getAll());
        return maw;
    }

  
    @GetMapping("/{id}")//devuelve un cliente
    private ModelAndView one(@PathVariable("id") Long id){
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del cliente #" + id);
        maw.addObject("vista", "clientes/ver");
        maw.addObject("cliente", clienteServicio.getById(id));
        return maw;
    }


	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Cliente cliente){
        return this.editar(id, cliente, true);
    }
    
    public ModelAndView editar(@PathVariable("id") Long id, Cliente cliente, boolean estaPersistido){
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar cliente");
        maw.addObject("vista", "clientes/editar");
        maw.addObject("clientes", clienteServicio.getAll());
        
        if (estaPersistido)
            maw.addObject("cliente", clienteServicio.getById(id));

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id, @Valid Cliente cliente, BindingResult br, RedirectAttributes ra){
        if ( br.hasErrors() ) {
			return this.editar(id, cliente, false);
		}

        Cliente registro = clienteServicio.getById(id);
        registro.setNombres(cliente.getNombres() );
        registro.setDireccion(cliente.getDireccion() );
        registro.setCiudad(cliente.getCiudad() );
        registro.setProvincia(cliente.getProvincia() );
        registro.setCodPost(cliente.getCodPost() );
        registro.setCorreo(cliente.getCorreo() );
        registro.setTelefono(cliente.getTelefono() );
        ModelAndView maw = this.index();

        clienteServicio.save(registro);
        maw.addObject("exito", "Cliente  editado exitosamente");
		return maw;
    }

    @GetMapping("/crear")
	public ModelAndView crear(Cliente cliente){
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear cliente");
        maw.addObject("vista", "clientes/crear");
        maw.addObject("clientes", clienteServicio.getAll());
        return maw;
	}
    
	@PostMapping("/crear")
	public ModelAndView guardar(@Valid Cliente cliente, BindingResult br, RedirectAttributes ra){
		if ( br.hasErrors() ) {
			return this.crear(cliente);
		}
		clienteServicio.save(cliente);

        ModelAndView maw = this.index();
        maw.addObject("exito", "cliente guardado exitosamente");
		return maw;
	}

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {
        clienteServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Cliente borrado exitosamente");
		return maw;
    }



}