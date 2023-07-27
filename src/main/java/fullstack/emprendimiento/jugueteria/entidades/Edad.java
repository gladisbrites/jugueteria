package fullstack.emprendimiento.jugueteria.entidades;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "edades")
public class Edad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo Obligatorio")
    private String edades;

    @OneToMany(mappedBy = "edad")
    @JsonManagedReference
    private List<Producto> productos;

    @Override
    public String toString() {
        return this.edades;
    }

}

