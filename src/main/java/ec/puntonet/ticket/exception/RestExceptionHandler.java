package ec.puntonet.ticket.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<Object> exception(DataAccessException ex) {
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
		apiError.setMessage("Error en la Base de Datos");
		apiError.setDebugMessage(ex.getMessage());
		logger.warn("Error en la Base de Datos:" + ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(BadRequest.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequest ex) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		apiError.setMessage("Error en la Entrada de Datos");
		apiError.setDebugMessage(ex.getMessage());
		logger.warn("Error en la Entrada de Datos:" + ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		apiError.setMessage("Error de validacion");
		apiError.addValidationErrors(ex.getConstraintViolations());
		logger.warn("Error de validacion:" + ex.getConstraintViolations());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(DataException.class)
	public ResponseEntity<Object> handleDataException(DataException ex) {
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
		apiError.setMessage("Error en la Base de Datos");
		apiError.setDebugMessage(ex.getMessage());
		logger.warn("Error en la Base de Datos:" + ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
		apiError.setMessage("Integridad de datos violada");
		apiError.setDebugMessage(ex.getMessage());
		logger.warn("Integridad de datos violada:" + ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
		return buildResponseEntity(new ApiError(NOT_FOUND, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append("El tipo de medio no es compatible. Los tipos de medios admitidos son");
		logger.warn("El tipo de medio no es compatible:" + ex.getContentType());
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		return buildResponseEntity(
				new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		String error = "Solicitud JSON con formato incorrecto";
		logger.warn("Solicitud JSON con formato incorrecto:{} para {}", servletWebRequest.getHttpMethod(),
				servletWebRequest.getRequest().getServletPath());
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Error al escribir la salida JSON";
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	@ExceptionHandler(InOutException.class)
	protected ResponseEntity<Object> handleInOutException(InOutException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED);
		apiError.setMessage("Error en la entrada/salida");
		apiError.setDebugMessage(ex.getMessage());
		logger.warn("Error en la entrada/salida:" + ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(IntegrityViolationException.class)
	protected ResponseEntity<Object> handleIntegrityViolation(IntegrityViolationException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
		apiError.setMessage("Integridad de datos violada");
		apiError.setDebugMessage(ex.getMessage());
		logger.warn("Integridad de datos violada:" + ex.getMessage());
		return buildResponseEntity(apiError);
	}

	// Exception de data incorrecta en los DTO
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY);
		apiError.setMessage("Datos de error validacion");
		apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
		apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
		logger.warn("Datos de error validacion:" + ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		apiError.setMessage(String.format("El parámetro '%s' del valor '%s' no se pudo convertir al tipo '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
		apiError.setDebugMessage(ex.getMessage());
		logger.warn(String.format("El parámetro '%s' del valor '%s' no se pudo convertir al tipo '%s'", ex.getName(),
				ex.getValue(), ex.getRequiredType().getSimpleName()));
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " falta el parámetro";
		logger.warn(error);
		return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		apiError.setMessage(String.format("No se pudo encontrar el método %s para la URL %s", ex.getHttpMethod(),
				ex.getRequestURL()));
		apiError.setDebugMessage(ex.getMessage());
		logger.warn(String.format("No se pudo encontrar el método %s para la URL %s", ex.getHttpMethod(),
				ex.getRequestURL()));
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		ApiError apiError = new ApiError(NOT_FOUND);
		apiError.setMessage("Error en la búsqueda del Objeto");
		apiError.setDebugMessage(ex.getMessage());
		logger.warn("Error en la búsqueda del Objeto: " + ex.getMessage());
		return buildResponseEntity(apiError);
	}

}