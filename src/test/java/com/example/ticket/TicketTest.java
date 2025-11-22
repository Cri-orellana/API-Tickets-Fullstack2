package com.example.ticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.example.ticket.model.Ticket;
import com.example.ticket.model.entity.TicketEntity;
import com.example.ticket.repository.TicketRepository;
import com.example.ticket.service.TicketService;

public class TicketTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketEntity ticketEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket();
        ticket.setId(1);
        ticket.setDescripcion("Ticket de prueba");
        ticket.setEstado("ABIERTO");

        ticketEntity = new TicketEntity();
        ticketEntity.setId(1);
        ticketEntity.setDescripcion("Ticket de prueba");
        ticketEntity.setEstado("ABIERTO");
    }

    @Test
    public void testCrearTicket() {
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);

        Ticket resultado = ticketService.createTicket(ticket);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Ticket de prueba", resultado.getDescripcion());
    }

    @Test
    public void testObtenerTicketPorId_Existe() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticketEntity));

        Ticket resultado = ticketService.getTicketById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    public void testObtenerTicketPorId_NoExiste() {
        when(ticketRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            ticketService.getTicketById(99);
        });
    }

    @Test
    public void testActualizarTicket_Existe() {
        when(ticketRepository.existsById(1)).thenReturn(true);
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);

        Ticket resultado = ticketService.updateTicket(ticket);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    public void testActualizarTicket_NoExiste() {
        ticket.setId(99);
        when(ticketRepository.existsById(99)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            ticketService.updateTicket(ticket);
        });
    }

    @Test
    public void testEliminarTicket_Existe() {
        when(ticketRepository.existsById(1)).thenReturn(true);
        doNothing().when(ticketRepository).deleteById(1);

        ticketService.deleteTicket(1);

        verify(ticketRepository, times(1)).deleteById(1);
    }

    @Test
    public void testEliminarTicket_NoExiste() {
        when(ticketRepository.existsById(99)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            ticketService.deleteTicket(99);
        });

        verify(ticketRepository, never()).deleteById(99);
    }
}