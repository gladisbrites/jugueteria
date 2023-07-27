package fullstack.emprendimiento.jugueteria.entidades;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

@Entity //para decirle que nuestra clase es una entidad
@NoArgsConstructor //constructor sin parametros, con esta anotacion lombok agrega constructor sin parametros
@AllArgsConstructor //constructor con todos los parametros
@Data
@Builder
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //le decimos que el id va a ser autoincremental
    private Long id;

    @NotBlank(message = "Campo Obligatorio")
    @Size(max = 250, message = "Nombre muy largo")
    private String nombres;


    @NotNull(message = "Campo obligatorio")
    private String direccion;

    @NotNull(message = "Campo obligatorio")
    private String ciudad;

    @NotNull(message = "Campo obligatorio")
    private String provincia;

    @NotNull(message = "Campo obligatorio")
    private Integer codPost;

    @NotNull(message = "Campo obligatorio")
    private String correo;

    @NotNull(message = "Campo obligatorio")
    private String telefono;

}


