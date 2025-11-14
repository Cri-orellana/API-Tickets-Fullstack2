package com.example.ticket.controller;

import com.example.ticket.model.Ticket;
import com.example.ticket.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Administracion Ticket", description = "CRUD Tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping
    @Operation(summary = "Obtener todos los tickets", description = "Devuelve una lista de todos los tickets disponibles")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{ticket}")
    @Operation(summary = "Obtener un ticket por ID", description = "Devuelve un ticket específico basado en su ID")
    public Ticket getTicketById(@PathVariable("ticket") Integer id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo ticket", description = "Crea un nuevo ticket con la información proporcionada")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/{ticket}")
    @Operation(summary = "Actualizar un ticket existente", description = "Actualiza la información del ticket")
    public Ticket updateTicket(@PathVariable("ticket") Integer id, @RequestBody Ticket ticket) {
        Ticket ticketRegistrado = ticketService.getTicketById(id);
        if (ticketRegistrado != null) {
            ticketRegistrado.setDescripcion(ticket.getDescripcion());
            ticketRegistrado.setEstado(ticket.getEstado());
            return ticketService.updateTicket(ticketRegistrado);
        }
        return null;
    }

    @DeleteMapping("/{ticket}")
    @Operation(summary = "Eliminar un ticket", description = "Elimina un ticket específico basado en su ID")
    public void deleteTicket(@PathVariable("ticket") Integer id) {
        ticketService.deleteTicket(id);
    }
}
