package ec.puntonet.ticket.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import lombok.Data;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiError {
	private String debugMessage;
	private String message;
	private HttpStatus status;
	private List<ApiSubError> subErrors;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	private void addSubError(ApiSubError subError) {
		if (subErrors == null) {
			subErrors = new ArrayList<>();
		}
		subErrors.add(subError);
	}

	/**
	 * Utility method for adding error of ConstraintViolation. Usually when
	 * a @Validated validation fails.
	 *
	 * @param cv the ConstraintViolation
	 */
	private void addValidationError(ConstraintViolation<?> cv) {
		this.addValidationError(((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getMessage(),
				cv.getRootBeanClass().getSimpleName(), cv.getInvalidValue());
	}

	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getObjectName(),
				fieldError.getRejectedValue());
	}

	public void addValidationError(List<ObjectError> globalErrors) {
		globalErrors.forEach(this::addValidationError);
	}

	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}

	private void addValidationError(String message, String object) {
		addSubError(new ApiValidationError(message, object));
	}

	private void addValidationError(String field, String message, String object, Object rejectedValue) {
		addSubError(new ApiValidationError(field, message, object, rejectedValue));
	}

	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(this::addValidationError);
	}

	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
	}
}