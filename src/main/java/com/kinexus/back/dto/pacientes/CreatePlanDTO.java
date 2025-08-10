package com.kinexus.back.dto.pacientes;

import java.util.List;

public class CreatePlanDTO {
    public Integer asistencia;
    public Integer numeroSesiones;
    public String infoPlan;
    public String evaluacionInicial;
    public String evaluacionFinal;
    public List<CreateSesionDTO> sesiones;
    public List<CreatePagoDTO> pagos;
}
