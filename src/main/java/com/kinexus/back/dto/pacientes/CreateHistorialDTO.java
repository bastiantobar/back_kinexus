package com.kinexus.back.dto.pacientes;

import java.util.List;

public class CreateHistorialDTO {
    public List<CreatePagoDTO> pagos;
    public String infoBasicaPaciente;
    public List<PlanResumenDTO> planes;

    public static class PlanResumenDTO {
        public String fecha;
        public String tituloPlan;
        public String diagnostico;
        public String informeAlta;
    }
}
