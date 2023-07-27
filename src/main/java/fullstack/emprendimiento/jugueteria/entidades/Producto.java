package fullstack.emprendimiento.jugueteria.entidades;

//import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;


//import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo Obligatorio")
    private String nombre;

    @Positive(message = "Tiene que ingresar un precio positivo y que no sea cero")
    private double precio;

    private String foto;
    @NotBlank(message = "Campo obligatorio")
    private String descripcion;

   
    @ManyToOne()
    @NotNull(message = "Debe seleccionar una categoría")
    private Categoria categoria;

    @ManyToOne()
    @NotNull(message = "Debe seleccionar un rango de edades")
    private Edad edad;

    @Override
    public String toString() {
        return this.nombre;
    }

}

