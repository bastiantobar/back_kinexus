package com.kinexus.back.dto.empresas;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CreatePagoEmpresaDTO {
    public String planId;
    public Double monto;
    public String metodoPago;
    public String estadoPago;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaPago;
}
