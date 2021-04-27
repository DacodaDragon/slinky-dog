package nl.dacodadragon.slinkydog.configuration.exceptions;

public class ConfigurationErrorException extends RuntimeException {
	private static final long serialVersionUID = 486140786049380264L;

	public ConfigurationErrorException(String message) {
		super(message);
	}

	public ConfigurationErrorException(String message, Object... args) {
		super(String.format(message, args));
	}
}
