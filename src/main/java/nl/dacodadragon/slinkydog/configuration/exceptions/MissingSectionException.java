package nl.dacodadragon.slinkydog.configuration.exceptions;

public class MissingSectionException extends ConfigurationErrorException {
	private static final long serialVersionUID = -8894668767684576753L;

	private static final String ERROR_MISSING_SECTION = "Section with name \"%s\" does not exist.";

	private final String section;

	public MissingSectionException(String section) {
		super(ERROR_MISSING_SECTION, section);
		this.section = section;
	}

	public String getSection() {
		return section;
	}
}
