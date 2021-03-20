package ec.puntonet.ticket.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ec.puntonet.ticket.model.Ticket;


public interface ITicketDAO extends CrudRepository<Ticket, Long> {

	@Query(value = "SELECT u FROM Ticket u WHERE u.state != 0")
	public Optional<List<Ticket>> findAllByEnable();

	@Query(value = "SELECT u FROM Ticket u WHERE u.state != 0 and u.id=?1")
	public Optional<Ticket> findByEnable(Long id);
	
}
