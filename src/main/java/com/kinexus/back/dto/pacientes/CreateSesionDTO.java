package com.kinexus.back.dto.pacientes;

import java.time.LocalDateTime;

public class CreateSesionDTO {
    public LocalDateTime fechaHora;
    public Boolean asistencia;
    public Boolean cancelado;
    public String evolucionClinica;
}
