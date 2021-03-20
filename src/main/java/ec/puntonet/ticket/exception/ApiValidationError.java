package ec.puntonet.ticket.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {
	private String field;
	private String message;
	private String object;
	private Object rejectedValue;

	ApiValidationError(String message, String object) {
		this.message = message;
		this.object = object;
	}
}