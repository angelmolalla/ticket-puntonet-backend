package ec.puntonet.ticket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.puntonet.ticket.dao.ITicketDAO;
import ec.puntonet.ticket.dto.TicketDTO;
import ec.puntonet.ticket.exception.DataException;
import ec.puntonet.ticket.exception.NotFoundException;
import ec.puntonet.ticket.model.Ticket;
import ec.puntonet.ticket.utils.StateEnum;

@Service
public class TicketService implements ITicketService {

	@Autowired
	private ITicketDAO ticketDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> findAll() {
		return ticketDAO.findAllByEnable().orElse(new ArrayList<>());
	}

	@Override
	@Transactional(readOnly = true)
	public Ticket findId(Long id) {
		return ticketDAO.findByEnable(id).orElseThrow(() -> new NotFoundException(Ticket.class, "id", id));
	}

	@Override
	@Transactional
	public void create(TicketDTO ticketDTO) {
		try {
			Date currentDate = new Date();
			Ticket ticket = new Ticket();
			ticket.setName(ticketDTO.getName());
			ticket.setDescription(ticketDTO.getDescription());
			ticket.setState(ticketDTO.getState());
			ticket.setPriority(ticketDTO.getPriority());
			ticket.setUser(ticketDTO.getUser());
			ticket.setDeal(ticketDTO.getDeal());
			ticket.setDueDate(ticketDTO.getDueDate());
			ticket.setCreateDate(currentDate);
			ticketDAO.save(ticket);
		} catch (DataAccessException e) {
			throw new DataException(Ticket.class, "Error al crear ticket", e.getMostSpecificCause().getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Long id, TicketDTO ticketDTO) {
		try {
			Ticket ticket = findId(id);
			ticket.setName(ticketDTO.getName());
			ticket.setDescription(ticketDTO.getDescription());
			ticket.setState(ticketDTO.getState());
			ticket.setPriority(ticketDTO.getPriority());
			ticket.setDeal(ticketDTO.getDeal());
			ticket.setUser(ticketDTO.getUser());
			ticket.setDueDate(ticketDTO.getDueDate());
			ticketDAO.save(ticket);
		} catch (DataAccessException e) {
			throw new DataException(Ticket.class, "Error al modificar ticket", e.getMostSpecificCause().getMessage());
		}

	}

	@Override
	@Transactional
	public void delete(Long id) {
		try {
			Ticket ticket = findId(id);
			ticket.setState(StateEnum.Deleted);
			ticketDAO.save(ticket);
		} catch (DataAccessException e) {
			throw new DataException(Ticket.class, "Error al eliminar ticket", e.getMostSpecificCause().getMessage());
		}
	}

}
