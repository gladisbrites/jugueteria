package fullstack.emprendimiento.jugueteria.dto;

import fullstack.emprendimiento.jugueteria.validaciones.*;
import javax.validation.constraints.*;
import lombok.*;

@Data
@Confirmar
@EmailUnico
public class RegistroDto {

    @NotNull
    @NotEmpty(message = "Ingrese una dirección de correo electrónico")
    @Email(message = "Ingrese una dirección de correo electrónico válida")
    private String email;

    @NotNull
    @NotEmpty(message = "Ingrese una contraseña")
    private String password;

    private String confirmar;

}