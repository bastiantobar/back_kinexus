package com.kinexus.back.dto.empresas;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateUsuarioEmpresaDTO {
    public String sucursalId;
    public String nombre;
    public String genero;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaNacimiento;
    public String cargo;
    public String discapacidad;
}
