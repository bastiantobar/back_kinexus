package com.kinexus.back.dto.pacientes;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateCitaDTO {
    public UUID pacienteId;
    public UUID adminId;
    public LocalDateTime fechaHora;
    public String estado;
}
