package com.kinexus.back.dto.empresas;

import java.time.LocalDateTime;

public class CreatePagoEmpresaDTO {
    public String planId;
    public Double monto;
    public String metodoPago;
    public String estadoPago;
    public LocalDateTime fechaPago;
}
