package ec.puntonet.ticket.service;

import java.util.List;

import ec.puntonet.ticket.dto.TicketDTO;
import ec.puntonet.ticket.model.Ticket;

public interface ITicketService {

	public List<Ticket> findAll();

	public Ticket findId(Long id);

	public void create(TicketDTO ticketDTO);

	public void update(Long id, TicketDTO ticketDTO);

	public void delete(Long id);

}
