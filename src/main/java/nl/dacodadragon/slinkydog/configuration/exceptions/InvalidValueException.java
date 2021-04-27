package nl.dacodadragon.slinkydog.configuration.exceptions;

import nl.dacodadragon.slinkydog.configuration.ParseContext;

import java.util.Collection;

public class InvalidValueException extends ConfigurationErrorException {
	private static final long serialVersionUID = -7918107077487983261L;

	private static final String ERROR_INVALID_VALUE = "Invalid value \"%s\": %s";

	Collection<String> possibleValues;

	String section;
	String setting;
	String value;
	String reason;
	Class<?> type;

	public InvalidValueException(ParseContext context, String reason) {
		this(context, reason, null);
	}

	public InvalidValueException(ParseContext context, String reason, Collection<String> possibleValues) {
		this(context.getSetting(), context.getSection(), context.getValue(), context.getExpectedType(), reason, possibleValues);
	}

	public InvalidValueException(String setting, String section, String value, Class<?> type, String reason,
			Collection<String> possibleValues) {
		super(ERROR_INVALID_VALUE, value, reason);
		this.value = value;
		this.type = type;
		this.reason = reason;
	}

	public Class<?> getValueType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String getReason() {
		return reason;
	}

	public String getSetting() {
		return setting;
	}

	public String getSection() {
		return section;
	}

	public Collection<String> getPossibleValues() {
		return possibleValues;
	}
}
