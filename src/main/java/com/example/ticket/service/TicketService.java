package com.example.ticket.service;

import com.example.ticket.model.Ticket;
import com.example.ticket.model.entity.TicketEntity;
import com.example.ticket.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket convertToTicket(TicketEntity entity) {
        if (entity == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());

        ticket.setNombre(entity.getNombre());
        ticket.setEmail(entity.getEmail());
        ticket.setAsunto(entity.getAsunto());

        ticket.setDescripcion(entity.getDescripcion());
        ticket.setEstado(entity.getEstado());
        return ticket;
    }

    private TicketEntity convertToTicketEntity(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());

        entity.setNombre(ticket.getNombre());
        entity.setEmail(ticket.getEmail());
        entity.setAsunto(ticket.getAsunto());

        entity.setDescripcion(ticket.getDescripcion());
        entity.setEstado(ticket.getEstado());
        return entity;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::convertToTicket)
                .collect(Collectors.toList());
    }

    public Ticket getTicketById(Integer id) {
        return ticketRepository.findById(id)
                .map(this::convertToTicket)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket no encontrado con ID: " + id));
    }

    public Ticket createTicket(Ticket ticket) {
        TicketEntity entityToSave = convertToTicketEntity(ticket);
        if (entityToSave.getEstado() == null) {
            entityToSave.setEstado("ABIERTO");
        }
        TicketEntity savedEntity = ticketRepository.save(entityToSave);
        return convertToTicket(savedEntity);
    }

    public Ticket updateTicket(Ticket ticket) {
        if (!ticketRepository.existsById(ticket.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se puede actualizar. ID no existe: " + ticket.getId());
        }
        TicketEntity entityToUpdate = convertToTicketEntity(ticket);
        TicketEntity updatedEntity = ticketRepository.save(entityToUpdate);
        return convertToTicket(updatedEntity);
    }

    public void deleteTicket(Integer id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar. ID no existe: " + id);
        }
        ticketRepository.deleteById(id);
    }
}