package ec.puntonet.ticket.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(Class<?> exceptionClass, Object... params) {
		super(NotFoundException.generateMessage(exceptionClass.getSimpleName(),
				toMap(String.class, String.class, params)));
	}

	public NotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotFoundException(String arg0) {
		super(arg0);
	}

	private static String generateMessage(String entity, Map<String, String> searchParams) {
		return "No se encontró " + entity + " en la base de datos: " + searchParams.toString();
	}

	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
		if (entries.length % 2 == 1) {
			throw new IllegalArgumentException("Entradas inválidas");
		}
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
				(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1].toString())), Map::putAll);
	}
}