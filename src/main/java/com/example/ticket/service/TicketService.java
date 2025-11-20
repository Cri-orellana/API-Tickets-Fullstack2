package com.example.ticket.service;

import com.example.ticket.model.Ticket;
import com.example.ticket.model.entity.TicketEntity;
import com.example.ticket.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElse(null);
    }

    public Ticket createTicket(Ticket ticket) {
        TicketEntity entityToSave = convertToTicketEntity(ticket);
        TicketEntity savedEntity = ticketRepository.save(entityToSave);
        return convertToTicket(savedEntity);
    }

    public Ticket updateTicket(Ticket ticket) {
        TicketEntity entityToUpdate = convertToTicketEntity(ticket);
        TicketEntity updatedEntity = ticketRepository.save(entityToUpdate);
        return convertToTicket(updatedEntity);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }
}
