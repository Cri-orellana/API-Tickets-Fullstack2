package com.example.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Integer id;
    private String nombre;
    private String email;
    private String asunto;
    private String descripcion;
    private String estado;
}