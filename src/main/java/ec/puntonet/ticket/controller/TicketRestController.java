package ec.puntonet.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.puntonet.ticket.dto.TicketDTO;
import ec.puntonet.ticket.model.Ticket;
import ec.puntonet.ticket.service.ITicketService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TicketRestController {

	@Autowired
	private ITicketService ticketService;

	@GetMapping("/ticket")
	public ResponseEntity<List<Ticket>> findAllTicke() {
		return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/ticket/{id}")
	public ResponseEntity<Ticket> findTicke(@PathVariable Long id) {
		return new ResponseEntity<>(ticketService.findId(id), HttpStatus.OK);
	}

	@PostMapping("/ticket")
	public ResponseEntity<HttpStatus> createTicket(@Validated @RequestBody TicketDTO ticketDTO) {
		ticketService.create(ticketDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/ticket/{id}")
	public ResponseEntity<HttpStatus> updateTicket(@PathVariable Long id, @Validated @RequestBody TicketDTO ticketDTO) {
		ticketService.update(id, ticketDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/ticket/{id}")
	public ResponseEntity<HttpStatus> deleteTicket(@PathVariable Long id) {
		ticketService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
