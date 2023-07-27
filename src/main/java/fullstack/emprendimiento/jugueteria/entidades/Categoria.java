package fullstack.emprendimiento.jugueteria.entidades;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @NotBlank
    private String nombre;
 
    private String foto;
   
   @OneToMany(mappedBy = "categoria")
   @JsonManagedReference
    private List<Producto> productos;


    @Override
    public String toString() {
        return this.nombre;
    }
    
}


