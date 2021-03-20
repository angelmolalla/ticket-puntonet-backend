package ec.puntonet.ticket.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import ec.puntonet.ticket.utils.DealEnum;
import ec.puntonet.ticket.utils.PriorityEnum;
import ec.puntonet.ticket.utils.StateEnum;
import lombok.Data;

@Data
public class TicketDTO {

	@NotBlank(message = "El nombre del ticket esta vacio")
	private String name;

	private String description;

	@NotNull(message = "El estado esta vacio")
	private StateEnum state;

	@NotNull(message = "La prioridad esta vacia")
	private PriorityEnum priority;

	@NotNull(message = "La categoria esta vacia")
	private DealEnum deal;

	@NotBlank(message = "El usuario esta vacio")
	private String user;

	@NotNull(message = "La fecha de vencimiento esta vacia")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
	@Temporal(TemporalType.DATE)
	private Date dueDate;

}
