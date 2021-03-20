package ec.puntonet.ticket;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ec.puntonet.ticket.dto.TicketDTO;
import ec.puntonet.ticket.service.ITicketService;
import ec.puntonet.ticket.utils.DealEnum;
import ec.puntonet.ticket.utils.PriorityEnum;
import ec.puntonet.ticket.utils.StateEnum;

@SpringBootTest
public class TicketServiceTest {

	@Autowired
	private ITicketService ticketService;

	@Test
	public void findAllTicket() {
		ticketService.findAll();
	}

	@Test
	public void findTicket() {
		ticketService.findId(1L);
	}

	@Test
	public void createTicket() {
		Date currentDate = new Date();
		TicketDTO ticketDTO = new TicketDTO();
		ticketDTO.setName("proyecto creado");
		ticketDTO.setDescription("proyecto creado");
		ticketDTO.setState(StateEnum.Open);
		ticketDTO.setPriority(PriorityEnum.low);
		ticketDTO.setDeal(DealEnum.mobile);
		ticketDTO.setUser("Marcelo Olalla");
		ticketDTO.setDueDate(currentDate);
		ticketService.create(ticketDTO);

	}

	@Test
	public void updateTicket() {
		Date currentDate = new Date();
		TicketDTO ticketDTO = new TicketDTO();
		ticketDTO.setName("proyecto creado");
		ticketDTO.setDescription("proyecto creado");
		ticketDTO.setState(StateEnum.Open);
		ticketDTO.setPriority(PriorityEnum.low);
		ticketDTO.setDeal(DealEnum.mobile);
		ticketDTO.setUser("Marcelo Olalla");
		ticketDTO.setDueDate(currentDate);
		;
		ticketService.update(1L, ticketDTO);

	}

}
