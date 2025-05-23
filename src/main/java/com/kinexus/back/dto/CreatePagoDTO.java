package com.kinexus.back.dto;

import java.util.UUID;

public class CreatePagoDTO {
    public UUID usuarioId;
    public Integer monto;
    public String metodoPago;
    public String estadoPago;
}
