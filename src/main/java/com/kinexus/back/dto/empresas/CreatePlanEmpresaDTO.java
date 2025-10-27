package com.kinexus.back.dto.empresas;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CreatePlanEmpresaDTO {
    public String nombre;
    public String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaTermino;
    public Double valor;
    public Integer numeroSesiones;
    public String empresaId;
}
