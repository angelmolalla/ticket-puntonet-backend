package ec.puntonet.ticket.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class InOutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InOutException(Class<?> exceptionClass, Object... params) {
		super(InOutException.generateMessage(exceptionClass.getSimpleName(),
				toMap(String.class, String.class, params)));
	}

	public InOutException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InOutException(String arg0) {
		super(arg0);
	}

	private static String generateMessage(String entity, Map<String, String> searchParams) {
		return "Error de entrada y salida del programa en: " + entity + " " + searchParams.toString();
	}

	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
		if (entries.length % 2 == 1) {
			throw new IllegalArgumentException("Entradas inválidas");
		}
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
				(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1].toString())), Map::putAll);
	}
}