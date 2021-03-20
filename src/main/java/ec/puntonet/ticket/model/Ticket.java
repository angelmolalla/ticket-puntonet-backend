package ec.puntonet.ticket.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import ec.puntonet.ticket.utils.DealEnum;
import ec.puntonet.ticket.utils.PriorityEnum;
import ec.puntonet.ticket.utils.StateEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "PNTT")
public class Ticket implements Serializable {

	@Id
	@Column(name = "PNTT_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "PNTT_NAME", nullable = false)
	private String name;

	@Column(name = "PNTT_DESC")
	private String description;

	@Column(name = "PNTT_STAT", nullable = false)
	private StateEnum state;

	@Column(name = "PNTT_DEAL", nullable = false)
	private DealEnum deal;

	@Column(name = "PNTT_PRIO", nullable = false)
	private PriorityEnum priority;

	@Column(name = "PNTT_USER", nullable = false)
	private String user;

	@Column(name = "PNTT_DUE_DATE", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "PNTT_CRE_DATE", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
	@Temporal(TemporalType.DATE)
	private Date createDate;

	private static final long serialVersionUID = 1L;
}
