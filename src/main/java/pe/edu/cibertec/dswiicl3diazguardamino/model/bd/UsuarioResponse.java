package pe.edu.cibertec.dswiicl3diazguardamino.model.bd;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsuarioResponse {
    private Integer idusuario;
    private String nomusuario;
    private String token;
}
